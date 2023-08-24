package com.milkfoam.mcgocqhttp.service.controller

import com.milkfoam.mcgocqhttp.McGoCqHttp
import java.io.IOException
import java.net.URL

class GroupController {

    fun groupBan(groupId: String, userId: String, duration: Int) {
        try {
            val url = URL(
                "${McGoCqHttp.webSocketHttp}/set_group_ban?group_id=$groupId&userId=$userId&duration=$duration"
            )
            url.openStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun groupAllBan(groupId: String, enable: String) {
        try {
            val url = URL(
                "${McGoCqHttp.webSocketHttp}/set_group_ban?group_id=$groupId&enable=$enable"
            )
            url.openStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun groupKick(groupId: String, userId: String, rejectAddRequest: String) {
        try {
            val url = URL(
                "${McGoCqHttp.webSocketHttp}/set_group_ban?group_id=$groupId&userId=$userId&rejectAddRequest=$rejectAddRequest"
            )
            url.openStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setGroupCard(groupId: String, userId: String, card: String) {
        try {
            val url = URL(
                "${McGoCqHttp.webSocketHttp}/set_group_card?group_id=$groupId&userId=$userId&card=$card"
            )
            url.openStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}