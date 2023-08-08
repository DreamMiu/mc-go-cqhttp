package com.milkfoam.mcgocqhttp.bot.get

import com.alibaba.fastjson2.JSON
import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.bot.GroupMember
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets


class GetGroupInfo {

    fun getGroupMemberList(groupId: String): List<GroupMember> {
        val groupMembers = mutableListOf<GroupMember>()

        try {
            val url = URL("${McGoCqHttp.webSocketHttp}/get_group_member_list?group_id=$groupId")
            val connection = url.openConnection()
            connection.connect()

            val inputStream = connection.getInputStream()
            val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            val jsonData = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonData.append(line)
            }
            reader.close()

            val jsonObject = JSON.parseObject(jsonData.toString())
            val dataArray = jsonObject.getJSONArray("data")
            for (i in 0 until dataArray.size) {
                val memberObject = dataArray.getJSONObject(i)
                val groupMember = JSON.parseObject(memberObject.toJSONString(), GroupMember::class.java)
                groupMembers.add(groupMember)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return groupMembers
    }

}