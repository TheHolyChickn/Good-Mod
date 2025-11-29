package com.github.theholychicken.managers

import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.utils.CroesusChest
import com.github.theholychicken.utils.modMessage
import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagList
import net.minecraft.nbt.NBTTagString

/**
 * Backend for parsing croesus chests.
 * @param parseCroesusLoot saves all croesus chests as CroesusChest objects into the list runLoot
 * @param purchasedChest saves the name of the purchaed chest. If no chest is purchased, returns the empty string
 * @param I dont know what @param means
 * lolllll
 */
object CroesusChestParser {
    private val GLASS_REGEX = Regex("1xtile.thinStainedGlass@\\d+$")
    private var purchasedChest = ""
    val runLoot = mutableListOf<CroesusChest>()
    var dungeonChestKeyPrice = SellableItemParser.auctionPrices["Dungeon Chest Key"] ?: 0.00
    var keyStatus: Boolean = false // slightly misleading name, true if should use key
    var openStatus: Boolean = false

    // Process croesus instance
    fun parseCroesusLoot(chest: ContainerChest) {
        runLoot.clear()
        keyStatus = false
        openStatus = false
        dungeonChestKeyPrice = SellableItemParser.auctionPrices["Dungeon Chest Key"] ?: 0.0

        // Chests occur 10-16, and I grab the glass at the edges as well just in case
        for (index in 9..17) {
            val stack = chest.lowerChestInventory.getStackInSlot(index)
                .takeIf { !GLASS_REGEX.matches(it.toString()) } ?: continue // remove any extra glass components

            val tagCompound = stack.tagCompound ?: continue
            val displayName = tagCompound.getCompoundTag("display").getString("Name")
            val chestLoot = tagCompound.getCompoundTag("display").getTagList("Lore", NBTTagString().id.toInt())
            // If chest is purchased, it won't say "Cost"
            val costIndex = (1 until chestLoot.tagCount())
                .firstOrNull { chestLoot.getStringTagAt(it) == "§7Cost" }
            // Loot ends with an empty string always
            val endLootIndex = (1 until chestLoot.tagCount())
                .first { chestLoot.getStringTagAt(it) == "" }

            val lootSubList = subList(chestLoot, 1, endLootIndex)
            val location = Pair(chest.inventorySlots[index].xDisplayPosition, chest.inventorySlots[index].yDisplayPosition)

            if (costIndex != null) {
                val chestCost = findCost(chestLoot, costIndex)
                runLoot.add(CroesusChest(displayName, lootSubList, false, chestCost, location))
            } else {
                runLoot.add(CroesusChest(displayName, lootSubList, true, 0.00, location))
                purchasedChest = displayName
            }
        }

        setKeyStatus()
    }

    // adds a sublist function to NBTTagLists
    private fun subList(tagList: NBTTagList, startIndex: Int, endIndex: Int): MutableList<String> {
        val sublist = mutableListOf<String>()
        for (i in startIndex until endIndex) {
            if (isEnchantedBook(tagList.get(i).toString())) {
                val open = tagList.get(i).toString().indexOf("(")
                val close = tagList.get(i).toString().indexOf(")")
                try {
                    sublist.add("§9" + tagList.get(i).toString().substring(open + 1, close - 2))
                } catch (e: Exception) {
                    modMessage("Error occurred on item ${tagList.get(i)} §4§l(please report this) ")
                }
            } else {
                sublist.add(tagList.get(i).toString().replace("\"", ""))
            }
        }
        return sublist
    }

    private fun isEnchantedBook(tag: String): Boolean = tag.contains("(")

    // finds the cost of the chest
    private fun findCost(tagList: NBTTagList, index: Int): Double {
        if (index == 0) {
            modMessage("An error has occurred.")
            return 0.00
        }
        val firstLine = tagList[index + 1].toString().substring(1, tagList[index + 1].toString().length - 1)
        val secondLine = tagList[index + 2].toString().substring(1, tagList[index + 2].toString().length - 1)

        // values are hardcoded from chest NBT data
        val cost = when {
            firstLine == "§aFREE" -> 0
            firstLine == "§9Dungeon Chest Key" -> {
                openStatus = true
                dungeonChestKeyPrice
            }
            (firstLine.indexOf("§6") == 0) && (secondLine == "") -> {
                val endIndex = tagList.get(index + 1).toString().indexOf("Coins") - 2
                firstLine.substring(2, endIndex).replace(",", "").toInt()
            }
            (firstLine.indexOf("§6") == 0) && (secondLine == "§9Dungeon Chest Key") -> {
                val endIndex = tagList.get(index + 1).toString().indexOf("Coins") - 2
                openStatus = true
                firstLine.substring(2, endIndex).replace(",", "").toDouble() + dungeonChestKeyPrice
            }
            else -> 0
        }
        return cost.toDouble()
    }

    private fun setKeyStatus() {
        if (openStatus) return // quit if chest alr opened

        val secondMostProfit: Double = runLoot
            .sortedByDescending { it.profit }
            .getOrNull(1)
            ?.profit ?: return

        keyStatus = secondMostProfit > dungeonChestKeyPrice + GuiConfig.minChestPurchase
    }
}