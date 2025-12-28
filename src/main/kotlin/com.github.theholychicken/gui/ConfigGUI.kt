package com.github.theholychicken.gui

import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.gui.prices.ConfigPricingGui
import com.github.theholychicken.gui.utils.DropdownMenu
import net.minecraft.client.gui.*
import org.lwjgl.input.Keyboard
import java.io.IOException

class ConfigGUI : AbstractScrollableGui() {

    override val guiTitle: String = "good mod config"

    private val rowHeight = 40
    private val labelX = leftMargin + 5
    private val componentWidth = 150
    private val rightPadding = 20

    // List of api endpoints paired with actions to GuiConfig.api
    private var apis = listOf(
        "hypixel api" to { GuiConfig.api = "HypixelApi" },
        "cofl api" to { GuiConfig.api = "CoflApi" },
        "skytils api" to { GuiConfig.api = "TrickedApi" },
    )
    private val selected = when (GuiConfig.api) {
        "HypixelApi" -> 0
        "CoflApi" -> 1
        "TrickedApi" -> 2
        else -> 0
    }

    private val configRows = mutableListOf<ConfigRow>()
    data class ConfigRow(
        val label: String,
        val button: GuiButton? = null,
        val textField: GuiTextField? = null,
        val dropdown: DropdownMenu? = null,
        val type: RowType
    )
    enum class RowType {
        BUTTON, TEXT_FIELD, DROPDOWN
    }

    private val dropdownButtons = mutableListOf<GuiButton>()
    private lateinit var apiDropdown: DropdownMenu

    override fun initGui() {
        super.initGui()
        configRows.clear()

        // stuff display
        configRows.add(
            ConfigRow(
                label = "stuff display",
                button = GuiButton(0, 0, 0, componentWidth, 20, "open"),
                type = RowType.BUTTON
            )
        )

        // toggle master croesus overlay
        configRows.add(
            ConfigRow(
                label = "toggle main croesus overlay",
                button = GuiButton(1, 0, 0, componentWidth, 20, renderMainCroesusMenu),
                type = RowType.BUTTON
            )
        )

        // sell prince configs
        configRows.add(
            ConfigRow(
                label = "prices config",
                button = GuiButton(2, 0, 0, componentWidth, 20, "open"),
                type = RowType.BUTTON
            )
        )

        // aliases
        configRows.add(
            ConfigRow(
                label = "/goodmod alias",
                textField = GuiTextField(3, fontRendererObj, 0, 0, componentWidth, 20).apply {
                    text = GuiConfig.commandNames["goodmod"] ?: "goodmod"
                },
                type = RowType.TEXT_FIELD
            )
        )

        configRows.add(
            ConfigRow(
                label = "getItems alias",
                textField = GuiTextField(4, fontRendererObj, 0, 0, componentWidth, 20).apply {
                    text = GuiConfig.commandNames["getItems"] ?: "getItems"
                },
                type = RowType.TEXT_FIELD
            )
        )

        configRows.add(
            ConfigRow(
                label = "set min chest profit",
                textField = GuiTextField(5, fontRendererObj, 0, 0, componentWidth, 20).apply {
                    text = GuiConfig.minChestPurchase.toString()
                },
                type = RowType.TEXT_FIELD
            )
        )

        apiDropdown = DropdownMenu(0, 0, componentWidth, apis, selected).apply { initButtons(dropdownButtons) }
        configRows.add(
            ConfigRow(
                label = "set api provider",
                dropdown = apiDropdown,
                type = RowType.DROPDOWN
            )
        )

        configRows.forEachIndexed { index, row ->
            val yPos = index * rowHeight + 10
            val xPos = width - rightMargin - componentWidth - rightPadding

            row.button?.apply {
                xPosition = xPos
                yPosition = yPos
            }

            row.textField?.apply {
                xPosition = xPos
                yPosition = yPos
            }

            row.dropdown?.apply {
                dropdownButtons.forEachIndexed { _, button ->
                    button.xPosition = xPos
                    if (button.id == 100) {
                        button.yPosition = yPos
                    } else {
                        button.yPosition = yPos + (button.id - 100) * 20
                    }
                }
            }
        }

    }

    override fun getContentHeight(): Int {
        val baseHeight = configRows.size * rowHeight
        return if (apiDropdown.expanded) baseHeight + (apiDropdown.options.size * 20) else baseHeight
    }

