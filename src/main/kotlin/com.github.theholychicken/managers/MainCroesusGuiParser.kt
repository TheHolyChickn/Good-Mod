package com.github.theholychicken.managers

import net.minecraft.inventory.ContainerChest
import net.minecraft.nbt.NBTTagString

object MainCroesusGuiParser {
    val openedChests: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    fun parseCroesusMenu(chest: ContainerChest) {
        openedChests.clear()
        for (index in 10..43) {
            val stack = chest.lowerChestInventory.getStackInSlot(index)
                ?.takeIf { !it.toString().matches(Regex("1xtile.thinStainedGlass@\\\\d+\$")) } ?: continue

            val tagCompound = stack.tagCompound ?: continue
            val chestInfo = tagCompound.getCompoundTag("display").getTagList("Lore", NBTTagString().id.toInt())

            // Index of the "Completed: XX time ago" line
            val completedIndex = (1 until chestInfo.tagCount())
                .firstOrNull { Regex("§7Completed:").containsMatchIn(chestInfo.getStringTagAt(it))} ?: continue

            val display = Pair(chest.inventorySlots[index].xDisplayPosition, chest.inventorySlots[index].yDisplayPosition)

            /*
            Completely opened chests are marked with a 0
            Opened but keyable chests are marked with a 1
            Unopened chests are marked with a 2
             */
            when {
                chestInfo.getStringTagAt(completedIndex + 1) == "§aNo more chests to open!" -> openedChests[display] = 0
                chestInfo.getStringTagAt(completedIndex + 2) == "§cNo chests opened yet!" -> openedChests[display] = 2
                chestInfo.getStringTagAt(completedIndex + 2).matches(Regex("^§7Opened Chest: §[0-9a-fk-or].+")) -> openedChests[display] = 1
            }
        }
    }
}