package com.github.theholychicken.config

import java.io.File
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.managers.SellableItemParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Config backend for the sell pricing of bz items
 * saves item together with whether it should use sell offer or instasell
 *
 * @property sellPrices Given any item i, sellPrices.get(i) is true if i should use sell offers, and false otherwise
 */
object SellPricesConfig {
    private val configFile = File(mc.mcDataDir, "config/goodmod/sellprices.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            println(e.message)
        }
    }
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    var sellPrices: MutableMap<String, Boolean> = mutableMapOf()

    fun loadConfig() {
        try {
            with(configFile.bufferedReader().use { it.readText() }) {
                if (this == "") return
                sellPrices = gson.fromJson(
                    this,
                    object : TypeToken<MutableMap<String, Boolean>>() {}.type
                )
            }
            if (sellPrices.keys.isEmpty()) initConfig()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun saveConfig() {
        try {
            configFile.bufferedWriter().use { it.write(gson.toJson(sellPrices)) }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun initConfig() {
        SellableItemParser.SellableItem.entries.filter {
            it.sellType == SellableItemParser.SellableItem.SellType.BAZAAR
        }.forEach {
            sellPrices[it.name] = false
        }
        saveConfig()
    }
}