package com.github.theholychicken.managers.apiclients

import com.github.theholychicken.managers.SellableItemParser
import com.github.theholychicken.utils.modMessage
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

object TrickedApiClient : ApiClient {
    private val gson = Gson()

    // to minimize api calls to reduce strain on tricked
    // we use lowestbins and scan the json instead of queuing
    // each item individually
    override suspend fun fetchAllAuctions() = withContext(Dispatchers.IO) {
        val jsonData = fetchAllPrices()

        SellableItemParser.items.forEach {
            if (it.sellType == SellableItemParser.SellableItem.SellType.AUCTION) {
                when (it.name) {
                    "PET-SPIRIT-EPIC" -> {
                        SellableItemParser.updateAuction(it.displayName, jsonData.get(it.name).asDouble, "EPIC")
                    }
                    "PET-SPIRIT-LEGENDARY" -> {
                        SellableItemParser.updateAuction(it.displayName, jsonData.get(it.name).asDouble, "LEGENDARY")
                    }
                    else -> {
                        SellableItemParser.updateAuction(it.displayName, jsonData.get(it.name).asDouble)
                    }
                }
            } else if (it.sellType == SellableItemParser.SellableItem.SellType.BAZAAR) {
                val itemTag = it.name.replace(Regex("^ENCHANTMENT_(.*)_(\\d)$"), "ENCHANTED_BOOK-$1-$2")
                // this api stores enchanted books with a slitghtly diff id
                try {
                    SellableItemParser.updateBazaar(it.name, jsonData.get(itemTag).asDouble)
                } catch (e: Exception) {
                    modMessage("Parsing error on $itemTag: ${e.message}")
                }
            }
            sleep(25)
        }

        SellableItemParser.saveToFile()
        modMessage("Successfully fetched all auctions.")
    }

    private fun fetchAllPrices(): JsonObject {
        val response = HttpClient.sendRequest("https://lb.tricked.pro/lowestbins")
        return gson.fromJson(response, JsonObject::class.java)
    }

    fun fetchPrice(itemTag: String): Double {
        val response = HttpClient.sendRequest("https://lb.tricked.pro/lowestbin/$itemTag")
        return response.toDouble()
    }
}