package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.config.ManualPricesConfig
import com.github.theholychicken.gui.AbstractScrollableGui
import com.github.theholychicken.gui.manualprices.ConfigManualPrices
import com.github.theholychicken.managers.SellableItemParser
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiTextField
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.GL11

/**
 * A scrollable GUI for manually setting sell prices.
 */
abstract class AbstractManualPricingGui : AbstractScrollableGui() {

    abstract val items: List<SellableItemParser.SellableItem>
    abstract val guiName: String

    override val guiTitle: String
        get() = "manual pricing - $guiName"

    private val rowHeight = 40
    private val fieldWidth = 120
    private val fieldHeight = 22
    private val textScale = 1.2f

    private val entryFields = mutableListOf<Pair<GuiTextField, SellableItemParser.SellableItem>>()

    override fun initGui() {
        super.initGui()

        entryFields.clear()

        // back button
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))

        // text fields
        items.forEachIndexed { index, item ->
            val yPos = index * rowHeight + (rowHeight - fieldHeight) / 2
            val xPos = width - rightMargin - fieldWidth

            val field = GuiTextField(index, fontRendererObj, xPos, yPos, fieldWidth, fieldHeight)
            val savedPrice = ManualPricesConfig.manualPrices[item.displayName] ?: 0.0
            field.text = if (savedPrice == -1.0) "-1" else savedPrice.toInt().toString()

            entryFields.add(field to item)
        }
    }

    override fun getContentHeight(): Int {
        return items.size * rowHeight
    }

    override fun drawContent(mouseX: Int, mouseY: Int, partialTicks: Float) {
        entryFields.forEachIndexed { index, (field, item) ->
            val rowTop = index * rowHeight
            val rowBottom = rowTop + rowHeight

            // highlight effect
            if (mouseY in rowTop..rowBottom && mouseX >= leftMargin && mouseX <= width - rightMargin) {
                drawRect(leftMargin, rowTop, width - rightMargin, rowBottom, 0x25FFFFFF)
            }

            // spacer
            drawRect(leftMargin, rowBottom - 1, width - rightMargin, rowBottom, 0xFF555555.toInt())

            GL11.glPushMatrix()
            GL11.glScalef(textScale, textScale, 1f)

            // why tf does drawStringWithShadow require a float input?
            val textX = ((leftMargin + 5) / textScale)
            val centeredY = rowTop + (rowHeight - (fontRendererObj.FONT_HEIGHT * textScale)) / 2
            val textY = (centeredY / textScale)

            fontRendererObj.drawStringWithShadow(item.displayName, textX, textY, item.hexColor)

            GL11.glPopMatrix()

            // Draw the text box
            field.drawTextBox()
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

        entryFields.forEach { (field, _) ->
            field.mouseClicked(mouseX, getRelativeMouseY(mouseY), mouseButton)
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        // Pass key inputs to text fields (they handle focus internally)
        if (entryFields.any { it.first.textboxKeyTyped(typedChar, keyCode) }) {
            return
        }

        when (keyCode) {
            Keyboard.KEY_ESCAPE -> {
                saveAllPrices()
                mc.displayGuiScreen(ConfigManualPrices())
            }
            Keyboard.KEY_RETURN -> {
                saveAllPrices()
                // Optional: Provide feedback or move focus
                modMessage("Prices saved!")
            }
        }
    }

    override fun actionPerformed(button: GuiButton) {
        if (button.id == 100) {
            saveAllPrices()
            mc.displayGuiScreen(ConfigManualPrices())
        }
    }

    /**
     * Helper to save all current values to the config
     */
    private fun saveAllPrices() {
        var changed = false
        entryFields.forEach { (field, item) ->
            val input = field.text.toDoubleOrNull()
            if (input != null) {
                if (ManualPricesConfig.manualPrices[item.displayName] != input) {
                    ManualPricesConfig.manualPrices[item.displayName] = input
                    changed = true
                }
            } else {
                modMessage("Invalid input for $item: ${field.text}")
            }
        }
        if (changed) {
            ManualPricesConfig.saveConfig()
        }
    }
}