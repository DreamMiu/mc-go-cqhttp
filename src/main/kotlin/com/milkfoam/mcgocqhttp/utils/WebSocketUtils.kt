package com.milkfoam.mcgocqhttp.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener

import okhttp3.internal.ws.RealWebSocket

private var client: OkHttpClient? = null
private var webSocket: RealWebSocket? = null

class WebSocketUtils(url: String, listener: WebSocketListener) {

    init {
        if (webSocket != null) {
            webSocket?.close(1001, "已经有连接,关闭")
            consoleInfoMsg("已有WebSocket连接,自动关闭")
        }
        try {
            client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//断线重连
                .build()
            val request: Request = Request.Builder().url(url).build()
            //创建webSocket
            webSocket = client?.newWebSocket(request, listener) as RealWebSocket
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}