package com.github.theholychicken.config

import java.io.File
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.managers.SellableItemParser
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Config backend for pricing preferences of all items
 *
 * @property prices Maps itemIds to a PricePreference object containing their pricing logic
 * @property PricePreference useful object for saving price preference data in a very persistent and compact way
 */
object SellPricesConfig {
    private val configFile = File(mc.mcDataDir, "config/goodmod/sellprices.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            println(e.message)
        }
    }
    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(PricePreference::class.java, PricePreferenceAdapter())
        .create()
    var sellPrices: MutableMap<String, Boolean> = mutableMapOf()
    var prices: MutableMap<String, PricePreference> = mutableMapOf()

    fun loadConfig() {
        try {
            with(configFile.bufferedReader().use { it.readText() }) {
                if (this.isBlank()) {
                    initConfig()
                    return
                }
                val type = object : TypeToken<Map<String, PricePreference>>() {}.type
                prices = gson.fromJson(this, type) ?: mutableMapOf()

                ensureAllItemsExist()
                absorbManualPrices()
                saveConfig()
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun saveConfig() {
        try {
            configFile.bufferedWriter().use { it.write(gson.toJson(prices)) }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun initConfig() {
        SellableItemParser.items.forEach {
            prices[it.displayName] = PricePreference()
        }
        SellableItemParser.shinyItems.forEach {
            prices[it] = PricePreference()
        }
        saveConfig()
    }

    private fun ensureAllItemsExist() {
        SellableItemParser.items.forEach {
            if (!prices.containsKey(it.displayName)) {
                prices[it.displayName] = PricePreference()
            }
        }
        SellableItemParser.shinyItems.forEach {
            if (!prices.containsKey(it)) {
                prices[it] = PricePreference()
            }
        }
    }

    enum class PriceSource {
        MANUAL, API
    }

    enum class ApiPricing {
        INSTASELL, SELL_OFFER
    }

    data class PricePreference(
        var source: PriceSource = PriceSource.API,
        var apiPricing: ApiPricing = ApiPricing.SELL_OFFER,
        var manualValue: Double = 0.0
    )

    // gson adapter for PricePreference
    // also handles some migration logic from the old format
    class PricePreferenceAdapter : JsonDeserializer<PricePreference> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): PricePreference {
            // old format was boolean, true = instasell
            // new format is via PricePreference objects
            if (json.isJsonPrimitive && json.asJsonPrimitive.isBoolean) {
                return PricePreference(
                    source = PriceSource.API, // add check for manual pricing
                    apiPricing = if (json.asBoolean) ApiPricing.INSTASELL else ApiPricing.SELL_OFFER,
                    manualValue = 0.0
                )
            }
            // standard object
            else if (json.isJsonObject) {
                val obj = json.asJsonObject
                return PricePreference(
                    source = PriceSource.valueOf(obj["source"]?.asString ?: "API"),
                    apiPricing = ApiPricing.valueOf(obj["apiPricing"]?.asString ?: "SELL_OFFER"),
                    manualValue = obj["manualValue"]?.asDouble ?: 0.0
                )
            }
            return PricePreference()
        }
    }

    // migration logic
    private fun absorbManualPrices() {
        val manualPricesConfigFile = File(mc.mcDataDir, "config/goodmod/manualprices.json")
        if (!manualPricesConfigFile.exists()) return

        try {
            val type = object : TypeToken<MutableMap<String, Double>>() {}.type
            val manualMap: MutableMap<String, Double> = gson.fromJson(manualPricesConfigFile.readText(), type) ?: return

            manualMap.forEach { (name, value) ->
                // -1 meant api pricing
                if (value != -1.0) {
                    val pref = prices.getOrPut(name) { PricePreference() }
                    pref.source = PriceSource.MANUAL
                    pref.manualValue = value
                }
            }

            manualPricesConfigFile.renameTo(File(mc.mcDataDir, "config/goodmod/MANUAL-PRICES-DEPRECATED.json"))
        } catch (e: Exception) {
            println(e.message)
        }
    }
}