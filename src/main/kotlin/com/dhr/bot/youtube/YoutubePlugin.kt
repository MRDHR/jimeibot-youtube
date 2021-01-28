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
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException

class YoutubePlugin : KotlinPlugin(
    JvmPluginDescription(
        id = YoutubePlugin::class.java.name,
        version = "0.1.0",
        name = "油管 power by 一生的等待"
    )
) {
    private var client = OkHttpClient()
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
                    if (message.content.startsWith("yt监控添加 ")) {
                        val replace = message.content.replace("yt监控添加", "").replace(" ", "")
                        val channelId = if (replace.startsWith("https://www.youtube.com/channel/")) {
                            replace.replace("https://www.youtube.com/channel/", "")
                        } else {
                            replace
                        }
                        val groupId = group.id
                        if (channelId.isNotEmpty()) {
                            val find = YoutubeData.channels.find { it.groupId == groupId && it.channelId == channelId }
                            if (null == find) {
                                YoutubeData.channels.add(Channel(groupId, channelId))
                                sender.group.sendMessage("该频道已保存")
                            } else {
                                sender.group.sendMessage("该频道之前已经保存了，请勿重新提交")
                            }
                        }

                    } else if (message.content.startsWith("yt监控移除 ")) {
                        val replace = message.content.replace("yt监控移除 ", "")
                        val channelId = if (replace.startsWith("https://www.youtube.com/channel/")) {
                            replace.substring(replace.lastIndexOf("/"))
                        } else {
                            replace
                        }
                        val groupId = group.id
                        if (channelId.isNotEmpty()) {
                            val find = YoutubeData.channels.find { it.groupId == groupId && it.channelId == channelId }
                            YoutubeData.channels.removeAt(YoutubeData.channels.lastIndexOf(find))
                        }
                        sender.group.sendMessage("该频道已移除")
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
                    val jsonStr = Jsoup.connect("https://www.youtube.com/channel/$channelId/live").get().body()
                        .select("script")[1].toString()
                        .split("var ytInitialPlayerResponse = ")[1].split(";var meta = document.createElement('meta');")[0]
                    if (jsonStr.isNotEmpty()) {
                        val youtubeStatusVo = gson.fromJson(jsonStr, YoutubeStatusVo::class.java)
                        if (null != youtubeStatusVo) {
                            val status = youtubeStatusVo.playabilityStatus.status
                            if ("LIVE_STREAM_OFFLINE" == status) {
                                //没有正在直播
                                if (it.isLiving) {
                                    //之前在直播
                                    val result = StringBuilder("您订阅的->")
                                        .append(it.channelName)
                                        .append("下播直播啦")
                                    bot.getGroup(it.groupId)?.sendMessage(result.toString())
                                }
                                it.isLiving = false
                            } else if ("OK" == status) {
                                //有正在直播
                                if (!it.isLiving) {
                                    //之前没有在直播
                                    val result = StringBuilder("您订阅的->")
                                        .append(youtubeStatusVo.videoDetails.author)
                                        .append("开始直播啦")
                                        .append("\n蓝条条：https://www.youtube.com/channel/$channelId/live")
                                        .append("\n直播标题：" + youtubeStatusVo.videoDetails.title)
                                        .append("\n直播封面：")
                                    val images = mutableListOf<String>()
                                    val file = File("${dataFolder.absolutePath}/imagecache/")
                                    if (!file.exists()) {
                                        file.mkdirs()
                                    }
                                    val imagePath =
                                        "${dataFolder.absolutePath}/imagecache/${System.currentTimeMillis()}.jpg"
                                    try {
                                        val imageRequest: Request = Request.Builder()
                                            .url(youtubeStatusVo.videoDetails.thumbnail.thumbnails.last().url)
                                            .removeHeader("User-Agent")
                                            .addHeader(
                                                "User-Agent",
                                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36 Edg/85.0.564.51"
                                            )
                                            .build()
                                        val execute = client.newCall(imageRequest).execute()
                                        //将响应数据转化为输入流数据
                                        val inputStream = execute.body!!.byteStream()
                                        val fileOutputStream = FileOutputStream(File(imagePath))
                                        val output = ByteArrayOutputStream()
                                        val buffer = ByteArray(1024)
                                        var length: Int
                                        while (inputStream.read(buffer)
                                                .also { it2 -> length = it2 } > 0
                                        ) {
                                            output.write(buffer, 0, length)
                                        }
                                        fileOutputStream.write(output.toByteArray())
                                        inputStream.close()
                                        fileOutputStream.close()
                                        images.add(imagePath)
                                    } catch (e: MalformedURLException) {
                                        e.printStackTrace()
                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    }
                                    val messageArray: MutableList<Message> = mutableListOf()
                                    messageArray.add(PlainText(result))
                                    images.forEach { it2 ->
                                        bot.getGroup(it.groupId)?.let { it1 ->
                                            messageArray.add(
                                                File(it2).toExternalResource()
                                                    .uploadAsImage(it1)
                                            )
                                        }
                                    }
                                    val asMessageChain = messageArray.toMessageChain()
                                    bot.getGroup(it.groupId)?.sendMessage(asMessageChain)
                                }
                                it.isLiving = true
                            }
                        }
                    }
                    delay(50)
                }
                delay(60000)
                val file = File("${dataFolder.absolutePath}/imagecache/")
                file.listFiles().forEach {
                    it.delete()
                }
                queryYoutube()
            } catch (ex: Exception) {
                delay(60000)
                queryYoutube()
            }
        }
    }

}