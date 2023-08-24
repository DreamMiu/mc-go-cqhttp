package com.milkfoam.mgcconsoletool

import com.milkfoam.mcgocqhttp.McGoCqHttp.connectEnable
import com.milkfoam.mcgocqhttp.McGoCqHttp.connectGroup
import com.milkfoam.mcgocqhttp.McGoCqHttp.mctoqqEnable
import com.milkfoam.mcgocqhttp.McGoCqHttp.mctoqqKeyWords
import com.milkfoam.mcgocqhttp.McGoCqHttp.mctoqqText
import com.milkfoam.mcgocqhttp.McGoCqHttp.qqtomcEnable
import com.milkfoam.mcgocqhttp.McGoCqHttp.qqtomcKeyText
import com.milkfoam.mcgocqhttp.McGoCqHttp.qqtomcKeyWords
import com.milkfoam.mcgocqhttp.api.MGCAPI.sendGroupMessage
import com.milkfoam.mcgocqhttp.api.event.QQMessageEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.chat.colored
import taboolib.platform.compat.replacePlaceholder

object MgcConnect {

    /**
     * MC消息转发至QQ
     * @param event AsyncPlayerChatEvent
     */
    @SubscribeEvent
    fun mcToQQ(event: AsyncPlayerChatEvent) {

        if (!connectEnable) return

        //获取玩家
        val player = event.player

        //获取MC原版消息
        val rawMessage = event.message

        //替换原版消息变量和占位符
        val msg = mctoqqText
            .replace("{player}", player.name)
            .replace("{message}", rawMessage)
            .replacePlaceholder(player)

        //判断是否开启关键词功能
        when (mctoqqEnable) {
            true -> {
                //判断是否包含关键词
                if (rawMessage.contains(mctoqqKeyWords)) {
                    //发送至群聊列表
                    val replaceMsg = msg.replace(mctoqqKeyWords, "")
                    connectGroup.forEach {
                        sendGroupMessage(it, replaceMsg)
                    }
                }
            }

            false -> {
                //发送至群聊列表
                connectGroup.forEach {
                    sendGroupMessage(it, msg)
                }
            }
        }
    }


    /**
     * QQ消息转发至MC
     * @param e QQMessageEvent
     */
    @SubscribeEvent
    fun qqToMC(e: QQMessageEvent) {

        if (!connectEnable) return

        if (e.data.message_type == "group") {
            //获取消息
            val message = e.data.raw_message

            if (!message.contains("CQ")) {
                //获取发送玩家名字
                val senderName = if (e.data.sender.card!! == "") {
                    e.data.sender.nickname
                } else {
                    e.data.sender.card!!
                }
                //获取发送QQ号
                val senderId = e.data.user_id
                if (connectGroup.contains(e.data.group_id)) {
                    //判断是否开启关键词功能
                    when (qqtomcEnable) {
                        true -> {
                            //判断是否包含关键词
                            if (message.contains(qqtomcKeyWords)) {
                                val replaceMsg = message.replace(qqtomcKeyWords, "")
                                onlinePlayers().forEach { proxyplayer ->
                                    proxyplayer.sendMessage(
                                        qqtomcKeyText
                                            .replace("{sender}", senderName)
                                            .replace("{sender_id}", senderId)
                                            .replace("{message}", replaceMsg)
                                            .colored()
                                    )
                                }
                            }
                        }

                        false -> {
                            onlinePlayers().forEach { proxyplayer ->
                                proxyplayer.sendMessage(
                                    qqtomcKeyText
                                        .replace("{sender}", senderName)
                                        .replace("{sender_id}", senderId)
                                        .replace("{message}", message)
                                        .colored()
                                )
                            }
                        }

                    }

                }
            }
        }
    }


}