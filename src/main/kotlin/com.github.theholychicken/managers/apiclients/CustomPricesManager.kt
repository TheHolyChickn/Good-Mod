package com.github.theholychicken.managers.apiclients

import com.github.theholychicken.config.ManualPricesConfig
import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.managers.SellableItemParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * Not an api client, but unless we end up adding more api clients for things
 * unrelated to price fetching, i think it makes the most sense to put this file here
 */
object CustomPricesManager : ApiClient {
    override suspend fun fetchAllAuctions() = withContext(Dispatchers.IO) {
        ManualPricesConfig.loadConfig()
    }

    /**
     * Gets an item's manually set price
     *
     * @param item The skyblock ID tag of the item UNLESS you are using spirit pets, in which case you sould pass either PET_SPIRIT_LEGENDARY or PET_SPIRIT_EPIC (note the underscores instead of the dashes). This is to agree with SellableItemParser
     */
    fun get(item: String) = ManualPricesConfig.manualPrices[item]

    fun updatePrice(item: String, price: Double, rarity: String = "") {
        ManualPricesConfig.manualPrices[item] = price
        ManualPricesConfig.saveConfig()
    }
}