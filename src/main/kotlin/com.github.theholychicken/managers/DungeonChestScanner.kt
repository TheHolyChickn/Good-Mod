package com.github.theholychicken.managers

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.ChestLootParser.dumpCollectedItems
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraft.inventory.Slot
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import org.lwjgl.input.Mouse
import java.util.regex.Pattern

object DungeonChestScanner {
    private var isScanning: Boolean = false
    private var chestContainer: ContainerChest? = null
    private var scanAttempts: Int = 0
    private var isChestGuiOpen: Boolean = false
    private val chestLootParser: ChestLootParser = ChestLootParser

    // Check for instance of DUNGEON_CHEST
    @SubscribeEvent
    fun onGuiOpen(event: GuiOpenEvent) {
        val gui = event.gui
        if (isChestGuiOpen && gui !is GuiChest) {
            isChestGuiOpen = false
            return
        }
        if (gui !is GuiChest) return

        val container = gui.inventorySlots as ContainerChest
        val chestName = container.lowerChestInventory.name

        if (!CHEST_PATTERN.matcher(chestName).matches()) {
            isChestGuiOpen = false
            return
        } else {
            isChestGuiOpen = true
        }

        chestContainer = container
        isScanning = true
        scanAttempts = 0
    }

    // Await loaded chest
    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        // Only execute during the END phase to avoid running twice per tick
        if (event.phase != TickEvent.Phase.END || !isScanning || chestContainer == null) return
        GoodMod.logger.info("[onClientTick] Scanning protocol active - awaiting fully loaded dungeon chest")

        val currentScreen = Minecraft.getMinecraft().currentScreen
        if (currentScreen !is GuiChest) {
            stopScanning()
            GoodMod.logger.info("[onClientTick] Chest closed, terminating scanning protocol")
            isChestGuiOpen = false
            return
        } else {
            chestContainer = currentScreen.inventorySlots as ContainerChest
        }

        val inventorySize = chestContainer!!.lowerChestInventory.sizeInventory
        val bottomRightSlotIndex = inventorySize - 1
        val bottomRightStack = chestContainer!!.lowerChestInventory.getStackInSlot(bottomRightSlotIndex)

        if (bottomRightStack != null) {
            // Chest has loaded
            GoodMod.logger.info("[onClientTick] Instance of DUNGEON_CHEST has been fully loaded, scanning protocol terminated")
            isScanning = false
            scanAttempts = 0

            // Store items
            chestLootParser.parseChestLoot(chestContainer!!)

            // End protocol (chest GUI will remain open)
            chestContainer = null
            return
        }

        // Timeout protocol
        scanAttempts++
        if (scanAttempts > 200) {
            stopScanning()
            modMessage("§r§4§lDungeon chest not loaded [timed out]. §r§bIs the server lagging?§r")
            isChestGuiOpen = false
        }
    }

    // Store loot on chest close
    @SubscribeEvent
    fun onSlotClick(event: GuiScreenEvent.MouseInputEvent) {
        // Check if items have already been saved
        if (!isChestGuiOpen) return
        // Check if instance of DUNGEON_CHEST
        if (event.gui !is GuiChest) return
        if (Mouse.getEventButton() == -1) return

        val currentScreen = event.gui
        val containerChest = (currentScreen as GuiChest).inventorySlots as ContainerChest
        if (!CHEST_PATTERN.matcher(containerChest.lowerChestInventory.name).matches()) return

        // Safeguard against null pointer issues
        var clickedSlot: Slot? = null
        try {
            clickedSlot = currentScreen.slotUnderMouse
        } catch (e: Exception) {
            // Handle exception
        }
        if (clickedSlot != null && Mouse.getEventButtonState() && clickedSlot.slotNumber == 31) {
            dumpCollectedItems()
            isChestGuiOpen = false
            GoodMod.logger.info("Dungeon loot saved")
        }
    }

    private fun stopScanning() {
        isScanning = false
        chestContainer = null
        scanAttempts = 0
    }

    private val CHEST_PATTERN: Pattern = Pattern.compile("(Wood|Gold|Diamond|Emerald|Obsidian|Bedrock) Chest")
}
