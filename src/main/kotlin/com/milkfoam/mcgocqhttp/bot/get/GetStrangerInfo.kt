package com.milkfoam.mcgocqhttp.bot.get

import com.alibaba.fastjson2.JSON
import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.bot.GroupMember
import com.milkfoam.mcgocqhttp.bot.UserData
import taboolib.common.io.groupId
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
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