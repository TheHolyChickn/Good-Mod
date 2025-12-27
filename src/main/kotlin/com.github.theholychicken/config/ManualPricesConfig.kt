package com.github.theholychicken.config

import com.github.theholychicken.GoodMod
import com.github.theholychicken.GoodMod.Companion.logger
import java.io.File
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.managers.SellableItemParser
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Config backend for manually setting sell prices for all items
 * thank you 15h for demanding that i add this
 */
object ManualPricesConfig {
    private val configFile = File(mc.mcDataDir, "config/goodmod/manualprices.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            println(e.message)
        }
    }
    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    var manualPrices: MutableMap<String, Double> = mutableMapOf()

    fun loadConfig() {
        try {
            with(configFile.bufferedReader().use { it.readText() }) {
                //if (this == "") return
                manualPrices = gson.fromJson(
                    this,
                    object : TypeToken<MutableMap<String, Double>>() {}.type
                ) ?: mutableMapOf()
            }
            if (manualPrices.isEmpty()) {
                initConfig()
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun saveConfig() {
        try {
            configFile.bufferedWriter().use { it.write(gson.toJson(manualPrices)) }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun initConfig() {
        logger.info("Initializing manual prices config file")
        SellableItemParser.items.forEach {
            manualPrices[it.displayName] = 0.0
        }
        SellableItemParser.shinyItems.forEach {
            manualPrices[it] = 0.0
        }
        saveConfig()
    }
}