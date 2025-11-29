package com.github.theholychicken.managers

import com.github.theholychicken.utils.CroesusChest
import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagString

object ChestLootParser {
    private val GLASS_REGEX = Regex("1xtile.thinStainedGlass@\\d+$")
    private val WITHER_ESSENCE_REGEX = Regex("§dWither Essence §8x\\d+")
    private val UNDEAD_ESSENCE_REGEX = Regex("§dUndead Essence §8x\\d+")
    private val QUANTITY_REGEX = Regex("(\\d+)$")
    private val collectedItems = mutableListOf<String>()
    private val essenceCounts = mutableMapOf<String, Int>()
    private val chestLoot = mutableListOf<String>()
    lateinit var croesusChest: CroesusChest

    // Process instance of DUNGEON_CHEST
    fun parseChestLoot(chest: ContainerChest) {
        collectedItems.clear()
        essenceCounts.clear()
        chestLoot.clear()

        for (index in 9..17) {
            val stack = chest.lowerChestInventory.getStackInSlot(index)
                .takeIf { !GLASS_REGEX.matches(it.toString()) } ?: continue

            val tagCompound = stack.tagCompound ?: continue
            val displayName = tagCompound.getCompoundTag("display").getString("Name")

            when {
                isEnchantedBook(tagCompound) -> {
                    tagCompound.getCompoundTag("display")
                        .getTagList("Lore", NBTTagString().id.toInt())
                        .getStringTagAt(0)
                        .let {
                            collectedItems.add(it)
                            chestLoot.add(it)
                        }
                }
                WITHER_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.groupValues?.get(1)?.toIntOrNull()?.let {
                        essenceCounts["Wither Essence"] = it
                        chestLoot.add(displayName)
                    }
                }
                UNDEAD_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.groupValues?.get(1)?.toIntOrNull()?.let {
                        essenceCounts["Undead Essence"] = it
                        chestLoot.add(displayName)
                    }
                }
                else -> {
                    collectedItems.add(displayName)
                    chestLoot.add(displayName)
                }
            }
        }

        croesusChest = CroesusChest("TEST", chestLoot, false, getCost(chest.lowerChestInventory.getStackInSlot(31).tagCompound), Pair(0,0))
    }

    // Checks if NBT data defines an enchanted book
    private fun isEnchantedBook(tagCompound: NBTTagCompound): Boolean {
        return tagCompound
            .getCompoundTag("ExtraAttributes")
            .getString("id") == "ENCHANTED_BOOK"
    }

    // returns cost of the chest
    private fun getCost(tagCompound: NBTTagCompound): Double {
        val tags = tagCompound
            .getCompoundTag("display")
            .getTagList("Lore", NBTTagString().id.toInt())

        var cost = 0.0
        for (i in 0 until tags.tagCount()) {
            if (tags.get(i).toString().contains(Regex("Cost"))) {
                if (tags.get(i + 2).toString().contains(Regex("§9Dungeon Chest Key"))) {
                    cost += SellableItemParser.auctionPrices["Dungeon Chest Key"] ?: 0.00
                } else if (tags.get(i + 1).toString().contains(Regex("§9Dungeon Chest Key"))) {
                    cost += SellableItemParser.auctionPrices["Dungeon Chest Key"] ?: 0.00
                    return cost
                }
                val coins = tags.get(i + 1).toString().drop(3).dropLast(7)
                coins.replace(",", "").let { if (it != "") cost += it.toInt() }
                //cost += (coins.replace(",", "").toInt() ?: 0)
            }
        }
        return cost
    }

    fun dumpCollectedItems() {
        collectedItems.forEach { itemName ->
            ItemDropParser.itemDropPatterns[itemName]?.let { ItemDropParser.dropsConfig.addItem(it) }
        }

        essenceCounts.forEach { (essenceType, count) ->
            when (essenceType) {
                "Wither Essence" -> ItemDropParser.dropsConfig.addMany("§dWither Essence§r: §8", count)
                "Undead Essence" -> ItemDropParser.dropsConfig.addMany("§dUndead Essence§r: §8", count)
            }
        }
    }
}
