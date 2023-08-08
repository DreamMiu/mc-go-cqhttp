package com.milkfoam.mcgocqhttp.api

import com.milkfoam.mcgocqhttp.bot.GroupMember
import com.milkfoam.mcgocqhttp.bot.UserData
import com.milkfoam.mcgocqhttp.bot.get.GetGroupInfo
import com.milkfoam.mcgocqhttp.bot.get.GetStrangerInfo
import com.milkfoam.mcgocqhttp.bot.sendMessage.PrivateMessage
import com.milkfoam.mcgocqhttp.database.DataBaseManager
import com.milkfoam.mcgocqhttp.bot.sendMessage.GroupMessage
import java.util.*

object McGoCqHttpAPI {

    /**
     * 获取玩家绑定的QQ
     * @param uuid UUID
     * @return String
     */
    fun getQQ(uuid: UUID): String {
        return DataBaseManager.getQQ(uuid)
    }

    /**
     * 获取数据库中玩家的绑定信息 uuid:QQ
     * @return MutableMap<String, String>
     */
    fun getPlayerData(): MutableMap<String, String> {
        return DataBaseManager.getPlayerData()
    }

    /**
     * 获取指定群聊的所有QQ的详细信息
     * @param groupId String
     * @return List<GroupMember>
     */
    fun getGroupMemberList(groupId: String): List<GroupMember> {
        return GetGroupInfo().getGroupMemberList(groupId)
    }

    /**
     * 获取指定账号的QQ信息
     * @param qq String
     * @return String
     */
    fun getStrangerQQInfo(qq: String): UserData? {
        return GetStrangerInfo().getData(qq)
    }

    /**
     * 保存玩家信息
     * @param uuid UUID
     * @param qq String
     */
    fun save(uuid: UUID, qq: String) {
        DataBaseManager.saveQQ(uuid, qq)
    }

    /**
     * 发送私聊消息
     * @param userId String
     * @param message String
     */
    fun sendPrivateMessage(userId: String, message: String) {
        PrivateMessage().send(userId, message)
    }

    /**
     * 发送群组消息
     * @param groupId String
     * @param message String
     */
    fun sendGroupMessage(groupId: String, message: String) {
        GroupMessage().send(groupId, message)
    }

}