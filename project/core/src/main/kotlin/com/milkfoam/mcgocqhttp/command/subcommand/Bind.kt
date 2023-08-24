package com.milkfoam.mcgocqhttp.command.subcommand

import com.milkfoam.mcgocqhttp.database.DataBaseManager
import com.milkfoam.mcgocqhttp.utils.sendInfoMsg
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common5.util.parseUUID
import taboolib.module.chat.Components
import taboolib.module.chat.colored
import taboolib.platform.util.sendInfo

object Bind {

    val addBind = subCommand {
        dynamic("qq") {
            dynamic("player", optional = true) {
                execute<CommandSender> { sender, context, _ ->
                    val qq = context["qq"]
                    val player = Bukkit.getOfflinePlayer(context["player"])
                    player.uniqueId.let { DataBaseManager.saveQQ(it, qq) }
                    sender.sendInfoMsg("&a添加成功!")
                }
            }
        }
    }

    val removeBind = subCommand {
        execute<Player> { sender, _, _ ->
            DataBaseManager.remove(sender.uniqueId)
            sender.sendInfo("&c删除成功!")
        }
        dynamic("player", optional = true) {
            execute<CommandSender> { sender, context, _ ->
                Bukkit.getOfflinePlayer(context["player"]).uniqueId.let {
                    DataBaseManager.remove(it)
                    sender.sendInfoMsg("&c删除成功!")
                }
            }
        }
    }

    val lookBind = subCommand {
        dynamic("player", optional = true) {
            execute<CommandSender> { sender, context, _ ->
                val player = Bukkit.getOfflinePlayer(context["player"])
                val qq = DataBaseManager.getQQ(player.uniqueId)
                sender.sendInfoMsg("&7玩家&e${player.name}&7绑定的QQ为: &c${qq}!")
            }
        }
    }


    val lookAllBind = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            sender.sendInfoMsg("已经绑定的玩家数据")
            DataBaseManager.getPlayerData().map {
                val player = it.key.parseUUID()?.let { it1 -> Bukkit.getOfflinePlayer(it1) }
                val qq = it.value
                Components.empty()
                    .append("&7  - &a${player?.name} &6| &b$qq".colored())
                    .hoverText("&7点击删除此绑定".colored())
                    .clickRunCommand("/mgc removeBind ${player?.name}")
                    .sendTo(sender)
            }
        }
    }



}