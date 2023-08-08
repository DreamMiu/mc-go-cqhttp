package com.milkfoam.mcgocqhttp.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.chat.colored

fun Player.sendInfoMsg(text: String) {
    this.sendMessage("&7[&amc-go-cqhttp&7] $text".colored())
}

fun Player.sendErrorMsg(text: String) {
    this.sendMessage("&7[&cmc-go-cqhttp&7] $text".colored())
}

fun consoleInfoMsg(text: String) {
    console().sendMessage("&7[&amc-go-cqhttp&7] $text".colored())
}

fun consoleErrorMsg(text: String) {
    console().sendMessage("&7[&cmc-go-cqhttp&7] $text".colored())
}

fun CommandSender.sendInfoMsg(text: String) {
    this.sendMessage("&7[&amc-go-cqhttp&7] $text".colored())
}

fun CommandSender.sendErrorMsg(text: String) {
    this.sendMessage("&7[&cmc-go-cqhttp&7] $text".colored())
}

fun ProxyPlayer.sendInfoMsg(text: String) {
    this.sendMessage("&7[&amc-go-cqhttp&7] $text".colored())
}


fun sendAllPlayer(text: String) {
    onlinePlayers().forEach {
        it.sendMessage("&7[&cmc-go-cqhttp&7] $text".colored())
    }
}