package com.milkfoam.mcgocqhttp.command.subcommand

import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.bot.get.GetGroupInfo
import com.milkfoam.mcgocqhttp.database.DataBaseManager
import com.milkfoam.mcgocqhttp.utils.consoleInfoMsg
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand

object Check {

    val check = subCommand {
        execute<CommandSender> { _, _, _ ->
            McGoCqHttp.resetGroupList.forEach { groupId ->
                val gameList = DataBaseManager.getQQList().toMutableList()
                val originalGameList = gameList.toList()
                val groupMemberList = GetGroupInfo().getGroupMemberList(groupId)
                val groupMemberUserIdList = groupMemberList.map { it.user_id.toString() }

                gameList.retainAll(groupMemberUserIdList.toSet())

                val removedData = originalGameList - gameList.toSet()
                consoleInfoMsg("成功移除${removedData.size}个账号")
                removedData.forEach {
                    consoleInfoMsg("  - $it | ${DataBaseManager.getPlayer(it)}")
                    DataBaseManager.remove(it)
                }
            }

        }
    }

}