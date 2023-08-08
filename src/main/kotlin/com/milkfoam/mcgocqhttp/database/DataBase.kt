package com.milkfoam.mcgocqhttp.database

import taboolib.module.database.Host
import taboolib.module.database.Table
import java.util.*
import javax.sql.DataSource


abstract class DataBase {

    abstract val host: Host<*>

    abstract val userTable: Table<*, *>

    abstract val source: DataSource

    fun getQQ(uniqueId: UUID): String {
        val table = userTable
        return table.select(source) {
            rows("qq")
            where { "uuid" eq uniqueId.toString() }
            limit(1)
        }.firstOrNull {
            this.getString("qq")
        } ?: let {
            "未绑定"
        }
    }

    fun checkQQ(qq: String): Boolean {
        val table = userTable
        return table.find(source) {
            rows("qq")
            where { "qq" eq qq }
        }
    }

    fun getPlayer(qq: String): String {
        val table = userTable
        return table.select(source) {
            rows("uuid")
            where { "qq" eq qq }
            limit(1)
        }.firstOrNull {
            this.getString("uuid")
        } ?: let {
            "未找到"
        }
    }

    fun getPlayerData(): MutableMap<String, String> {
        val table = userTable
        val dataMap = mutableMapOf<String, String>()
        table.select(source) {
            rows("uuid", "qq")
        }.forEach {
            val uuid = getString("uuid")
            val qq = getString("qq")
            dataMap[uuid] = qq
        }
        return dataMap
    }

    fun getQQList(): MutableList<String> {
        val table = userTable
        val dataList = mutableListOf<String>()
        table.select(source) {
            rows("qq")
        }.firstOrNull {
            dataList.add(getString("qq"))
        }
        return dataList
    }

    fun saveQQ(uniqueId: UUID, data: String) {
        val query = userTable.find(source) { where { "uuid" eq uniqueId.toString() } }
        if (query) {
            userTable.update(source) {
                where { "uuid" eq uniqueId.toString() }
                set("qq", data)
            }
        } else {
            userTable.insert(source, "uuid", "qq") {
                value(uniqueId.toString(), data)
            }
        }
    }

    fun removeQQ(uniqueId: UUID) {
        userTable.delete(source) {
            where { "uuid" eq uniqueId.toString() }
        }
    }

    fun removeQQ(qq: String) {
        userTable.delete(source) {
            where { "qq" eq qq }
        }
    }

}