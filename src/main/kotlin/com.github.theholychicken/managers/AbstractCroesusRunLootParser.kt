package com.github.theholychicken.managers

import com.github.theholychicken.utils.CatacombsChest
import com.github.theholychicken.utils.KuudraChest
import net.minecraft.nbt.NBTTagList

abstract class AbstractCroesusRunLootParser(dungeon: Dungeon) {
    private val GLASS_REGEX = Regex("1xtile.thinStainedGlass@\\d+$")
    private var purchasedChest = ""
    val runLoot = when (dungeon) {
        Dungeon.KUUDRA -> mutableListOf<KuudraChest>()
        Dungeon.CATACOMBS -> mutableListOf<CatacombsChest>()
    }
    var openStatus: Boolean = false

    abstract fun findCost(tagList: NBTTagList, index: Int): Double

    enum class Dungeon {
        CATACOMBS,
        KUUDRA;
    }
}