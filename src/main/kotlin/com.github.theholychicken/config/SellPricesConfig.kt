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

    var sellPrices: MutableMap<String, Boolean> = mutableMapOf()
    private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
    private val CONFIG_FILE = File(mc.mcDataDir, "config/goodmod/sellprices.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun loadConfig() {
        try {
            with(CONFIG_FILE.bufferedReader().use { it.readText() }) {
                if (this == "") return
                sellPrices = GSON.fromJson(
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
            CONFIG_FILE.bufferedWriter().use { it.write(GSON.toJson(sellPrices)) }
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