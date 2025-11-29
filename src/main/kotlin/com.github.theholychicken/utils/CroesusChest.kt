package com.github.theholychicken.utils

import com.github.theholychicken.managers.SellableItemParser

/**
 * Represents a Croesus chest with details about its contents, purchase status, cost, and profit.
 *
 * @param name The name of the chest
 * @param items a List<String> of items in the chest
 * @param purchased whether the chest is purchased or not
 * @param cost the cost of the chest
 * @param location chest coordinates, passed as Pair<Int, Int>
 *
 * @property name The display name of the chest.
 * @property items The list of items in the chest.
 * @property purchased Whether the chest has been purchased.
 * @property cost The cost of the chest.
 * @property profit The calculated profit from the chest.
 */
class CroesusChest(
    val name: String,
    val items: List<String>,
    val purchased: Boolean,
    private val cost: Double,
    val location: Pair<Int, Int>
) {
    private val itemTags = thing()
    val profit: Double = calculateProfit()

    private fun thing(): MutableList<String> { // what does this do
        val returnList = mutableListOf<String>()
        items.forEach {
            when {
                it.substring(2) in SellableItemParser.shinyItems -> {
                    returnList.add(it.substring(5))
                }
                it.matches(Regex("§dWither Essence §8x\\d+")) -> {
                    returnList.add("WITHER_ESSENCE")
                }

                it.matches(Regex("§dUndead Essence §8x\\d+")) -> {
                    returnList.add("UNDEAD_ESSENCE")
                }

                it.matches(Regex("§7[Lvl 1] §\\dSpirit")) -> {
                    if (Regex("(\\d)").find(it)?.groupValues?.get(3)?.toIntOrNull() == 6) {
                        returnList.add("PET-SPIRIT-LEGENDARY")
                    } else {
                        returnList.add("PET-SPIRIT-EPIC")
                    }
                }

                else -> {
                    SellableItemParser.getItemID(it)
                }
            }
        }
        return returnList
    }


    private fun calculateProfit(): Double {
        val auctionPrices = SellableItemParser.auctionPrices.takeIf { it.isNotEmpty() } ?: run {
            modMessage("Auction/Bazaar prices are empty or null! Try manually refreshing with /updateauctions. If this does not fix the issue, please open an issue on the github.")
            return -cost
        }

        if (purchased) return 0.00

        return items.sumOf {
            when {
                it.matches(Regex("§dWither Essence §8x\\d+")) -> {
                    val price = auctionPrices["Wither Essence"] ?: 0.0
                    val quantity = Regex("(\\d+)$").find(it)?.groupValues?.get(1)?.toIntOrNull() ?: 0
                    quantity * price
                }

                it.matches(Regex("§dUndead Essence §8x\\d+")) -> {
                    val price = auctionPrices["Undead Essence"] ?: 0.0
                    val quantity = Regex("(\\d+)$").find(it)?.groupValues?.get(1)?.toIntOrNull() ?: 0
                    quantity * price
                }

                it.matches(Regex("§[0-9a-fk-or].+ Shard §8x\\d+")) -> {
                    val match = Regex("§[0-9a-fk-or](.+) §8x(\\d+)").find(it)
                    if (match != null) {
                        val (name, quantityStr) = match.destructured
                        val price = auctionPrices[name] ?: 0.0
                        val quantity = quantityStr.toIntOrNull() ?: 0
                        quantity * price
                    } else {
                        modMessage("Failed to parse shard instance $it")
                        0.0
                    }
                }

                it.matches(Regex("§7[Lvl 1] §\\dSpirit")) -> {
                    val key = it.substring(2)
                    auctionPrices[key] ?: run {
                        modMessage("Spirit pet failed")
                        0.00
                    }
                }

                else -> {
                    val key = it.substring(it.lastIndexOf("§") + 2)
                    auctionPrices[key] ?: run {
                        modMessage("Item not found in auction/bazaar prices: §6$key §4§l(Please report this)")
                        0.00
                    }
                }
            }
        } - cost
    }
}