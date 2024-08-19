package com.github.theholychicken.config

import com.github.theholychicken.GoodMod.Companion.mc
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

object GuiConfig {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    var commandNames = mutableMapOf<String, String>()

    // .apply{} defines config file initiation protocol
    private val configFile = File(mc.mcDataDir, "config/goodmod/goodmod.json").apply {
        try {
            parentFile.mkdirs()
            createNewFile()
        } catch (e: Exception) {
            println("Error initializing personal bests config")
        }
    }

    fun loadConfig() {
        try {
            with(configFile.bufferedReader().use { it.readText() }) {
                if (this == "") return

                commandNames = gson.fromJson(
                    this,
                    object : TypeToken<MutableMap<String, String>>() {}.type
                )
                println("Successfully loaded pb config $commandNames")
            }
        }  catch (e: Exception) {
            println(e.message)
        }
    }

    fun saveConfig() {
        try {
            configFile.bufferedWriter().use {
                it.write(gson.toJson(commandNames))
                println("Successfully saved config")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun initConfig() {
        commandNames["goodmod"] = "goodmod"
        commandNames["getItems"] = "getItems"
        commandNames["reloadLoot"] = "goodmod:dev_commands:furry"
        saveConfig()
    }

}