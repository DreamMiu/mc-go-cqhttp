package com.milkfoam.mcgocqhttp.utils

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.platform.compat.replacePlaceholder

fun extractCommandType(input: String): String {
    val regex = "(cmd|op|console|kether):\\s?(.*)".toRegex()
    val matchResult = regex.find(input)
    if (matchResult?.groupValues?.get(2)?.trimStart() != null) {
        return matchResult.groupValues[2].trimStart()
    } else {
        consoleErrorMsg("&c执行指令出现错误，请检查配置文件!")
    }
    return ""
}

fun runAction(str: String, player: Player) {
    submit {
        val cmds = extractCommandType(str).replacePlaceholder(player)
        if (str.startsWith("op")) {
            if (player.isOp) {
                Bukkit.dispatchCommand(player, cmds)
            } else {
                val isOp = player.isOp
                player.isOp = true
                try {
                    Bukkit.dispatchCommand(player, cmds)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                player.isOp = isOp
            }
        } else if (str.startsWith("console")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds)
        } else if (str.startsWith("cmd")) {
            player.performCommand(cmds)
        } else if (str.startsWith("kether")) {
            runKether(player, cmds)
        }
    }
}


