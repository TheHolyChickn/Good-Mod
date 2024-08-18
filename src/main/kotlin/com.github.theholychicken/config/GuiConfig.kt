package com.github.theholychicken.config

import com.github.theholychicken.GoodMod
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*

object GuiConfig {
    private val GSON_INSTANCE: Gson = GsonBuilder().setPrettyPrinting().create()
    private val CONFIG_FILE = File("com.github.theholychicken/config/goodmod.json")

    private var goodModInstance: GoodMod? = null

    fun loadGuiConfig() {
        if (CONFIG_FILE.exists()) {
            try {
                FileReader(CONFIG_FILE).use { reader ->
                    goodModInstance = GSON_INSTANCE.fromJson(
                        reader,
                        GoodMod::class.java
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            goodModInstance = GoodMod()
            saveMeow()
        }
        GoodMod.logger.info("Meower loaded")
    }

    fun saveMeow() {
        try {
            FileWriter(CONFIG_FILE).use { writer ->
                GSON_INSTANCE.toJson(goodModInstance, writer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}