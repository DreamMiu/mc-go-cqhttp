package com.milkfoam.mcgocqhttp.api

import com.milkfoam.mcgocqhttp.data.GroupMember
import com.milkfoam.mcgocqhttp.data.UserData
import com.milkfoam.mcgocqhttp.service.info.GetGroupAllUserInfo
import com.milkfoam.mcgocqhttp.service.info.GetStrangerInfo
import com.milkfoam.mcgocqhttp.service.message.GroupMessage
import com.milkfoam.mcgocqhttp.service.message.PrivateMessage
import com.milkfoam.mcgocqhttp.database.DataBaseManager
import com.milkfoam.mcgocqhttp.service.controller.GroupController
import java.util.*

typealias McGoCqHttpAPI = MGCAPI
object MGCAPI {

    /**
     * 禁言某个群的QQ
     * @param groupId 群号
     * @param userId QQ
     * @param duration 单位为秒 0的时候取消禁言
     */
    fun groupBan(groupId: String, userId: String, duration: Int) {
        GroupController().groupBan(groupId, userId, duration)
    }

    /**
     * 控制某个群的全员禁言
     * @param groupId 群号
     * @param enable 是否开启 (true|false)
     */
    fun groupBan(groupId: String, enable: String) {
        GroupController().groupAllBan(groupId, enable)
    }

    /**
     * 踢出某个群的某个QQ
     * @param groupId String
     * @param userId String
     * @param rejectAddRequest 是否拒绝此人的加群请求 (true|false)
     */
    fun groupKick(groupId: String, userId: String, rejectAddRequest: String) {
        GroupController().groupKick(groupId, userId, rejectAddRequest)
    }

    /**
     * 修改某个群某个QQ的群名片
     * @param groupId String
     * @param userId String
     * @param card String 群名片内容, 不填或空字符串表示删除群名片
     */
    fun setGroupCard(groupId: String, userId: String, card: String) {
        GroupController().setGroupCard(groupId, userId, card)
    }

    /**
     * 获取玩家绑定的QQ
     * @param uuid UUID
     * @return String
     */
    fun getQQ(uuid: UUID): String {
        return DataBaseManager.getQQ(uuid)
    }

    /**
     * 获取qq绑定的玩家
     * @param qq String
     * @return String
     */
    fun getPlayer(qq: String): String {
        return DataBaseManager.getPlayer(qq)
    }

    /**
     * 判断qq是否已经绑定
     * @param qq String
     * @return Boolean
     */
    fun checkQQ(qq: String): Boolean {
        return DataBaseManager.checkQQ(qq)
    }

    /**
     * 保存玩家信息
     * @param uuid UUID
     * @param qq String
     */
    fun saveQQ(uuid: UUID, qq: String) {
        DataBaseManager.saveQQ(uuid, qq)
    }

    /**
     * 通过uuid删除玩家数据
     * @param uuid UUID
     */
    fun remove(uuid: UUID) {
        DataBaseManager.remove(uuid)
    }

    /**
     * 通过qq删除玩家数据
     * @param qq String
     */
    fun remove(qq: String) {
        DataBaseManager.remove(qq)
    }

    /**
     * 获取数据库中玩家的绑定信息 uuid:QQ
     * @return MutableMap<String, String>
     */
    fun getPlayerData(): MutableMap<String, String> {
        return DataBaseManager.getPlayerData()
    }

    /**
     * 获取数据库中的所有qq
     * @return MutableList<String>
     */
    fun getQQList(): MutableList<String> {
        return DataBaseManager.getQQList()
    }

    /**
     * 获取指定群聊的所有QQ的详细信息
     * @param groupId String
     * @return List<GroupMember>
     */
    fun getGroupMemberList(groupId: String): List<GroupMember> {
        return GetGroupAllUserInfo().getGroupMemberList(groupId)
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

    /**
     * 群聊内艾特玩家
     * @param qq String
     * @return String
     */
    fun atQQUser(qq: String): String {
        return "[CQ:at,qq=${qq}]"
    }

}