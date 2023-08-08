package com.milkfoam.mcgocqhttp.bot.sendMessage

import com.milkfoam.mcgocqhttp.McGoCqHttp.webSocketHttp
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class GroupMessage {

    //发送群聊消息
    fun send(groupId: String, message: String) {
        try {
            val uft8Msg = URLEncoder.encode(message, "utf-8") //java中的中文字符转URLEncode
            val url = URL("$webSocketHttp/send_group_msg?group_id=$groupId&message=$uft8Msg")
            url.openStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}