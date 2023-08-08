package com.milkfoam.mcgocqhttp

import com.milkfoam.mcgocqhttp.bot.BotTool
import com.milkfoam.mcgocqhttp.task.ResetTask
import com.milkfoam.mcgocqhttp.utils.WebSocketUtils
import com.milkfoam.mcgocqhttp.utils.consoleErrorMsg
import com.milkfoam.mcgocqhttp.utils.consoleInfoMsg
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import java.text.SimpleDateFormat


@RuntimeDependencies(
    RuntimeDependency(
        value = "!com.squareup.okhttp3:okhttp:4.10.0",
        test = "!okhttp3",
        relocate = ["!kotlin.", "!kotlin@kotlin_version_escape@."]
    ),
    RuntimeDependency(
        value = "!com.squareup.okhttp3:logging-interceptor:4.10.0",
        test = "!okhttp3.logging",
        relocate = ["!kotlin.", "!kotlin@kotlin_version_escape@."]
    ),
    RuntimeDependency(
        value = "!com.squareup.okio:okio-jvm:3.0.0",
        test = "!okio",
        relocate = ["!kotlin.", "!kotlin@kotlin_version_escape@."]
    ),
    RuntimeDependency(
        value = "!com.alibaba.fastjson2:fastjson2:2.0.35",
        test = "!com.alibaba.fastjson2"
    )
)
object McGoCqHttp : Plugin() {

    //创建配置
    @Config(migrate = true, value = "settings.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    //创建数据库配置
    @Config(migrate = true, value = "storage.yml", autoReload = true)
    lateinit var storage: Configuration
        private set

    /**
     * 绑定模块
     */
    var groupIDList: MutableList<String> = mutableListOf()
    var actions: MutableList<String> = mutableListOf()
    lateinit var groupInputKey: String
    lateinit var level: String

    /**
     * 连接模块
     */
    private var webSocketUtils: WebSocketUtils? = null
    private lateinit var webSocketUrl: String
    lateinit var webSocketHttp: String

    /**
     * 重置模块
     */
    val Time = SimpleDateFormat("yyyy-MM-dd/HH:mm")
    var resetOriginalDay: String? = null
    var resetTime: String = ""
    var resetGroupList: MutableList<String> = mutableListOf()

    /**
     * QQ-MC通信模块
     */
    private var group: MutableList<String> = mutableListOf()
    private var mctoqqEnable = false
    private var mctoqqKeyWords = ""
    private var mctoqqText = ""
    private var qqtomcEnable = false
    private var qqtomcKeyWords = ""
    private var qqtomcKeyText = ""


    override fun onActive() {
        loadConfig()
        runBot()
        ResetTask().start()
    }

    //加载配置文件
    fun loadConfig() {

        groupIDList = config.getStringList("module.bind.groupBind.list").toMutableList()
        groupInputKey = config.getString("module.bind.groupBind.inputKey") ?: "#绑定账号"

        webSocketUrl = config.getString("go-cqhttp.WebSocket") ?: ""
        webSocketHttp = config.getString("go-cqhttp.Http") ?: ""

        actions = config.getStringList("module.bind.actions").toMutableList()

        resetTime = config.getString("module.check.time", "00:00")!!
        resetGroupList = config.getStringList("module.check.list").toMutableList()

        level = config.getString("module.bind.level", "1")!!

    }

    fun runBot() {
        webSocketUtils = WebSocketUtils(webSocketUrl, object : WebSocketListener() {
            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                consoleInfoMsg("&ago-cqhttp断开连接!")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                consoleErrorMsg("&cgo-cqhttp出现错误!")
                println(t)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                //println(text)
                //将JSON解析为BotData对象
                BotTool.parse(text)
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                consoleInfoMsg("&ago-cqhttp连接成功!")
            }
        })
    }


}