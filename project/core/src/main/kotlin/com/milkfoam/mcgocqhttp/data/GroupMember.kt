package com.milkfoam.mcgocqhttp.data

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
