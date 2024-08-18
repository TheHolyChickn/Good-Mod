package com.github.theholychicken.managers

import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagString
import java.util.regex.Pattern

object ChestLootParser {
    private val GLASS_PATTERN: Pattern = Pattern.compile("1xtile.thinStainedGlass@\\d+$")
    private val WITHER_ESSENCE_PATTERN: Pattern = Pattern.compile("§dWither Essence §8x\\d+")
    private val UNDEAD_ESSENCE_PATTERN: Pattern = Pattern.compile("§dUndead Essence §8x\\d+")
    private val QUANTITY_PATTERN: Pattern = Pattern.compile("(\\d+)$")
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
            if (!GLASS_PATTERN.matcher(stack.toString()).matches()) {
                // Retrieve NBT Data
                val tagCompound = stack.tagCompound ?: continue
                val displayName = tagCompound.getCompoundTag("display").getString("Name")

                // Check if item is an enchanted book
                if (isEnchantedBook(tagCompound)) {
                    val loreTagList = tagCompound.getCompoundTag("display")
                        .getTagList("Lore", NBTTagString().id.toInt())
                    val bookName = loreTagList.getStringTagAt(0)
                    collectedItems.add(bookName)
                } else if (WITHER_ESSENCE_PATTERN.matcher(displayName).matches()) {
                    // Check if item is Wither Essence
                    val matcher = QUANTITY_PATTERN.matcher(displayName)
                    if (matcher.find()) {
                        essenceCounts["Wither Essence"] = matcher.group(1).toInt()
                    }
                } else if (UNDEAD_ESSENCE_PATTERN.matcher(displayName).matches()) {
                    // Check if item is Undead Essence
                    val matcher = QUANTITY_PATTERN.matcher(displayName)
                    if (matcher.find()) {
                        essenceCounts["Undead Essence"] = matcher.group(1).toInt()
                    }
                } else {
                    // Item is a dungeon item
                    collectedItems.add(displayName)
                }
            }
        }
    }

    // Checks if NBT data defines an enchanted book
    fun isEnchantedBook(tagCompound: NBTTagCompound): Boolean {
        val itemId = tagCompound.getCompoundTag("ExtraAttributes").getString("id")
        return itemId == "ENCHANTED_BOOK"
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
