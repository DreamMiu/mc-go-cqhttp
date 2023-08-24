package com.milkfoam.mcgocqhttp.utils

import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.kether.KetherShell.eval
import taboolib.module.kether.ScriptOptions
import taboolib.module.kether.runKether

fun runKether(player: Player, text: String) {
    runKether {
        eval(
            text,
            ScriptOptions.builder().namespace(namespace = listOf("ApoOnlineReward"))
                .sender(sender = adaptPlayer(player)).build()
        )
    }
}

fun runKetherList(player: Player, list: List<String>) {
    runKether {
        eval(
            list,
            ScriptOptions.builder().namespace(namespace = listOf("ApoOnlineReward"))
                .sender(sender = adaptPlayer(player)).build()
        )
    }
}
