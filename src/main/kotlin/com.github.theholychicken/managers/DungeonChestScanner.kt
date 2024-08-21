package com.github.theholychicken.managers

import com.github.theholychicken.GoodMod
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.events.GuiLoadedEvent
import com.github.theholychicken.managers.ChestLootParser.dumpCollectedItems
import com.github.theholychicken.utils.modMessage
import com.github.theholychicken.utils.name
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import org.lwjgl.input.Mouse

object DungeonChestScanner {
    private var isScanning: Boolean = false
    private var chestContainer: ContainerChest? = null
    private var scanAttempts: Int = 0
    private var isChestGuiOpen: Boolean = false
    private val chestLootParser: ChestLootParser = ChestLootParser

    // Check for instance of DUNGEON_CHEST
    @SubscribeEvent
    fun onGuiOpen(event: GuiOpenEvent) {
        val gui = event.gui as? GuiChest ?: return

        if (isChestGuiOpen) {
            isChestGuiOpen = false
            return
        }

        val container = gui.inventorySlots as? ContainerChest ?: return
        val chestName = container.lowerChestInventory.name

        if (!CHEST_PATTERN.matches(chestName)) {
            isChestGuiOpen = false
            return
        } else isChestGuiOpen = true


        chestContainer = container
        isScanning = true
        scanAttempts = 0
    }

    // Await loaded chest
    @SubscribeEvent
    fun onGuiLoaded(event: GuiLoadedEvent) {
        chestLootParser.parseChestLoot(event.gui)
    }

    // Store loot on chest close
    @SubscribeEvent
    fun onSlotClick(event: GuiScreenEvent.MouseInputEvent) {
        if (!isChestGuiOpen || event.gui !is GuiChest || Mouse.getEventButton() == -1) return

        val currentScreen = event.gui as? GuiChest ?: return
        val containerChest = currentScreen.inventorySlots as? ContainerChest ?: return
        if (!CHEST_PATTERN.matches(containerChest.name)) return

        if (currentScreen.slotUnderMouse == null || !Mouse.getEventButtonState() || currentScreen.slotUnderMouse?.slotNumber != 31) return
        dumpCollectedItems()
        isChestGuiOpen = false
        GoodMod.logger.info("Dungeon loot saved")
    }

    private fun stopScanning() {
        isScanning = false
        chestContainer = null
        scanAttempts = 0
    }

    private val CHEST_PATTERN: Regex = Regex("(Wood|Gold|Diamond|Emerald|Obsidian|Bedrock) Chest")
}
