package com.milkfoam.mcgocqhttp.event

import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.McGoCqHttp.groupIDList
import com.milkfoam.mcgocqhttp.McGoCqHttp.groupInputKey
import com.milkfoam.mcgocqhttp.McGoCqHttp.level
import com.milkfoam.mcgocqhttp.api.event.PlayerBindQQEvent
import com.milkfoam.mcgocqhttp.bot.BotData
import com.milkfoam.mcgocqhttp.bot.get.GetStrangerInfo
import com.milkfoam.mcgocqhttp.database.DataBaseManager
import com.milkfoam.mcgocqhttp.bot.sendMessage.GroupMessage
import com.milkfoam.mcgocqhttp.utils.runAction
import org.bukkit.Bukkit
import taboolib.module.lang.Language
import taboolib.module.lang.TypeText
import taboolib.platform.util.nextChatInTick
import taboolib.platform.util.sendLang
import java.util.UUID

object BindEvent {

    private val notFindPlayer = (Language.languageFile["zh_CN"]!!.nodes["QQ-notFindPlayer"] as TypeText).text!!
    private val sendMessageToMinecraft =
        (Language.languageFile["zh_CN"]!!.nodes["QQ-sendMessageToMinecraft"] as TypeText).text!!
    private val success = (Language.languageFile["zh_CN"]!!.nodes["QQ-Success"] as TypeText).text!!
    private val timeOut = (Language.languageFile["zh_CN"]!!.nodes["QQ-TimeOut"] as TypeText).text!!
    private val isBind = (Language.languageFile["zh_CN"]!!.nodes["QQ-isBind"] as TypeText).text!!
    private val isInBind = (Language.languageFile["zh_CN"]!!.nodes["QQ-isInBind"] as TypeText).text!!
    private val levelIsNotStandard =
        (Language.languageFile["zh_CN"]!!.nodes["QQ-levelIsNotStandard"] as TypeText).text!!

    private val bindKey = (Language.languageFile["zh_CN"]!!.nodes["Minecraft-BindKey"] as TypeText).text!!

    val inBindList: MutableMap<UUID, String> = mutableMapOf()

    fun bind(data: BotData) {
        with(data) {
            //判断是否为群消息
            if (message_type == "group") {
                //获取消息
                val message = raw_message
                //获取发送人QQ
                val sendQQ = user_id
                //获取群聊号
                val groupId = group_id!!
                //获取发送者的信息
                val userData = GetStrangerInfo().getData(sendQQ)
                //获取发送者的QQ等级
                val qqLevel = userData?.level ?: 1
                if (groupIDList.contains(groupId)) {
                    if (message.contains(groupInputKey)) {
                        if (qqLevel >= (level.toIntOrNull() ?: 1)) {
                            //获取玩家
                            Bukkit.getPlayer(message.split(" ")[1])?.let { player ->
                                //判断是否为绑定状态
                                if (!inBindList.containsValue(sendQQ)) {
                                    //获取绑定状态
                                    val bindState = DataBaseManager.getQQ(player.uniqueId)
                                    //判断是否未绑定
                                    if (bindState == "未绑定" && !DataBaseManager.checkQQ(sendQQ)) {
                                        //添加绑定状态
                                        inBindList[player.uniqueId] = sendQQ
                                        //发送绑定消息给游戏内
                                        player.sendLang("Minecraft-Bind", sendQQ, player.name)
                                        //发送绑定消息给QQ群
                                        GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + sendMessageToMinecraft)
                                        //监听玩家输入字符串并且30秒超时后发送超时消息给玩家
                                        player.nextChatInTick(30 * 20, { str ->
                                            //判断玩家游戏内输入的关键词
                                            if (str == bindKey) {
                                                //删除绑定状态
                                                inBindList.remove(player.uniqueId, sendQQ)
                                                //储存绑定数据进数据库
                                                DataBaseManager.saveQQ(player.uniqueId, sendQQ)
                                                //发送绑定成功消息给游戏内
                                                player.sendLang("Minecraft-Success")
                                                //发送绑定成功消息给QQ群
                                                GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + success)
                                                //绑定成功执行指令
                                                McGoCqHttp.actions.forEach { strs ->
                                                    runAction(strs, player)
                                                }
                                                val event = PlayerBindQQEvent(sendQQ, player.name)
                                                if (!event.isCancelled) {
                                                    event.call()
                                                }
                                            }
                                        }, {
                                            //发送超时消息给游戏内
                                            player.sendLang("Minecraft-TimeOut")
                                            //发送超时消息给QQ群
                                            GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + timeOut)
                                            //删除绑定状态
                                            inBindList.remove(player.uniqueId, sendQQ)
                                        })
                                    } else {
                                        //发送已经绑定消息给游戏内
                                        player.sendLang("Minecraft-isBind")
                                        //发送已经绑定消息给QQ群
                                        GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + isBind)
                                    }
                                } else {
                                    //QQ账号正在绑定中发送消息给QQ群
                                    GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + isInBind)
                                }
                            } ?: run {
                                //获取玩家失败发送消息给QQ群
                                GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + notFindPlayer)
                            }
                        } else {
                            //QQ账号未达到等级发送消息给QQ群
                            GroupMessage().send(groupId, "[CQ:at,qq=${sendQQ}]" + levelIsNotStandard)
                            Bukkit.getPlayer(message.split(" ")[1])?.sendLang("Minecraft-levelIsNotStandard")
                        }
                    }
                }
            }
        }
    }


}