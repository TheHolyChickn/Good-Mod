package com.github.theholychicken.managers

import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.utils.AbstractCroesusChest
import com.github.theholychicken.utils.CatacombsChest
import com.github.theholychicken.utils.KuudraChest
import com.github.theholychicken.utils.modMessage
import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagString

object ChestLootParser {
    private val GLASS_REGEX = Regex("1xtile.thinStainedGlass@\\d+$")
    private val WITHER_ESSENCE_REGEX = Regex("§dWither Essence §8x\\d+")
    private val UNDEAD_ESSENCE_REGEX = Regex("§dUndead Essence §8x\\d+")
    private val CRIMSON_ESSENCE_REGEX = Regex("§dCrimson Essence §8x\\d+")
    private val QUANTITY_REGEX = Regex("(\\d+)$")
    private val collectedItems = mutableListOf<String>()
    private val essenceCounts = mutableMapOf<String, Int>()
    private val chestLoot = mutableListOf<String>()
    lateinit var catacombsChest: CatacombsChest
    lateinit var kuudraChest: KuudraChest
    private val keyPrice: Double
        get() = when (SellPricesConfig.prices["Dungeon Chest Key"]?.source) {
        SellPricesConfig.PriceSource.API -> SellableItemParser.auctionPrices["Dungeon Chest Key"] ?: 0.0
        SellPricesConfig.PriceSource.MANUAL -> SellPricesConfig.prices["Dungeon Chest Key"]?.manualValue ?: 0.0
        else -> SellableItemParser.auctionPrices["Dungeon Chest Key"] ?: 0.0
    }

    // Process instance of DUNGEON_CHEST
    fun parseCatacombsChestLoot(chest: ContainerChest) {
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

        catacombsChest = CatacombsChest("TEST", mutableListOf<AbstractCroesusChest.LootItem>().apply {
            chestLoot.forEach { add(AbstractCroesusChest.LootItem(displayName = it, modifiers = setOf<String>())) }
        },
            false,
            getCost(chest.lowerChestInventory.getStackInSlot(31).tagCompound),
            Pair(0,0)
        )
    }

    fun parseKuudraChestLoot(chest: ContainerChest) {
        collectedItems.clear()
        essenceCounts.clear()
        chestLoot.clear()


        for (index in 9..17) {
            val stack = chest.lowerChestInventory.getStackInSlot(index)
                .takeIf { !GLASS_REGEX.matches(it.toString()) } ?: continue

            val tagCompound = stack.tagCompound ?: continue
            val displayName = KuudraChestParser.cleanStars(
                tagCompound.getCompoundTag("display").getString("Name")
            )

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
                CRIMSON_ESSENCE_REGEX.matches(displayName) -> {
                    QUANTITY_REGEX.find(displayName)?.groupValues?.get(1)?.toIntOrNull()?.let {
                        essenceCounts["Crimson Essence"] = it
                        chestLoot.add(displayName)
                    }
                }
                else -> {
                    collectedItems.add(displayName)
                    chestLoot.add(displayName)
                }
            }

            kuudraChest = KuudraChest("TEST", mutableListOf<AbstractCroesusChest.LootItem>().apply {
                chestLoot.forEach { add(AbstractCroesusChest.LootItem(displayName = it, modifiers = setOf<String>())) }
            },
                false,
                getCostKuudra(chest.lowerChestInventory.getStackInSlot(31).tagCompound),
                Pair(0,0)
            )
        }
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
                    cost += keyPrice
                } else if (tags.get(i + 1).toString().contains(Regex("§9Dungeon Chest Key"))) {
                    cost += keyPrice
                    return cost
                }
                val coins = tags.get(i + 1).toString().drop(3).dropLast(7)
                coins.replace(",", "").let { if (it != "") cost += it.toInt() }
                //cost += (coins.replace(",", "").toInt() ?: 0)
            }
        }
        return cost
    }

    private fun getCostKuudra(tagCompound: NBTTagCompound): Double {
        val tags = tagCompound.getCompoundTag("display").getTagList("Lore", NBTTagString().id.toInt())
        for (i in 0 until tags.tagCount()) {
            if (tags.get(i).toString().contains("Kuudra Key")) {
                return when {
                    tags.get(i).toString().contains("Hot") -> {
                        (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                                (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(4) ?: 0.0) +
                                310_400.0
                    }
                    tags.get(i).toString().contains("Burning") -> {
                        (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                                (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(16) ?: 0.0) +
                                582_000.0
                    }
                    tags.get(i).toString().contains("Fiery") -> {
                        (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                                (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(40) ?: 0.0) +
                                1_164_000.0
                    }
                    tags.get(i).toString().contains("Infernal") -> {
                        (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                                (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(80) ?: 0.0) +
                                2_328_000.0
                    }
                    else -> {
                        (SellableItemParser.auctionPrices["Nether Star"]?.times(2) ?: 0.0) +
                                (SellableItemParser.auctionPrices["Enchanted Mycelium"]?.times(2) ?: 0.0) +
                                155_200.0
                    }
                }
            } else if (tags.get(i).toString() == "§aFREE") {
                return 0.0
            }
        }
        modMessage("Could not find chest cost! Defaulting to 0. If this is a free chest then please disregard" +
                " this message cuz idk how to fix this for free chests LOL")
        return 0.0
    }

    fun dumpCollectedItems() {
        collectedItems.forEach { itemName ->
            val cleanName = itemName.replace(Regex("§."), "")
            if (cleanName in SellableItemParser.shinyItems) {
                ItemDropParser.dropsConfig.addItem(cleanName)
            } else {
                val item = SellableItemParser.SellableItem.toItem(itemName)
                if (item != null) {
                    ItemDropParser.dropsConfig.addItem(item.name)
                }
            }
        }

        essenceCounts.forEach { (essenceType, count) ->
            when (essenceType) {
                "Wither Essence" -> ItemDropParser.dropsConfig.addMany(SellableItemParser.SellableItem.ESSENCE_WITHER.name, count)
                "Undead Essence" -> ItemDropParser.dropsConfig.addMany(SellableItemParser.SellableItem.ESSENCE_UNDEAD.name, count)
                "Crimson Essence" -> ItemDropParser.dropsConfig.addMany(SellableItemParser.SellableItem.ESSENCE_CRIMSON.name, count)
            }
        }
    }
}
