package com.dhr.bot.youtube

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.events.BotReloginEvent
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.*
import java.io.*

class YoutubePlugin : KotlinPlugin(
    JvmPluginDescription(
        id = YoutubePlugin::class.java.name,
        version = "0.1.0",
        name = "油管 power by 一生的等待"
    )
) {
    private var gson = Gson()
    private var job: Job? = null
    private lateinit var bot: Bot

    override fun onEnable() {
        super.onEnable()
        YoutubeData.reload()
        initYoutube()
        GlobalEventChannel.subscribeAlways<BotReloginEvent> {
            this@YoutubePlugin.bot = bot
            queryYoutube()
        }
        GlobalEventChannel.subscribeAlways<BotOnlineEvent> {
            this@YoutubePlugin.bot = bot
            queryYoutube()
        }
    }

    private fun initYoutube() {
        GlobalEventChannel.subscribeGroupMessages {
            always {
                launch(Dispatchers.IO) {
                    //直播监控添加 <频道1> <频道2> ...
                    //直播监控移除 <频道1> <频道2> ...
                    //直播监控列表 [页码]
                    //直播监控详细列表 [页码]
                    if (message.content.startsWith("yt-add ")) {
                        val replace = message.content.replace("yt-add ", "")
                        val split = replace.split(" ")
                        if (split.size == 2) {
                            val channelId = split[0].replace("https://www.youtube.com/channel/", "")
                            val name = split[1]
                            val groupId = group.id
                            if (channelId.isNotEmpty()) {
                                val find =
                                    YoutubeData.channels.find { it.groupId == groupId && it.channelId == channelId }
                                if (null == find) {
                                    YoutubeData.channels.add(Channel(groupId, channelId, name))
                                    sender.group.sendMessage("该频道保存成功")
                                } else {
                                    sender.group.sendMessage("该频道之前已经保存了，请勿重新提交")
                                }
                            }
                        } else {
                            sender.group.sendMessage(
                                "参数错误，保存失败 正确示例：\n" +
                                    "yt-add https://www.youtube.com/channel/xxxxx 某某的频道（保存的频道名，做开播提醒区分）\n" +
                                    "或yt-add xxxxx 某某的频道（保存的频道名，做开播提醒区分）"
                            )
                        }
                    } else if (message.content.startsWith("yt-remove ")) {
                        val replace = message.content.replace("yt-remove ", "")
                        val channelId = replace.replace("https://www.youtube.com/channel/", "")
                        val groupId = group.id
                        if (channelId.isNotEmpty()) {
                            val find = YoutubeData.channels.find { it.groupId == groupId && it.channelId == channelId }
                            YoutubeData.channels.removeAt(YoutubeData.channels.lastIndexOf(find))
                        }
                        sender.group.sendMessage("该频道已移除")
                    } else if (message.content == "yt-help") {
                        sender.group.sendMessage(
                            "油管直播提醒相关功能 \n" +
                                "1：添加监控\n" +
                                "yt-add https://www.youtube.com/channel/xxxxx 某某的频道（保存的频道名，做开播提醒区分）\n" +
                                "或yt-add xxxxx 某某的频道（保存的频道名，做开播提醒区分）\n" +
                                "2:移除监控\n" +
                                "yt-remove https://www.youtube.com/channel/xxxxx\n" +
                                "或yt-remove xxxxx"
                        )
                    }
                }
            }
        }
    }

    private fun queryYoutube() {
        job?.cancel()
        job = launch(Dispatchers.IO) {
            try {
                YoutubeData.channels.forEach {
                    val channelId = it.channelId
                    val proc: Process
                    try {
                        val args = arrayOf("python", "C:\\YoutubeLiveStatus\\LiveStatus.py", channelId)
                        proc = Runtime.getRuntime().exec(args) // 执行py文件
                        //用输入输出流来截取结果
                        val `in` = BufferedReader(InputStreamReader(proc.inputStream, "GBK"))
                        val readLines = `in`.readLines()
                        if (readLines.isNotEmpty()) {
                            val lastLine: String = readLines.last()
                            `in`.close()
                            proc.waitFor()
                            logger.info("查询结果：$lastLine")
                            if (lastLine.isNotBlank()) {
                                val youtubeLiveStatusVo = gson.fromJson(lastLine, YoutubeLiveStatusVo::class.java)
                                if ("true" == youtubeLiveStatusVo.isLive) {
                                    //直播中
                                    if (!it.isLiving) {
                                        //之前没有在直播
                                        val result = StringBuilder("您订阅的->")
                                            .append(it.channelName)
                                            .append("开始直播啦")
                                            .append("\n蓝条条：https://www.youtube.com/channel/$channelId/live")
                                            .append("\n直播标题：" + youtubeLiveStatusVo.title)
                                        bot.getGroup(it.groupId)?.sendMessage(result.toString())
                                    }
                                    it.isLiving = true
                                } else {
                                    //没有直播了
                                    if (it.isLiving) {
                                        //之前在直播
                                        val result = StringBuilder("您订阅的->")
                                            .append(it.channelName)
                                            .append("下播啦")
                                        bot.getGroup(it.groupId)?.sendMessage(result.toString())
                                    }
                                    it.isLiving = false
                                }
                            }
                        }
                    } catch (e: IOException) {
                        logger.error("IOException：$e")
                    } catch (e: InterruptedException) {
                        logger.error("InterruptedException：$e")
                    }
                    delay(50)
                }
                delay(60000)
                queryYoutube()
            } catch (ex: Exception) {
                logger.error("Exception：$ex")
                delay(60000)
                queryYoutube()
            }
        }
    }

}