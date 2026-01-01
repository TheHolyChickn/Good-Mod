package com.github.theholychicken.utils

import com.github.theholychicken.managers.SellableItemParser

class KuudraChest(
    name: String,
    items: List<AbstractCroesusChest.LootItem>,
    purchased: Boolean,
    cost: Double,
    location: Pair<Int, Int>,
) : AbstractCroesusChest(name, items, purchased, cost, location) {


    override fun parseItemKey(item: String): Pair<String?, Int> {

        return when {
            item.matches(CRIMSON_ESSENCE_REGEX) -> {
                ("Crimson Essence" to getEssenceCount(item))
            }

            item.matches(SHARD_REGEX_ONE) -> {
                val match = SHARD_REGEX_TWO.find(item)
                if (match != null) {
                    (match.groupValues[1] to (match.groupValues[2].toIntOrNull() ?: 0))
                } else {
                    modMessage("Failed to parse shard instance $item")
                    (item to 0)
                }
            }

            else -> {
                (formatItem(item) to 1)
            }
        }
    }

    private fun isEnchantedBook(tag: String): Boolean = tag.contains("(")

    private fun formatItem(item: String): String {
        if (isEnchantedBook(item)) {
            val open = item.indexOf("(")
            val close = item.indexOf(")")
            return item.substring(open + 1, close - 2)
        } else {
            return item.substring(item.lastIndexOf('§') + 2)
        }
    }

    private val CRIMSON_ESSENCE_REGEX = Regex("§dCrimson Essence §8x\\d+")
}
