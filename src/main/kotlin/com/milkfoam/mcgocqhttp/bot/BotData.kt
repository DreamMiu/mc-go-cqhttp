package com.milkfoam.mcgocqhttp.bot


data class BotData(
    val post_type: String,
    val message_type: String,
    val time: String,
    val self_id: String,
    val sub_type: String?,
    val message_id: String,
    val user_id: String,
    val target_id: String?,
    val message: String,
    val raw_message: String, //消息
    val font: String,
    val message_seq: String?,
    val group_id: String?,
    val sender: Sender
)

data class Sender(
    val age: String,
    val area: String?,
    val card: String?,
    val level: String?,
    val nickname: String,
    val role: String?,
    val sex: String,
    val title: String?,
    val user_id: String
)

data class GroupMember(
    val age: Int,
    val area: String,
    val card: String,
    val card_changeable: Boolean,
    val group_id: Long,
    val join_time: Long,
    val last_sent_time: Long,
    val level: String,
    val nickname: String,
    val role: String,
    val sex: String,
    val shut_up_timestamp: Long,
    val title: String,
    val title_expire_time: Long,
    val unfriendly: Boolean,
    val user_id: Long
)

data class UserData(
    val age: Int,
    val level: Int,
    val login_days: Int,
    val nickname: String,
    val qid: String,
    val sex: String,
    val sign: String,
    val user_id: Long,
    val vip_level: String
)