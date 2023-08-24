package com.milkfoam.mcgocqhttp.data


data class BotData(
    val post_type: String,//上报类型
    val message_type: String,//消息类型
    val time: String,//发送时间
    val self_id: String,//机器人本身id
    val sub_type: String?,//提交类型
    val message_id: String,//消息id
    val user_id: String,//发送者id
    val target_id: String?,//响应id
    val message: String,//消息
    val raw_message: String,//真实消息
    val font: String,//字体
    val message_seq: String?,
    val group_id: String?,//群号
    val sender: Sender//发送者详细信息
)