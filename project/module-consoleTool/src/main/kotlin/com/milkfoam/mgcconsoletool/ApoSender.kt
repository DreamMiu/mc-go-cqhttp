package com.milkfoam.mgcconsoletool

import com.milkfoam.mcgocqhttp.api.MGCAPI
import com.milkfoam.mgcconsoletool.MgcConsoleTool.request
import org.bukkit.command.ConsoleCommandSender
import taboolib.module.chat.uncolored

class ApoSender(private val sender: ConsoleCommandSender) : ConsoleCommandSender by sender {
    override fun sendMessage(msg: String) {
        val a = msg.uncolored()
        request.append("$a\n")
    }
}