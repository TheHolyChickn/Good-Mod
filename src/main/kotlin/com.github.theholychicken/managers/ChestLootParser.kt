package com.github.theholychicken.managers

import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagString

object ChestLootParser {
    private val GLASS_REGEX: Regex = Regex("1xtile.thinStainedGlass@\\d+$")
    private val WITHER_ESSENCE_REGEX: Regex = Regex("§dWither Essence §8x\\d+")
    private val UNDEAD_ESSENCE_REGEX: Regex = Regex("§dUndead Essence §8x\\d+")
    private val QUANTITY_REGEX: Regex = Regex("(\\d+)$")
    private val collectedItems = ArrayList<String>()
    private val essenceCounts: MutableMap<String, Int> = HashMap()

    // Process instance of DUNGEON_CHEST
    fun parseChestLoot(chest: ContainerChest) {
        // Ensure no items from past chests are saved
        collectedItems.clear()
        essenceCounts.clear()

        // Log items in collectedItems
        for (i in 9..17) {
            val stack = chest.lowerChestInventory.getStackInSlot(i)
            if (GLASS_REGEX.matches(stack.toString())) continue
            val tagCompound = stack.tagCompound ?: continue
            val displayName = tagCompound.getCompoundTag("display").getString("Name")

            when {
                isEnchantedBook(tagCompound) -> {
                    val loreTagList = tagCompound.getCompoundTag("display").getTagList("Lore", NBTTagString().id.toInt())
                    collectedItems.add(loreTagList.getStringTagAt(0))
                }
                WITHER_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.let {
                        essenceCounts["Wither Essence"] = it.groupValues[1].toInt()
                    }
                }
                UNDEAD_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.let {
                        essenceCounts["Undead Essence"] = it.groupValues[1].toInt()
                    }
                }
                else -> collectedItems.add(displayName)
            }
        }
    }

    // Checks if NBT data defines an enchanted book
    private fun isEnchantedBook(tagCompound: NBTTagCompound): Boolean {
        return tagCompound.getCompoundTag("ExtraAttributes").getString("id") == "ENCHANTED_BOOK"
    }

    fun dumpCollectedItems() {
        // Save collected items
        for (itemName in collectedItems) {
            for ((key, value) in ItemDropParser.itemDropPatterns) {
                if (key == itemName) {
                    ItemDropParser.dropsConfig.addItem(value)
                    break
                }
            }
        }
        for ((essenceType, count) in essenceCounts) {
            when (essenceType) {
                "Wither Essence" -> ItemDropParser.dropsConfig.addMany("§dWither Essence§r: §8", count)
                "Undead Essence" -> ItemDropParser.dropsConfig.addMany("§dUndead Essence§r: §8", count)
            }
        }
    }
}
