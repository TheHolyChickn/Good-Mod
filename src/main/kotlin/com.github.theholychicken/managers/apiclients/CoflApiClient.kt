package com.github.theholychicken.managers.apiclients

import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.managers.SellableItemParser
import com.github.theholychicken.utils.modMessage
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

// trying to parse each item individually gives errors, so we parse the entire
// neu prices endpoint for relevant items where possible

object CoflApiClient : ApiClient {
    private val gson = Gson()

    override suspend fun fetchAllAuctions() = withContext(Dispatchers.IO) {
        val jsonData = fetchAllPrices()

        for (item in SellableItemParser.items) {
            if (item.sellType == SellableItemParser.SellableItem.SellType.AUCTION) {
                when {
                    item.name == "GOLDOR_THE_FISH" || item.name == "MAXOR_THE_FISH" -> {
                        SellableItemParser.updateAuction(item.displayName, fetchPrice(item.name).get("buy").asDouble)
                    }
                    item.name == "PET-SPIRIT-LEGENDARY" -> {
                        // api doesnt check spirit pet rarity so we do it thru tricked
                        SellableItemParser.updateAuction(item.displayName, TrickedApiClient.fetchPrice(item.name), "LEGENDARY")
                    }
                    item.name == "PET-SPIRIT-EPIC" -> {
                        SellableItemParser.updateAuction(item.displayName, TrickedApiClient.fetchPrice(item.name), "EPIC")
                    }
                    jsonData.has(item.name) -> {
                        SellableItemParser.updateAuction(item.displayName, jsonData.get(item.name).asDouble)
                    }
                    else -> {
                        modMessage("Auction not found: (${item.name}, ${item.displayName})")
                        SellableItemParser.updateAuction(item.displayName, 0.0)
                    }
                }
            } else if (item.sellType == SellableItemParser.SellableItem.SellType.BAZAAR) {
                modMessage("Fetching auctions ${item.name}")
                val price = fetchPrice(item.name).get(getKeyString(item.name)).asDouble
                SellableItemParser.updateBazaar(item.name, price)
            }
        }

        SellableItemParser.saveToFile()
        modMessage("Successfully fetched all auctions.")
    }

    // Use this function sparingly to prevent too many requests errors
    private fun fetchPrice(itemTag: String): JsonObject {
        val response = HttpClient.sendRequest("https://sky.coflnet.com/api/item/price/$itemTag/current")
        return gson.fromJson(response, JsonObject::class.java)
    }

    // Using neu endpoint where possible to prevent too many request errors
    // Unfortunately this endpoint is trash its much less accurate afaik
    private fun fetchAllPrices(): JsonObject {
        val response = HttpClient.sendRequest("https://sky.coflnet.com/api/prices/neu")
        return gson.fromJson(response, JsonObject::class.java)
    }

    private fun getKeyString(tag: String): String =
        if (SellPricesConfig.sellPrices[tag] == true) "buy" else "sell"
}