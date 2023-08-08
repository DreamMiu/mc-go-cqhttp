package com.milkfoam.mcgocqhttp.api.event

import org.bukkit.OfflinePlayer
import taboolib.platform.type.BukkitProxyEvent

class PlayerBindQQEvent(
    val qq: String,
    val name: String
) : BukkitProxyEvent() {

}