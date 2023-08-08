package com.milkfoam.mcgocqhttp.api.event

import com.milkfoam.mcgocqhttp.bot.BotData
import taboolib.platform.type.BukkitProxyEvent

class QQMessageEvent(
    val message: String,
    val data: BotData
) : BukkitProxyEvent()