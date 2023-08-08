package com.milkfoam.mcgocqhttp.command.subcommand

import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.McGoCqHttp.runBot
import com.milkfoam.mcgocqhttp.database.DataBaseManager
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.module.database.Database

object Connect {

    val connect = subCommand {
        execute<CommandSender> { sender, _, _ ->
            runBot()
        }
    }

}