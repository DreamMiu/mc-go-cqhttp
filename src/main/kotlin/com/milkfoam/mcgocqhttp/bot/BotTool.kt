package com.milkfoam.mcgocqhttp.bot

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.milkfoam.mcgocqhttp.api.event.QQMessageEvent
import com.milkfoam.mcgocqhttp.event.BindEvent
import java.awt.SystemColor.text


object BotTool {

    private lateinit var data: BotData

    fun parse(json: String) {

        val a: JSONObject? = JSON.parseObject(json)
        if (a?.getString("post_type") == "message") {
            data = JSON.parseObject(json, BotData::class.java)
            //println(data)
            val event = QQMessageEvent(json, data)
            event.call()
            if (!event.isCancelled) {
                BindEvent.bind(data)
            }
        }
    }


}