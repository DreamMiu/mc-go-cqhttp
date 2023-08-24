@file:Suppress("PropertyName", "SpellCheckingInspection")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val taboolib_version: String by project

val kotlinVersionNum: String
    get() = project.kotlin.coreLibrariesVersion.replace(".", "")

plugins {
    java
    id("org.jetbrains.kotlin.jvm") version "1.5.20"
    id("com.github.johnrengelman.shadow") version "7.1.1" apply false
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        google()
        maven { url = uri("https://repo.spongepowered.org/maven") }
        maven {
            url = uri("http://ptms.ink:8081/repository/releases/")
            isAllowInsecureProtocol = true
        }
        maven("https://maven.aliyun.com/nexus/content/groups/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/public")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://jitpack.io")

    }

    dependencies {
        compileOnly(kotlin("stdlib"))

        implementation("io.izzel.taboolib:common:$taboolib_version")
        implementation("io.izzel.taboolib:common-5:$taboolib_version")
        implementation("io.izzel.taboolib:module-chat:$taboolib_version")
        implementation("io.izzel.taboolib:module-configuration:$taboolib_version")
        implementation("io.izzel.taboolib:module-database:$taboolib_version")
        implementation("io.izzel.taboolib:module-lang:$taboolib_version")
        implementation("io.izzel.taboolib:module-nms:$taboolib_version")
        implementation("io.izzel.taboolib:module-nms-util:$taboolib_version")
        implementation("io.izzel.taboolib:module-kether:$taboolib_version")
        implementation("io.izzel.taboolib:module-ui:$taboolib_version")
        implementation("io.izzel.taboolib:platform-bukkit:$taboolib_version")
        implementation("io.izzel.taboolib:expansion-command-helper:$taboolib_version")

        implementation("com.squareup.okio:okio-jvm:3.0.0")
        implementation("com.squareup.okhttp3:okhttp:4.10.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

        implementation("com.alibaba.fastjson2:fastjson2:2.0.35")
    }


    // =============================
    //       下面的东西不用动
    // =============================
    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
        }
    }
    // 这里不要乱改
    tasks.withType<ShadowJar> {
        // 重定向 TabooLib
        relocate("taboolib", "${rootProject.group}.taboolib")
        // 重定向 Kotlin
        relocate("kotlin.", "kotlin${kotlinVersionNum}.") { exclude("kotlin.Metadata") }

        // =============================
        //     如果你要重定向就在下面加
        // =============================
        relocate("com.alibaba.fastjson2", "com.milkfoam.mcgocqhttp.library.fastjson2")
        relocate("okkttp3", "com.milkfoam.mcgocqhttp.library.okkttp3")
        relocate("okkttp3.logging", "com.milkfoam.mcgocqhttp.library.okkttp3.logging")
        relocate("okio", "com.milkfoam.mcgocqhttp.library.okio")
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}