    override fun drawContent(mouseX: Int, mouseY: Int, partialTicks: Float) {
        configRows.forEachIndexed { index, row ->
            val rowTop = index * rowHeight
            val rowBottom = rowTop + rowHeight

            // highlight a row when hovered
            if (mouseY in rowTop..rowBottom && mouseX in leftMargin..width - rightMargin) {
                drawRect(leftMargin, rowTop, width - rightMargin, rowBottom, 0x25FFFFFF)
            }
            // spacers for each row
            drawRect(leftMargin, rowBottom - 1, width - rightMargin, rowBottom, 0xFF555555.toInt())

            // content
            val centeredY = rowTop + (rowHeight - fontRendererObj.FONT_HEIGHT) / 2
            fontRendererObj.drawStringWithShadow(row.label, labelX.toFloat(), centeredY.toFloat(), 0xFFFFFF)

            when (row.type) {
                RowType.BUTTON -> row.button?.drawButton(mc, mouseX, mouseY)
                RowType.TEXT_FIELD -> row.textField?.drawTextBox()
                RowType.DROPDOWN -> {
                    // scrollable gui doesnt account for dropdows so we do the coordinate transformation manually
                    dropdownButtons.forEach { button ->
                        val yPos = button.yPosition
                        button.yPosition = (yPos - scrollY).toInt()
                        button.drawButton(mc, mouseX, mouseY)
                        button.yPosition = yPos
                    }
                }
            }
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        var fieldTyped = false

        configRows.forEach { row ->
            if (row.type == RowType.TEXT_FIELD && row.textField != null) {
                if (row.textField.textboxKeyTyped(typedChar, keyCode)) {
                    fieldTyped = true
                }
            }
        }

        if (!fieldTyped) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                saveTextFields()
                mc.displayGuiScreen(null)
            }
            if (keyCode == Keyboard.KEY_RETURN) {
                saveTextFields()
            }
        }
    }

    override fun onGuiClosed() {
        saveTextFields()
        super.onGuiClosed()
    }

    private fun saveTextFields() {
        var changed = false

        configRows.forEach { row ->
            if (row.textField != null) {
                val text = row.textField.text
                when (row.textField.id) {
                    3 -> if (GuiConfig.commandNames["goodmod"] != text) {
                        GuiConfig.commandNames["goodmod"] = text
                        changed = true
                    }
                    4 -> if (GuiConfig.commandNames["getItems"] != text) {
                        GuiConfig.commandNames["getItems"] = text
                        changed = true
                    }
                    5 -> {
                        val chestPurchaseMin = formatPrice(text)
                        if (chestPurchaseMin != -1 && chestPurchaseMin != GuiConfig.minChestPurchase) {
                            GuiConfig.minChestPurchase = chestPurchaseMin
                            changed = true
                        }
                    }
                }
            }
        }

        if (changed) {
            saveAndReload()
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

        val relMouseY = getRelativeMouseY(mouseY) // i love manifolds
        configRows.forEach { row ->
            if (row.type == RowType.BUTTON && row.button != null) {
                if (row.button.mousePressed(mc, mouseX, relMouseY)) {
                    row.button.playPressSound(mc.soundHandler)
                    when (row.button.id) {
                        0 -> ItemDropHUD.open()
                        1 -> {
                            GuiConfig.renderMainCroesusGui = !GuiConfig.renderMainCroesusGui
                            saveAndReload()
                            row.button.displayString = GuiConfig.renderMainCroesusGui.toString()
                        }
                        2 -> ConfigPricingGui.open()
                    }
                }
            }

            if (row.type == RowType.TEXT_FIELD && row.textField != null) {
                row.textField.mouseClicked(mouseX, relMouseY, mouseButton)
            }

            if (row.type == RowType.DROPDOWN && row.dropdown != null) {
                dropdownButtons.forEach { button ->
                    if (button.visible && button.mousePressed(mc, mouseX, relMouseY)) {
                        button.playPressSound(mc.soundHandler)
                        row.dropdown.handleButtonClick(button)
                        row.dropdown.updateDropdownLabel(dropdownButtons)
                        if (button.id > 100) {
                            saveAndReload()
                        }
                    }
                }
            }
        }
    }

    override fun doesGuiPauseGame(): Boolean = false

    private fun saveAndReload() {
        GuiConfig.saveConfig()
        GuiConfig.loadConfig()
    }

    private val renderMainCroesusMenu: String
        get() = GuiConfig.renderMainCroesusGui.toString()

    private fun formatPrice(price: String): Int {
        val trimmed = price.trim()
        if (trimmed.isEmpty()) return -1

        val lastChar = trimmed.last().lowercaseChar()

        return try {
            val multiplier = when (lastChar) {
                'k' -> 1_000.0
                'm' -> 1_000_000.0
                'b' -> 1_000_000_000.0
                't' -> 1_000_000_000_000.0
                else -> 1.0
            }

            val number = if (multiplier != 1.0) trimmed.dropLast(1) else trimmed
            (number.toDouble() * multiplier).toInt()
        } catch (_: Exception) {
            -1
        }
    }
}