package com.milkfoam.mcgocqhttp.command.subcommand

import com.milkfoam.mcgocqhttp.McGoCqHttp.loadConfig
import com.milkfoam.mcgocqhttp.utils.sendInfoMsg
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendInfo

object Reload {

    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            loadConfig()
            sender.sendInfoMsg("&a重载成功!")
        }
    }

}