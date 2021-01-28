package com.dhr.bot.youtube

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object YoutubeData : AutoSavePluginData("youtube") {
    var channels: MutableList<Channel> by value() // List、Set 或 Map 同样支持 var。但请注意这是非引用赋值（详见下文）。
}

@Serializable
class Channel(
    var groupId: Long,
    var channelId: String,
    var channelName: String = "",
    var isLiving: Boolean = false
)