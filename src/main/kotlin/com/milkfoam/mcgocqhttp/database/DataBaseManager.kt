package com.milkfoam.mcgocqhttp.database

import com.milkfoam.mcgocqhttp.McGoCqHttp.storage
import java.util.UUID

object DataBaseManager {

    private val dataBase: DataBase = if (
        storage.getBoolean("SQL.enable")) {
        DataBaseSQL()
    } else {
        DataBaseSQLite()
    }

    internal fun getQQ(uniqueId: UUID): String {
        return dataBase.getQQ(uniqueId)
    }

    internal fun checkQQ(qq: String): Boolean {
        return dataBase.checkQQ(qq)
    }

    internal fun getPlayer(qq: String): String {
        return dataBase.getPlayer(qq)
    }

    internal fun getQQList(): MutableList<String> {
        return dataBase.getQQList()
    }

    internal fun getPlayerData(): MutableMap<String, String> {
        return dataBase.getPlayerData()
    }

    internal fun saveQQ(uniqueId: UUID, data: String) {
        dataBase.saveQQ(uniqueId, data)
    }

    internal fun remove(uniqueId: UUID) {
        dataBase.removeQQ(uniqueId)
    }

    internal fun remove(qq: String) {
        dataBase.removeQQ(qq)
    }


}