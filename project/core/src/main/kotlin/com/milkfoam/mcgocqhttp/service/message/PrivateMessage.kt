package com.milkfoam.mcgocqhttp.service.message

import com.milkfoam.mcgocqhttp.McGoCqHttp.webSocketHttp
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class PrivateMessage {

    //发送好友消息
    fun send(userId: String, message: String) {
        try {
            val uft8Msg = URLEncoder.encode(message, "utf-8") //java中的中文字符转URLEncode
            val url = URL("$webSocketHttp/send_private_msg?user_id=$userId&message=$uft8Msg")
            url.openStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}