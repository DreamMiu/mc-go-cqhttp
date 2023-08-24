package com.milkfoam.mcgocqhttp

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.milkfoam.mcgocqhttp.api.McGoCqHttpAPI
import com.milkfoam.mcgocqhttp.api.event.QQMessageEvent
import com.milkfoam.mcgocqhttp.data.BotData
import com.milkfoam.mcgocqhttp.event.BindEvent
import com.milkfoam.mcgocqhttp.service.Updater
import com.milkfoam.mcgocqhttp.service.WebSocketUtils
import com.milkfoam.mcgocqhttp.utils.consoleErrorMsg
import com.milkfoam.mcgocqhttp.utils.consoleInfoMsg
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.submitAsync
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration


object McGoCqHttp : Plugin() {

    @Config(migrate = true, value = "settings.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    @Config(migrate = true, value = "storage.yml", autoReload = true)
    lateinit var storage: Configuration
        private set

    /**
     * 绑定模块配置文件
     */
    var groupIDList: MutableList<String> = mutableListOf()
    lateinit var groupInputKey: String
    var actions: MutableList<String> = mutableListOf()
    lateinit var level: String
    lateinit var expirationTime: String

    /**
     * 连接模块配置文件
     */
    lateinit var webSocketUrl: String
    lateinit var webSocketHttp: String

    /**
     * 远程控制模块配置文件
     */
    var admins: MutableList<String> = mutableListOf()
    var consoleToolEnable = false
    lateinit var consoleToolKeyWords: String

    /**
     * 白名单模块配置文件
     */
    var whiteListEnable: Boolean = true
    var whiteListCancelJoin: Boolean = true
    var whiteListCancelMove: Boolean = true
    var whiteListCancelChat: Boolean = true
    var whiteListCancelCommand: Boolean = true
    lateinit var whiteListMessage: String

    /**
     * 更新模块配置文件
     */
    private var checkUpdate: Boolean = true

    /**
     * 数据模块配置文件
     */
    lateinit var data: BotData

    /**
     * 互通模块配置文件
     */
    var connectEnable = false
    var connectGroup: MutableList<String> = mutableListOf()
    var mctoqqEnable = false
    lateinit var mctoqqKeyWords: String
    lateinit var mctoqqText: String
    var qqtomcEnable = false
    lateinit var qqtomcKeyWords: String
    lateinit var qqtomcKeyText: String

    var authTokenEnable = false
    var authToken = ""

    /**
     * 插件启动完成的动作
     */
    override fun onActive() {
        loadConfig()
        runBot()
    }

    /**
     * 读取配置文件
     */
    fun loadConfig() {

        groupIDList = config.getStringList("module.bind.groupBind.list").toMutableList()
        groupInputKey = config.getString("module.bind.groupBind.inputKey") ?: "#绑定账号"
        actions = config.getStringList("module.bind.actions").toMutableList()
        level = config.getString("module.bind.level", "1")!!
        expirationTime = config.getString("module.bind.expirationTime", "30")!!

        webSocketUrl = config.getString("go-cqhttp.WebSocket") ?: ""
        webSocketHttp = config.getString("go-cqhttp.Http") ?: ""

        checkUpdate = config.getBoolean("Check-Update")
        if (checkUpdate) {
            submitAsync(delay = 20, period = 15 * 60 * 20) {
                Updater.grabInfo()
            }
        }

        whiteListEnable = config.getBoolean("module.whitelist.enable")
        whiteListCancelJoin = config.getBoolean("module.whitelist.cancelJoin")
        whiteListCancelMove = config.getBoolean("module.whitelist.cancelMove")
        whiteListCancelChat = config.getBoolean("module.whitelist.cancelChat")
        whiteListCancelCommand = config.getBoolean("module.whitelist.cancelCommand")
        whiteListMessage = config.getString("module.whitelist.message")!!

        //通信模块
        connectGroup = config.getStringList("module.connect.group").toMutableList()
        mctoqqEnable = config.getBoolean("module.connect.mc-qq.keywords.enable")
        mctoqqKeyWords = config.getString("module.connect.mc-qq.keywords.text")!!
        mctoqqText = config.getString("module.connect.mc-qq.text")!!
        qqtomcEnable = config.getBoolean("module.connect.qq-mc.keywords.enable")
        qqtomcKeyWords = config.getString("module.connect.qq-mc.keywords.text")!!
        qqtomcKeyText = config.getString("module.connect.qq-mc.text")!!
        connectEnable = config.getBoolean("module.connect.enable")

        //指令模块
        admins = config.getStringList("module.consoleTool.admin").toMutableList()
        consoleToolEnable = config.getBoolean("module.consoleTool.enable")
        consoleToolKeyWords = config.getString("module.consoleTool.keywords")!!

        //连接密钥
        authTokenEnable = config.getBoolean("go-cqhttp.AuthToken.enable")
        authToken = config.getString("go-cqhttp.AuthToken.value") ?: ""


    }

    /**
     * 连接机器人
     */
    fun runBot() {
        WebSocketUtils(webSocketUrl, object : WebSocketListener() {

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                consoleInfoMsg("&ago-cqhttp断开连接!")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                consoleErrorMsg("&cgo-cqhttp出现错误!")
                println(t)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val jSONObject = JSON.parseObject(text)
                if (jSONObject?.getString("post_type") == "message") {
                    data = JSON.parseObject(text, BotData::class.java)
                    val event = QQMessageEvent(text, data)
                    event.call()
                    if (!event.isCancelled) {
                        BindEvent.bind(data)
                    }
                }
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                consoleInfoMsg("&ago-cqhttp连接成功!")
            }

        })
    }


}