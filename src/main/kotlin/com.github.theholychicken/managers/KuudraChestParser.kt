package com.github.theholychicken.managers

import com.github.theholychicken.utils.AbstractCroesusChest
import com.github.theholychicken.utils.KuudraChest
import com.github.theholychicken.utils.modMessage
import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagList
import net.minecraft.nbt.NBTTagString

/**
 * backend for parsing kuudra chests
 */
object KuudraChestParser {
    private val GLASS_REGEX = Regex("1xtile.thinStainedGlass@\\d+$")
    private val purchasedChest = ""
    val runLoot = mutableListOf<KuudraChest>()
    var openStatus = false

    fun parseKuudraLoot(chest: ContainerChest) {
        runLoot.clear()
        openStatus = false

        // chests occur in 12, 14 but i grab entire row just to be sure
        for (index in 9..17) {
            val stack = chest.lowerChestInventory.getStackInSlot(index)
                .takeIf { !GLASS_REGEX.matches(it.toString()) } ?: continue

            val tagCompound = stack.tagCompound ?: continue
            val displayName = tagCompound.getCompoundTag("display").getString("Name")
            val chestLoot = tagCompound.getCompoundTag("display").getTagList("Lore", NBTTagString().id.toInt())

            // if chest is purchased, it wont say "cost"
            val costIndex = (1 until chestLoot.tagCount())
                .firstOrNull { chestLoot.getStringTagAt(it) == "§7Cost" }
            val endLootIndex = (1 until chestLoot.tagCount()).first { chestLoot.getStringTagAt(it) == "" }
            val lootSubList = mutableListOf<AbstractCroesusChest.LootItem>().apply {
                for (i in 1 until endLootIndex) {
                    add(AbstractCroesusChest.LootItem(
                        displayName = cleanStars(chestLoot.get(i).toString().replace("\"", "")),
                        modifiers = setOf<String>())) // currently voiding stars, will change later
                }
            }
            val location = Pair(chest.inventorySlots[index].xDisplayPosition, chest.inventorySlots[index].yDisplayPosition)

            if (costIndex != null) {
                val chestCost = findCost(chestLoot, costIndex)
                runLoot.add(KuudraChest(displayName, lootSubList, false, chestCost, location))
            } else {
                runLoot.add(KuudraChest(displayName, lootSubList, true, 0.0, location))
            }
        }
    }

    private fun findCost(tagList: NBTTagList, index: Int): Double {
        if (index == 0) {
            modMessage("An error has occurred.")
            return 0.00
        }
        val firstLine = tagList[index + 1].toString().substring(1, tagList[index + 1].toString().length - 1)

        // values are hardcoded from chest NBT data
        return when {
            firstLine == "§aFREE" -> 0.0
            firstLine.contains("Kuudra Key") -> when {
                firstLine.contains("Hot") -> {
                    modMessage("hot key")
                    (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                            (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(4) ?: 0.0) +
                            310_400.0
                }
                firstLine.contains("Burning") -> {
                    modMessage("burning key")
                    (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                            (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(16) ?: 0.0) +
                            582_000.0
                }
                firstLine.contains("Fiery") -> {
                    modMessage("fiery key")
                    (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                            (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(40) ?: 0.0) +
                            1_164_000.0
                }
                firstLine.contains("Infernal") -> {
                    modMessage("infernal key")
                    (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                            (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(80) ?: 0.0) +
                            2_328_000.0
                }
                else -> {
                    modMessage("regular key")
                    (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                            (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(2) ?: 0.0) +
                            155_200.0
                }
            }
            else -> {
                modMessage("could not find price")
                0.0
            }
        }
    }

    fun cleanStars(item: String): String {
        return when {
            item.contains(STARRABLE_ITEM_REGEX) -> {
                val firstStarIndex = item.indexOfFirst { it == '✪' }
                if (firstStarIndex == -1) {
                    item.substring(0, item.length - 1)
                } else {
                    item.substring(0, firstStarIndex - 3)
                }
            }
            else -> item
        }
    }

    private val STARRABLE_ITEM_REGEX = Regex("(Fervor|Crimson|Aurora|Hollow|Terror) (Helmet|Chestplate|Leggings|Boots)")
}