package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.config.ManualPricesConfig
import com.github.theholychicken.gui.manualprices.ConfigManualPrices
import com.github.theholychicken.managers.SellableItemParser
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import okio.IOException
import org.lwjgl.input.Keyboard
import kotlin.jvm.Throws

/**
 * Assists with the drawing of GUIs that are used for manually setting sell prices
 */
abstract class AbstractManualPricingGui : GuiScreen() {
    abstract val items: List<SellableItemParser.SellableItem>
    abstract val guiName : String
    private val entryFields : MutableList<Pair<GuiTextField, SellableItemParser.SellableItem>> = mutableListOf()

    override fun initGui() {
        super.initGui()
        defineEntries()
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))
    }

    @Throws(IOException::class)
    override fun actionPerformed(button: GuiButton) {
        if (button.id == 100) {
            mc.displayGuiScreen(ConfigManualPrices())
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        drawCenteredString(fontRendererObj, guiName, width / 2, 20, 0x00FFFF)
        drawCenteredString(fontRendererObj, "Enter the price you wish to sell each item at.", width / 2, 40, 0xFFFFFF)
        drawCenteredString(fontRendererObj, "Press enter to save, or press the save button to save all.", width / 2, 55, 0xFFFFFF)
        entryFields.forEach { drawBoxAndTitleString(it) }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        // Allows input fields to process inputs
        if (entryFields.any { it.first.textboxKeyTyped(typedChar, keyCode) }) {
            // for some reason this empty if statement is needed to make the code work
            // idk either but dont remove it
        }

        when (keyCode) {
            Keyboard.KEY_ESCAPE -> mc.displayGuiScreen(ConfigManualPrices())
            Keyboard.KEY_RETURN -> handleEnterKey()
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        entryFields.forEach { it.first.mouseClicked(mouseX, mouseY, mouseButton) }
    }

    private fun handleEnterKey() {
        entryFields.forEach {
            if (it.first.isFocused) {
                ManualPricesConfig.manualPrices[it.second.name] = it.first.text.toDoubleOrNull() ?: run {
                    modMessage("Please input a valid price.")
                    it.first.text = ManualPricesConfig.manualPrices[it.second.name].toString()
                    0.0
                }
                ManualPricesConfig.saveConfig()
            }
        }
    }

    override fun doesGuiPauseGame(): Boolean = false

    private fun defineEntries() {
        TODO(
            "This logic only works when considering only bazaarable items." +
                "I want to make it so each item is a line formatted as ITEM [whitespace] TextInputField" +
                "Then I want to implement scrolling. to do this i need to make a new type of gui that" +
                "handles scrolling by adding or substracting y vals to EVERYTHING until it hits a" +
                "specified max/min (except a title header which should be overlayed and have" +
                "transparent background)" +
                "Additionally i had to change from the key being the id to the displayName, gotta see if that breaks stuff too idk"
        )
        val maxPerRow = 5
        val fieldWidth = 40
        val fieldHeight = 20
        val spacingX = 50
        val spacingY = 80

        val rows = (items.size + maxPerRow - 1) / maxPerRow

        val startY = (height - (rows * spacingY)) / 2

        var index = 0
        for (row in 0 until rows) {
            val itemsInRow = if (row == rows - 1 && items.size % maxPerRow != 0) {
                items.size % maxPerRow
            } else {
                maxPerRow
            }

            val startX = (width - (itemsInRow * fieldWidth + (itemsInRow - 1) * spacingX)) / 2

            for (col in 0 until itemsInRow) {
                val x = startX + col * (fieldWidth + spacingX)
                val y = startY + row * spacingY
                entryFields.add(
                    Pair(
                        GuiTextField(
                            index,
                            fontRendererObj,
                            x,
                            y,
                            fieldWidth,
                            fieldHeight
                        ),
                        items[index]
                    )
                )
                index++
            }
        }
    }

    private fun drawBoxAndTitleString(textField: Pair<GuiTextField, SellableItemParser.SellableItem>) {
        textField.first.drawTextBox()
        val textX = textField.first.xPosition + (mc.fontRendererObj.getStringWidth(textField.second.displayName)) / 2
        drawString(
            fontRendererObj,
            textField.second.displayName,
            textX,
            textField.first.yPosition - 3 - mc.fontRendererObj.FONT_HEIGHT,
            textField.second.hexColor
        )
    }
}