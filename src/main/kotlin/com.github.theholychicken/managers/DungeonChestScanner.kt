package com.github.theholychicken.managers

import com.github.theholychicken.GoodMod
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.ChestLootParser.dumpCollectedItems
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import org.lwjgl.input.Mouse

/**
 * This object manages scanning for both croesus and dungeon chest instances.
 * I should really write an external utils file to clean this up a lot.
 * i'm too lazy to fix this too - ilo
 */
object DungeonChestScanner {
    private var isScanningChest = false
    private var chestContainer: ContainerChest? = null
    private var scanAttempts = 0
    private var isChestGuiOpen = false
    private val chestLootParser: ChestLootParser = ChestLootParser
    private val croesusChestParser: CroesusChestParser = CroesusChestParser
    private var isCroesusGuiOpen = false
    var croesusIsParsed = false
    private var isMainCroesusGuiOpen = false
    var mainCroesusIsParsed = false
    var chestIsParsed = false

    // Check for instance of DUNGEON_CHEST
    @SubscribeEvent
    fun onGuiOpen(event: GuiOpenEvent) {
        if (event.gui == null) {
            croesusIsParsed = false
            mainCroesusIsParsed = false
            chestIsParsed = false
            return
        }
        val gui = event.gui as? GuiChest ?: return

        //if (isChestGuiOpen) {
        //    isChestGuiOpen = false
        //    return
        //}
        // IDk how to make this work with croesus here too
        // what  did this even do?

        val container = gui.inventorySlots as? ContainerChest ?: return
        val chestName = container.lowerChestInventory.name

        // reset variables on each gui switch just to make sure we dont
        // get any rendering errors
        isCroesusGuiOpen = false
        isChestGuiOpen = false
        croesusIsParsed = false
        isMainCroesusGuiOpen = false
        mainCroesusIsParsed = false
        chestIsParsed = false
        MainCroesusGuiParser.openedChests.clear()

        when {
            CHEST_PATTERN.matches(chestName) -> isChestGuiOpen = true
            CROESUS_PATTERN.matches(chestName) -> isCroesusGuiOpen = true
            chestName == "Croesus" && GuiConfig.renderMainCroesusGui -> isMainCroesusGuiOpen = true
            else -> return
        }

        chestContainer = container
        isScanningChest = true
        scanAttempts = 0
    }

    // Await loaded chest
    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        // Only execute during the END phase to avoid running twice per tick
        if (event.phase != TickEvent.Phase.END || !isScanningChest || chestContainer == null) return
        GoodMod.logger.info("[onClientTick] Scanning protocol active - awaiting fully loaded gui")

        val currentScreen = mc.currentScreen
        if (currentScreen !is GuiChest) {
            stopScanning()
            GoodMod.logger.info("[onClientTick] Gui closed, terminating scanning protocol")
            isChestGuiOpen = false
            isCroesusGuiOpen = false
            isMainCroesusGuiOpen = false
            croesusIsParsed = false
            mainCroesusIsParsed = false
            chestIsParsed = false
            return
        } else chestContainer = currentScreen.inventorySlots as? ContainerChest ?: return


        val inventorySize = chestContainer?.lowerChestInventory?.sizeInventory ?: return
        val bottomRightStack = chestContainer?.lowerChestInventory?.getStackInSlot(inventorySize - 1)

        if (bottomRightStack != null) {
            GoodMod.logger.info("[onClientTick] Gui instance has been fully loaded, scanning protocol terminated")
            isScanningChest = false
            scanAttempts = 0

            chestContainer?.let {
                if (isChestGuiOpen) {
                    chestLootParser.parseChestLoot(it)
                    chestIsParsed = true
                } else if (isCroesusGuiOpen) {
                    SellableItemParser.initFromFile()
                    croesusChestParser.parseCroesusLoot(it)
                    croesusIsParsed = true
                } else if (isMainCroesusGuiOpen) {
                    MainCroesusGuiParser.parseCroesusMenu(it)
                    mainCroesusIsParsed = true
                }
            }

            chestContainer = null
            return
        }

        // Timeout protocol
        scanAttempts++
        if (scanAttempts > 200) {
            stopScanning()
            modMessage("§r§4§lGui was not fully loaded (timed out). §r§bIs the server lagging?§r")
            isChestGuiOpen = false
            isCroesusGuiOpen = false
            isMainCroesusGuiOpen = false
        }
    }

    // Store loot on chest close
    @SubscribeEvent
    fun onSlotClick(event: GuiScreenEvent.MouseInputEvent) {
        if (!isChestGuiOpen || event.gui !is GuiChest || Mouse.getEventButton() == -1) return

        val currentScreen = event.gui as? GuiChest ?: return
        val containerChest = currentScreen.inventorySlots as? ContainerChest ?: return
        if (!CHEST_PATTERN.matches(containerChest.lowerChestInventory.name)) return

        if (currentScreen.slotUnderMouse == null || !Mouse.getEventButtonState() || currentScreen.slotUnderMouse?.slotNumber != 31) return
        dumpCollectedItems()
        isChestGuiOpen = false
        chestIsParsed = false
        GoodMod.logger.info("Dungeon loot saved")
    }

    private fun stopScanning() {
        isScanningChest = false
        chestContainer = null
        scanAttempts = 0
    }

    private val CHEST_PATTERN = Regex("(Wood|Gold|Diamond|Emerald|Obsidian|Bedrock) Chest")
    private val CROESUS_PATTERN = Regex("^(Master )?Catacombs - Flo(or (IV|V?I{0,3}))?$")
}
