package com.milkfoam.mcgocqhttp.service.info

import com.alibaba.fastjson2.JSON
import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.data.UserData
import java.io.IOException
import java.net.URL

class GetStrangerInfo {

    fun getData(userId: String): UserData? {
        var data: UserData? = null
        try {
            val url = URL("${McGoCqHttp.webSocketHttp}/get_stranger_info?user_id=$userId")
            val connection = url.openConnection()
            connection.connect()

            val inputStream = connection.getInputStream()
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSON.parseObject(jsonString)
            data = jsonObject.getObject("data", UserData::class.java)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return data
    }


}