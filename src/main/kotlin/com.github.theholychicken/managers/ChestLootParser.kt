package com.github.theholychicken.managers

import com.github.theholychicken.utils.modMessage
import net.minecraft.init.Blocks
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

    // Process instance of DUNGEON_CHEST
    fun parseChestLoot(chest: ContainerChest) {
        collectedItems.clear()
        essenceCounts.clear()

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
                        .let { collectedItems.add(it) }
                }
                WITHER_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.groupValues?.get(1)?.toIntOrNull()?.let {
                        essenceCounts["Wither Essence"] = it
                    }
                }
                UNDEAD_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.groupValues?.get(1)?.toIntOrNull()?.let {
                        essenceCounts["Undead Essence"] = it
                    }
                }
                else -> {
                    collectedItems.add(displayName)
                }
            }
        }
    }

    // Checks if NBT data defines an enchanted book
    private fun isEnchantedBook(tagCompound: NBTTagCompound) =
        tagCompound.getCompoundTag("ExtraAttributes").getString("id") == "ENCHANTED_BOOK"

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
