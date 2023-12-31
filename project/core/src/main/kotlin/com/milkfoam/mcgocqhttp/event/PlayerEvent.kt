package com.milkfoam.mcgocqhttp.event

import com.milkfoam.mcgocqhttp.api.MGCAPI
import com.milkfoam.mcgocqhttp.event.BindEvent.inBindList
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.lang.Language
import taboolib.module.lang.TypeText

object PlayerEvent {

    private val isInBindButLeave =
        (Language.languageFile["zh_CN"]!!.nodes["QQ-isInBindButLeave"] as TypeText).text!!

    @SubscribeEvent
    fun quit(e: PlayerQuitEvent) {
        if (inBindList.contains(e.player.uniqueId)) {
            MGCAPI.sendPrivateMessage(inBindList[e.player.uniqueId]!!, isInBindButLeave)
            inBindList.remove(e.player.uniqueId)
        }
    }

    @SubscribeEvent
    fun kick(e: PlayerKickEvent) {
        if (inBindList.contains(e.player.uniqueId)) {
            MGCAPI.sendPrivateMessage(inBindList[e.player.uniqueId]!!, isInBindButLeave)
            inBindList.remove(e.player.uniqueId)
        }
    }


}