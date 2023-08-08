package com.milkfoam.mcgocqhttp.task

import com.milkfoam.mcgocqhttp.McGoCqHttp
import com.milkfoam.mcgocqhttp.bot.get.GetGroupInfo
import com.milkfoam.mcgocqhttp.database.DataBaseManager
import com.milkfoam.mcgocqhttp.utils.consoleInfoMsg
import taboolib.common.platform.function.submit
import java.util.*

class ResetTask {

    fun start() {
        submit(period = 200L, async = true) {
            val current = McGoCqHttp.Time.format(Date()).split("/")
            if (current[1] == McGoCqHttp.resetTime) {
                if (McGoCqHttp.resetOriginalDay == null || McGoCqHttp.resetOriginalDay != current[0]) {
                    McGoCqHttp.resetOriginalDay = current[0]

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
    }

}