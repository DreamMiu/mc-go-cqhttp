import io.izzel.taboolib.gradle.RelocateJar.relocate

plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.56"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {

    description {
        contributors {
            name("MilkFoam")
        }
        dependencies {
            name("PlaceholderAPI").with("bukkit").optional(true)
            name("Vault").with("bukkit").optional(true)
        }
    }

    install("common")
    install("common-5")
    install("module-chat")
    install("module-configuration")
    install("module-database")
    install("module-kether")
    install("module-lang")
    install("module-nms")
    install("module-nms-util")
    install("module-ui")
    install("platform-bukkit")
    install("expansion-command-helper")
    classifier = null
    version = "6.0.11-27"

}

//Most of the following content comes from NeigeItems
//https://github.com/ankhorg/NeigeItems-Kotlin/blob/main/build.gradle.kts
repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
    // taboo的仓库有时候github自动构建连不上, 丢到最后防止自动构建发生意外
    maven("https://repo.tabooproject.org/storages/public/releases")
}

dependencies {

    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v12001:12001-minimize:mapped")
    compileOnly("ink.ptms.core:v12001:12001-minimize:universal")

    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))

    compileOnly("com.squareup.okio:okio-jvm:3.0.0")
    compileOnly("com.squareup.okhttp3:okhttp:4.10.0")
    compileOnly("com.squareup.okhttp3:logging-interceptor:4.10.0")

    compileOnly("com.alibaba.fastjson2:fastjson2:2.0.35")

}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            groupId = project.group.toString()
        }
    }
}