package com.github.theholychicken.config

import com.github.theholychicken.GoodMod
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*

class DropsConfig {
    private var itemDrops: MutableMap<String, Int> = LinkedHashMap()

    fun loadConfig() {
        GoodMod.logger.info("Beginning AwA loadConfig task")

        try {
            if (CONFIG_FILE.exists()) {
                val reader = FileReader(CONFIG_FILE)
                val config = GSON.fromJson(reader, DropsConfig::class.java)
                reader.close()
                if (config != null) {
                    this.itemDrops = config.itemDrops
                    GoodMod.logger.info("Config loaded: " + this.itemDrops)
                } else GoodMod.logger.warn("Loaded config is null.")

            } else {
                GoodMod.logger.info("Config file does not exist, creating new one.")
                saveConfig()
            }
        } catch (e: IOException) {
            GoodMod.logger.error("Failed to load config file", e)
            e.printStackTrace()
        }
    }

    private fun saveConfig() {
        try {
            val writer = FileWriter(CONFIG_FILE)
            GSON.toJson(this, writer)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
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

    companion object {
        private val CONFIG_FILE = File("com.github.theholychicken/config/drops.json")
        private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
    }
}