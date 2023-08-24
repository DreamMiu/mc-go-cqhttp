package com.milkfoam.mcgocqhttp.service

import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.McGoCqHttp.authToken
import com.milkfoam.mcgocqhttp.McGoCqHttp.authTokenEnable
import com.milkfoam.mcgocqhttp.utils.consoleInfoMsg
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener
import okhttp3.internal.ws.RealWebSocket
import java.util.concurrent.TimeUnit

class WebSocketUtils(url: String, listener: WebSocketListener) {

    private var client: OkHttpClient? = null
    private var webSocket: RealWebSocket? = null

    init {
        if (webSocket != null) {
            webSocket?.close(1001, "已经有连接,关闭")
            consoleInfoMsg("已有WebSocket连接,自动关闭")
        }
        try {
            client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)       //设置连接超时
                .readTimeout(60, TimeUnit.SECONDS)          //设置读超时
                .writeTimeout(60, TimeUnit.SECONDS)          //设置写超时
                .retryOnConnectionFailure(true)
                .build()

            val request: Request = if (authTokenEnable) {
                Request
                    .Builder()
                    .header("Authorization", "Bearer $authToken")
                    .url(url)
                    .build()
            } else {
                Request.Builder()
                    .url(url)
                    .build()
            }

            //创建webSocket
            webSocket = client?.newWebSocket(request, listener) as RealWebSocket
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}