package com.github.theholychicken.utils

import com.github.theholychicken.config.SellPricesConfig
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
class CatacombsChest(
    name: String,
    items: List<AbstractCroesusChest.LootItem>,
    purchased: Boolean,
    cost: Double,
    location: Pair<Int, Int>
) : AbstractCroesusChest(name, items, purchased, cost, location) {

    override fun parseItemKey(item: String): Pair<String?, Int> {
        return when {
            item.matches(WITHER_ESSENCE_REGEX) -> {
                ("Wither Essence" to getEssenceCount(item))
            }

            item.matches(UNDEAD_ESSENCE_REGEX) -> {
                ("Undead Essence" to getEssenceCount(item))
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

            item.matches(Regex("§7[Lvl 1] §\\dSpirit")) -> {
                (item.substring(2) to 1)
            }

            else -> {
                (item.substring(item.lastIndexOf("§") + 2) to 1)
            }
        }
    }

    private val WITHER_ESSENCE_REGEX = Regex("§dWither Essence §8x\\d+")
    private val UNDEAD_ESSENCE_REGEX = Regex("§dUndead Essence §8x\\d+")
}