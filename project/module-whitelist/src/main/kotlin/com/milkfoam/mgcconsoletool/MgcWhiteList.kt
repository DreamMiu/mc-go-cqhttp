package com.milkfoam.mgcconsoletool


import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.McGoCqHttp.whiteListCancelChat
import com.milkfoam.mcgocqhttp.McGoCqHttp.whiteListCancelCommand
import com.milkfoam.mcgocqhttp.McGoCqHttp.whiteListCancelJoin
import com.milkfoam.mcgocqhttp.McGoCqHttp.whiteListCancelMove
import com.milkfoam.mcgocqhttp.McGoCqHttp.whiteListEnable
import com.milkfoam.mcgocqhttp.McGoCqHttp.whiteListMessage
import com.milkfoam.mcgocqhttp.api.MGCAPI.getQQ
import com.milkfoam.mcgocqhttp.api.McGoCqHttpAPI
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import sun.audio.AudioPlayer.player
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import taboolib.platform.compat.replacePlaceholder

object MgcWhiteList {

    @SubscribeEvent
    fun join(e: PlayerJoinEvent) {

        if (!whiteListEnable) return

        val player = e.player
        if (whiteListCancelJoin && getQQ(player.uniqueId) == "未绑定") {
            submit(delay = 40) {
                player.kickPlayer(sendWhiteListMessage(player))
            }
        }
    }

    @SubscribeEvent
    fun move(e: PlayerMoveEvent) {

        if (!whiteListEnable) return

        val player = e.player
        if (whiteListCancelMove && getQQ(player.uniqueId) == "未绑定") {
            e.isCancelled = true
            player.sendMessage(sendWhiteListMessage(player))
        }
    }

    @SubscribeEvent
    fun chat(e: AsyncPlayerChatEvent) {

        if (!whiteListEnable) return

        val player = e.player
        if (whiteListCancelChat && getQQ(player.uniqueId) == "未绑定") {
            e.isCancelled = true
            player.sendMessage(sendWhiteListMessage(player))
        }
    }

    @SubscribeEvent
    fun cmd(e: PlayerCommandPreprocessEvent) {

        if (!whiteListEnable) return

        val player = e.player
        if (whiteListCancelCommand && getQQ(player.uniqueId) == "未绑定") {
            e.isCancelled = true
            player.sendMessage(sendWhiteListMessage(player))
        }
    }

    private fun sendWhiteListMessage(player: Player): String {
        return whiteListMessage.replace("{player}", player.name).replacePlaceholder(player).colored()
    }


}