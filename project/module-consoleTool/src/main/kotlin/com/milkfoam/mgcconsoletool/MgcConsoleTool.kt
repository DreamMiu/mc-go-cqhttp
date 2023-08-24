package com.milkfoam.mgcconsoletool

import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.McGoCqHttp.admins
import com.milkfoam.mcgocqhttp.McGoCqHttp.consoleToolEnable
import com.milkfoam.mcgocqhttp.McGoCqHttp.consoleToolKeyWords
import com.milkfoam.mcgocqhttp.api.MGCAPI
import com.milkfoam.mcgocqhttp.api.event.QQMessageEvent
import org.bukkit.Bukkit
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit

object MgcConsoleTool {

    var request = StringBuilder()

    @SubscribeEvent
    fun consoleTool(e: QQMessageEvent) {

        if (!consoleToolEnable) return

        if (e.data.raw_message.startsWith(consoleToolKeyWords)) {
            val cmd = e.data.raw_message.split(":")[1]
            val sender = e.data.sender.user_id

            if (admins.contains(sender)) {
                submit {
                    Bukkit.dispatchCommand(
                        ApoSender(Bukkit.getConsoleSender()), cmd
                    )
                    MGCAPI.sendPrivateMessage(sender, request.toString())
                    request.clear()
                }
            }

        }
    }

}