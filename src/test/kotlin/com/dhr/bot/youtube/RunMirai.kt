package com.dhr.bot.youtube

import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader

suspend fun main() {
    //HTTP 代理，只能代理 HTTP 请求
    System.setProperty("http.proxyHost", "127.0.0.1")
    System.setProperty("http.proxyPort", "7890")
    // HTTPS 代理，只能代理 HTTPS 请求
    System.setProperty("https.proxyHost", "127.0.0.1")
    System.setProperty("https.proxyPort", "7890")

    MiraiConsoleTerminalLoader.startAsDaemon()

    YoutubePlugin().load()
    YoutubePlugin().enable()

    val bot = MiraiConsole.addBot(386165664, "Dhr13532201835") {
        fileBasedDeviceInfo()
    }.alsoLogin()

    MiraiConsole.job.join()
}