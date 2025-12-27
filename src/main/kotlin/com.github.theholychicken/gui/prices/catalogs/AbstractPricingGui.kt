package com.github.theholychicken.gui.prices.catalogs

import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.gui.AbstractScrollableGui
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiTextField
import org.lwjgl.input.Keyboard

abstract class AbstractPricingGui : AbstractScrollableGui() {

    abstract val items: List<SellableItemParser.SellableItem>
    abstract val guiName: String

    abstract fun openMainMenu()

    override val guiTitle: String
        get() = "pricing config - $guiName"

    // layout
    private val rowHeight = 30
    private val actionWidth = 100
    private val modeWidth = 60
    private val spacing = 5

    private val rowWidgets = mutableListOf<RowWidget>()

    // widgets for a single item
    data class RowWidget(
        val item: SellableItemParser.SellableItem,
        val modeButton: GuiButton,
        val apiVariantButton: GuiButton,
        val manualField: GuiTextField
    )

    override fun initGui() {
        super.initGui()
        rowWidgets.clear()

        // back button
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "back"))

        // set up the row widgets
        items.forEachIndexed { index, item ->
            val yPos = index * rowHeight + 5

            val actionX = width - rightMargin - actionWidth
            val modeX = actionX - spacing - modeWidth

            val modeButton = GuiButton(index, modeX, yPos, modeWidth, 20, "")
            val apiButton = GuiButton(index, actionX, yPos, actionWidth, 20, "")
            val field = GuiTextField(index, fontRendererObj, actionX, yPos, actionWidth, 20)
            val pref = SellPricesConfig.prices[item.displayName] ?: SellPricesConfig.PricePreference()

            updateButtonLabels(modeButton, apiButton, pref)

            // format it nicely for ints
            field.text = if (pref.manualValue % 1.0 == 0.0) {
                pref.manualValue.toInt().toString()
            } else {
                pref.manualValue.toInt().toString()
            }

            rowWidgets.add(RowWidget(item, modeButton, apiButton, field))
        }
    }

    override fun getContentHeight(): Int {
        return items.size * rowHeight
    }

    override fun drawContent(mouseX: Int, mouseY: Int, partialTicks: Float) {
        rowWidgets.forEachIndexed { index, widget ->
            val rowTop = index * rowHeight
            val rowBottom = rowTop + rowHeight

            // just dont render it if it doesnt exist
            val pref = SellPricesConfig.prices[widget.item.displayName] ?: SellPricesConfig.PricePreference()

            // background highlight
            if (mouseY in rowTop..rowBottom && mouseX >= leftMargin && mouseX <= width - rightMargin) {
                drawRect(leftMargin, rowTop, width - rightMargin, rowBottom, 0x25FFFFFF)
            }
            // spacer
            drawRect(leftMargin, rowBottom - 1, width - rightMargin, rowBottom, 0xFF555555.toInt())

            // item name
            val textX = leftMargin + 5
            val centeredY = rowTop + (rowHeight - fontRendererObj.FONT_HEIGHT) / 2
            fontRendererObj.drawStringWithShadow(
                widget.item.displayName,
                textX.toFloat(),
                centeredY.toFloat(),
                widget.item.hexColor
            )

            // render the widgets
            widget.modeButton.drawButton(mc, mouseX, mouseY)
            if (pref.source == SellPricesConfig.PriceSource.MANUAL) {
                widget.manualField.drawTextBox()
            } else {
                // only show the other button for bazaar items cuz like obviously?
                if (widget.item.sellType == SellableItemParser.SellableItem.SellType.BAZAAR) {
                    widget.apiVariantButton.drawButton(mc, mouseX, mouseY)
                }
            }
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

        // coordinate transformation to relative coordinates
        val relMouseY = getRelativeMouseY(mouseY)

        rowWidgets.forEach { widget ->
            val pref = SellPricesConfig.prices.getOrPut(widget.item.displayName) { SellPricesConfig.PricePreference() }

            if (widget.modeButton.mousePressed(mc, mouseX, relMouseY)) {
                pref.source = if (pref.source == SellPricesConfig.PriceSource.API) {
                    SellPricesConfig.PriceSource.MANUAL
                } else {
                    SellPricesConfig.PriceSource.API
                }

                widget.modeButton.playPressSound(mc.soundHandler)
                updateButtonLabels(widget.modeButton, widget.apiVariantButton, pref)
                SellPricesConfig.saveConfig()
                return
            }

            if (pref.source == SellPricesConfig.PriceSource.API) {
                if (widget.apiVariantButton.mousePressed(mc, mouseX, relMouseY)) {
                    pref.apiPricing = if (pref.apiPricing == SellPricesConfig.ApiPricing.SELL_OFFER) {
                        SellPricesConfig.ApiPricing.INSTASELL
                    } else {
                        SellPricesConfig.ApiPricing.SELL_OFFER
                    }
                    updateButtonLabels(widget.modeButton, widget.apiVariantButton, pref)
                    SellPricesConfig.saveConfig()
                }
            } else {
                widget.manualField.mouseClicked(mouseX, relMouseY, mouseButton)
            }
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        rowWidgets.forEach { widget ->
            val pref = SellPricesConfig.prices[widget.item.displayName]
            if (pref?.source == SellPricesConfig.PriceSource.MANUAL) {
                if (widget.manualField.textboxKeyTyped(typedChar, keyCode)) return
            }
        }

        when (keyCode) {
            Keyboard.KEY_ESCAPE -> {
                saveManualFields()
                openMainMenu()
            }
            Keyboard.KEY_RETURN -> {
                saveManualFields()
            }
        }
    }

    override fun actionPerformed(button: GuiButton) {
        if (button.id == 100) {
            saveManualFields()
            openMainMenu()
        }
    }

    private fun saveManualFields() {
        var changed = false
        rowWidgets.forEach { widget ->
            val pref = SellPricesConfig.prices[widget.item.displayName] ?: return@forEach

            val input = widget.manualField.text.toDoubleOrNull()
            if (input != null) {
                if (pref.manualValue != input) {
                    pref.manualValue = input
                    changed = true
                }
            }
        }
        if (changed) SellPricesConfig.saveConfig()
    }

    private fun updateButtonLabels(modeButton: GuiButton, apiButton: GuiButton, pref: SellPricesConfig.PricePreference) {
        if (pref.source == SellPricesConfig.PriceSource.API) {
            modeButton.displayString = "§bapi"
        } else {
            modeButton.displayString = "§emanual"
        }

        if (pref.apiPricing == SellPricesConfig.ApiPricing.SELL_OFFER) {
            apiButton.displayString = "§asell offer"
        } else {
            apiButton.displayString = "§cinstasell"
        }
    }
}