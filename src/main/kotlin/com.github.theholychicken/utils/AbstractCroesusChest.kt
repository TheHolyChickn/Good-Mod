package com.github.theholychicken.utils

import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.managers.SellableItemParser

abstract class AbstractCroesusChest(
    val name: String,
    val items: List<LootItem>,
    val purchased: Boolean,
    private val cost: Double,
    val location: Pair<Int, Int>
) {
    val profit: Double by lazy { calculateProfit() }

    abstract fun parseItemKey(item: String): Pair<String?, Int>

    // doesnt account for modifiers this may be a very complex refactor
    // idea: come up with an Item object, pass those
    fun calculateProfit(): Double {
        if (purchased) return 0.0

        return items.sumOf {
            val (key, quantity) = parseItemKey(it.displayName)
            val unitPrice: Double = if (key != null) {
                val pref = SellPricesConfig.prices[key]
                if (pref?.source == SellPricesConfig.PriceSource.MANUAL) {
                    pref.manualValue
                } else {
                    SellableItemParser.auctionPrices[key] ?: 0.0
                }
            } else {
                0.0
            }

            unitPrice * quantity
        } - cost
    }

    protected fun getEssenceCount(essence: String): Int {
        return Regex("(\\d+)$").find(essence)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    }

    data class LootItem(
        val displayName: String,
        val sellableItem: SellableItemParser.SellableItem? = SellableItemParser.SellableItem.toItem(displayName),
        val modifiers: Set<String>
    )

    enum class Modifiers {
        STAR_1, STAR_2, STAR_3, STAR_4, STAR_5 // dunno
    }

    protected val SHARD_REGEX_ONE = Regex("§[0-9a-fk-or].+ Shard §8x\\d+")
    protected val SHARD_REGEX_TWO = Regex("§[0-9a-fk-or](.+) §8x(\\d+)")
}