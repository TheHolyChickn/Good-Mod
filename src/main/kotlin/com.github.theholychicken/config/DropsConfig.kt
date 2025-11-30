package com.github.theholychicken.config

import com.github.theholychicken.GoodMod
import com.github.theholychicken.GoodMod.Companion.mc
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.*

class DropsConfig {
    private val configFile = File(mc.mcDataDir, "config/goodmod/drops.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            print(e.message)
        }
    }
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private var itemDrops: MutableMap<String, Int> = LinkedHashMap()

    fun loadConfig() {
        GoodMod.logger.info("Beginning DropsConfig loadConfig task")

        try {
            with(configFile.bufferedReader().use { it.readText() }) {
                if (this == "") return
                itemDrops = gson.fromJson(
                    this,
                    object : TypeToken<MutableMap<String, Int>>() {}.type
                )
                println("Successfully loaded itemDrops config $itemDrops")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun saveConfig() {
        try {
            configFile.bufferedWriter().use {
                it.write(gson.toJson(itemDrops))
                GoodMod.logger.info("Successfully loaded itemDrops config $itemDrops")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun addItem(item: String) {
        itemDrops[item] = itemDrops.getOrDefault(item, 0) + 1
        saveConfig()
    }

    fun getItemCount(item: String): Int {
        return itemDrops.getOrDefault(item, 0)
    }

    fun addMany(item: String, count: Int) {
        itemDrops[item] = itemDrops.getOrDefault(item, 0) + count
        saveConfig()
    }

    fun generateItem(item: String) {
        itemDrops[item] = itemDrops.getOrDefault(item, 0)
        saveConfig()
    }

    fun getList(): MutableMap<String, Int> {
        return itemDrops
    }
}