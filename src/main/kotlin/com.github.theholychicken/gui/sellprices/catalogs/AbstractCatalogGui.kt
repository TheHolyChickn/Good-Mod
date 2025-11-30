package com.github.theholychicken.gui.sellprices.catalogs
import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.gui.sellprices.ConfigSellPrices
import com.github.theholychicken.gui.utils.ToggleButton
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard
import java.io.IOException
import kotlin.collections.set

/**
 * Assists with the drawing of the GUI that's used for toggling whether to count certain catalog objects.
 */
abstract class AbstractCatalogGui : GuiScreen() {
    // should contain itemNames, and the corresponding buttonId should be items.indexOf(itemName)
    // what does this mean???? - ilo
    abstract val items: List<SellableItemParser.SellableItem>
    abstract val guiName: String

    override fun initGui() {
        super.initGui()
        buttonList.clear()
        renderRows(
            items,
            width,
            height,
            items.map { it.hexColor }
        ).forEach { buttonList.add(it) }
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))
    }

    @Throws(IOException::class)
    override fun actionPerformed(button: GuiButton) {
        if (button is ToggleButton) {
            SellPricesConfig.sellPrices[items[button.id].name] = !button.toggled
        } else if (button.id == 100) {
            SellPricesConfig.saveConfig()
            mc.displayGuiScreen(ConfigSellPrices())
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        drawCenteredString(fontRendererObj, guiName, width / 2, 20, 0x00FFFF)
        for (button in buttonList) {
            button.drawButton(mc, mouseX, mouseY)
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            SellPricesConfig.saveConfig()
            mc.displayGuiScreen(ConfigSellPrices())
        }
    }

    override fun doesGuiPauseGame(): Boolean = false

    /**
     * Dynamically generates a list of toggleButtons for each item
     *
     * Displays at most 5 buttons per row. If a row has fewer than 5 buttons, they are centered horizontally.
     * The entire grid is centered vertically and horizontally.
     *
     * @param itemList the list of items for which to create buttons
     * @param width the available width
     * @param height the available height
     * @return A list of GuiButtons (ToggleButtons) with buttonIds from 0 to items.size - 1
     */
    fun renderRows(itemList: List<SellableItemParser.SellableItem>, width: Int, height: Int, colorList: List<Int>): List<GuiButton> {
        val buttons = mutableListOf<GuiButton>()
        val items = itemList.map { it.displayName }

        val maxPerRow = 5
        val buttonWidth = 40
        val buttonHeight = 20
        val spacingX = 50
        val spacingY = 80

        val totalRows = (items.size + maxPerRow - 1) / maxPerRow

        val gridHeight = totalRows * spacingY
        val startY = (height - gridHeight) / 2

        var itemIndex = 0
        for (row in 0 until totalRows) {
            val itemsInRow = if (row == totalRows - 1 && items.size % maxPerRow != 0) {
                items.size % maxPerRow
            } else {
                maxPerRow
            }

            val rowWidth = itemsInRow * buttonWidth + (itemsInRow - 1) * spacingX
            val startX = (width - rowWidth) / 2

            for (col in 0 until itemsInRow) {
                val x = startX + col * (buttonWidth + spacingX)
                val y = startY + row * spacingY
                val initialState = SellPricesConfig.sellPrices[itemList[itemIndex].name] ?: false

                buttons.add(
                    ToggleButton(
                        itemIndex,
                        x,
                        y,
                        buttonWidth,
                        buttonHeight,
                        initialState,
                        items[itemIndex],
                        colorList[itemIndex]
                    )
                )
                itemIndex++
            }
        }
        return buttons
    }
}