package com.github.theholychicken.config

import com.github.theholychicken.GoodMod.Companion.mc
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

object GuiConfig {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    var commandNames = mutableMapOf<String, String>()
    var useSellOffer = false
    var api: String = "HypixelApi"
    var renderMainCroesusGui = true
    var minChestPurchase = 0

    // .apply{} defines config file initiation protocol
    private val configFile = File(mc.mcDataDir, "config/goodmod/goodmod.json").apply {
        try {
            parentFile.mkdirs()
            createNewFile()
        } catch (_: Exception) {
            println("Error initializing config")
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
            // temporary fix to provide backwards compatability
            if ("useSellOffer" !in commandNames.keys || "api" !in commandNames.keys || "renderMainCroesusGui" !in commandNames.keys || "minChestPurchase" !in commandNames.keys) {
                saveConfig()
            }
            useSellOffer = commandNames["useSellOffer"].toBoolean()
            api = commandNames["api"].toString()
            renderMainCroesusGui = commandNames["renderMainCroesusGui"].toBoolean()
            minChestPurchase = commandNames["minChestPurchase"]?.toInt() ?: 0
            commandNames.remove("useSellOffer")
            commandNames.remove("api")
            commandNames.remove("renderMainCroesusGui")
        }  catch (e: Exception) {
            println(e.message)
        }
    }

    fun saveConfig() {
        try {
            commandNames.put("useSellOffer", useSellOffer.toString())
            commandNames.put("api", api)
            commandNames.put("renderMainCroesusGui", renderMainCroesusGui.toString())
            commandNames.put("minChestPurchase", minChestPurchase.toString())
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
        commandNames["updateAuctions"] = "updateAuctions"
        saveConfig()
    }

}