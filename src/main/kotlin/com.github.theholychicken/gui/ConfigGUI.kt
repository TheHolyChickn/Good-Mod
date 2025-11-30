package com.github.theholychicken.gui

import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.gui.sellprices.ConfigSellPrices
import com.github.theholychicken.gui.utils.DropdownMenu
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.*
import org.lwjgl.input.Keyboard
import java.io.IOException

class ConfigGUI : GuiScreen() {

    private lateinit var getItemsNameField: GuiTextField
    private lateinit var openGuiNameField: GuiTextField
    private lateinit var setMinChestProfitField: GuiTextField
    private lateinit var dropdownMenu: DropdownMenu

    // List of api endpoints paired with actions to GuiConfig.api
    private var apis = listOf(
        "Hypixel API" to { GuiConfig.api = "HypixelApi" },
        "Cofl API" to { GuiConfig.api = "CoflApi" },
        "Skytils API" to { GuiConfig.api = "TrickedApi" }
    )
    private val selected = when (GuiConfig.api) {
        "HypixelApi" -> 0
        "CoflApi" -> 1
        "TrickedApi" -> 2
        else -> 0
    }

    override fun initGui() {
        super.initGui()
        buttonList.clear()

        // Add buttons
        // Opens the stuff display
        buttonList.add(GuiButton(0, (this.width / 2) - 100, (this.height / 2) + 2, buttonLabel))
        // Toggle for using sell offer/instasell pricing
        buttonList.add(GuiButton(1, (this.width / 2) - 100, (this.height / 2) + 26, sellOffer))
        // Toggle for rendering main croesus menu
        buttonList.add(GuiButton(2, (this.width / 2) - 100, (this.height / 2) - 24, renderMainCroesusMenu))
        // Opens the per-item sell price configs
        buttonList.add(GuiButton(3, width / 2 - 100, height / 2 + 50, "Sell Price Configs"))

        // Initialize text fields
        // Config field for /goodmod:getitems
        getItemsNameField = GuiTextField(
            2,
            fontRendererObj,
            width / 2 - 100,
            height / 2 - 50,
            200,
            20
        ).apply {
            maxStringLength = 100
            isFocused = false
            enableBackgroundDrawing = true
            text = GuiConfig.commandNames["getItems"] ?: "An error occurred, report this"
        }

        // Config field for /goodmod:goodmod
        openGuiNameField = GuiTextField(
            3,
            fontRendererObj,
            width / 2 - 100,
            height / 2 - 76,
            200,
            20
        ).apply {
            maxStringLength = 100
            isFocused = false
            enableBackgroundDrawing = true
            text = GuiConfig.commandNames["goodmod"] ?: "An error occurred, report this"
        }

        // Set min profit
        setMinChestProfitField = GuiTextField(
            1,
            fontRendererObj,
            width / 2 - 100,
            height / 2 - 102,
            200,
            20
        ).apply {
            maxStringLength = 100
            isFocused = false
            enableBackgroundDrawing = true
            text = GuiConfig.minChestPurchase.toString()
        }

        // Dropdown menu for api endpoints
        dropdownMenu = DropdownMenu(
            (this.width / 2) - 100,
            (this.height / 2) + 74,
            200,
            apis,
            selected
        ).apply {
            initButtons(buttonList)
        }
    }

    override fun updateScreen() {
        super.updateScreen()
        getItemsNameField.updateCursorCounter()
        openGuiNameField.updateCursorCounter()
        setMinChestProfitField.updateCursorCounter()
    }

    @Throws(IOException::class)
    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> ItemDropHUD.open()
            1 -> {
                GuiConfig.useSellOffer = !GuiConfig.useSellOffer
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                button.displayString = sellOffer
            }
            2 -> {
                GuiConfig.renderMainCroesusGui = !GuiConfig.renderMainCroesusGui
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                button.displayString = renderMainCroesusMenu
            }
            3 -> ConfigSellPrices.open()
            in 100..(100 + apis.size) -> {
                dropdownMenu.handleButtonClick(button)
                dropdownMenu.updateDropdownLabel(buttonList)
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                if (button.id > 100)  modMessage("You have activated the ${apis[button.id - 101].first}!")
            }
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        drawCenteredString(
            fontRendererObj,
            "good mod config",
            width / 2,
            height / 2 - 126,
            0x00FFFF
        )

        // draw labels and input fields
        drawCenteredString(
            fontRendererObj,
            "set /goodmod alias",
            width / 2 + 150,
            height / 2 - 70,
            0x00FFFF
        )
        getItemsNameField.drawTextBox()

        drawCenteredString(
            this.fontRendererObj,
            "set /getItems alias",
            this.width / 2 + 150,
            this.height / 2 - 45,
            0x00FFFF
        )
        openGuiNameField.drawTextBox()

        drawCenteredString(
            this.fontRendererObj,
            "set minimum chest purchase price",
            this.width / 2 + 175,
            this.height / 2 - 96,
            0x00FFFF
        )
        setMinChestProfitField.drawTextBox()

        // Draw buttons
        for (button in this.buttonList) { button.drawButton(this.mc, mouseX, mouseY) }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        // Allows input fields to process inputs
        if (getItemsNameField.textboxKeyTyped(typedChar, keyCode) ||
            openGuiNameField.textboxKeyTyped(typedChar, keyCode) ||
            setMinChestProfitField.textboxKeyTyped(typedChar, keyCode)) {
            // handled by text input fields
        }

        when (keyCode) {
            Keyboard.KEY_RETURN -> handleEnterKey()
            Keyboard.KEY_ESCAPE -> mc.displayGuiScreen(null)
        }
    }

    private fun handleEnterKey() {
        when {
            getItemsNameField.isFocused -> {
                GuiConfig.commandNames["getItems"] = getItemsNameField.text
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                mc.displayGuiScreen(null)
                modMessage("Set /getItems to /${getItemsNameField.text}! §r§2§lRestart game for changes to take effect.")
            }
            openGuiNameField.isFocused -> {
                GuiConfig.commandNames["goodmod"] = openGuiNameField.text
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                mc.displayGuiScreen(null)
                modMessage("Set /goodmod to /${openGuiNameField.text}! §r§2§lRestart game for changes to take effect.")
            }
            setMinChestProfitField.isFocused -> {
                val input = formatPrice(setMinChestProfitField.text)
                if (input == -1) {
                    mc.displayGuiScreen(null)
                    modMessage("Could not read input. Please try again.")
                } else {
                    GuiConfig.minChestPurchase = input
                    GuiConfig.saveConfig()
                    GuiConfig.loadConfig()
                    mc.displayGuiScreen(null)
                    modMessage("Set minimum chest purchase price to $input!")
                }
            }
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        getItemsNameField.mouseClicked(mouseX, mouseY, mouseButton)
        openGuiNameField.mouseClicked(mouseX, mouseY, mouseButton)
        setMinChestProfitField.mouseClicked(mouseX, mouseY, mouseButton)
        if (dropdownMenu.expanded && !isMouseOverDropdown(mouseX, mouseY)) {
            dropdownMenu.closeDropdown()
        }
    }

    private fun isMouseOverDropdown(mouseX: Int, mouseY: Int): Boolean {
        val dropdownHeight = if (dropdownMenu.expanded) {
            (dropdownMenu.options.size + 1) * 20
        } else {
            20
        }
        return mouseX in dropdownMenu.x..(dropdownMenu.x + dropdownMenu.width) &&
                mouseY in dropdownMenu.y..(dropdownMenu.y + dropdownHeight)
    }

    override fun doesGuiPauseGame(): Boolean = false

    private val buttonLabel: String
        get() = "stuff display"

    private val sellOffer: String
        get() = if (GuiConfig.useSellOffer) "Using sell offers" else "Using instasell"

    private val renderMainCroesusMenu: String
        get() = "Render Croesus Chest Overlay: ${GuiConfig.renderMainCroesusGui}"

    private fun formatPrice(price: String): Int {
        if (price.contains('.') || price.contains(',') || price.contains(' ')) {
            return -1
        }
        val lastChar = price.last().lowercaseChar()
        return when {
            lastChar == 'k' -> price.dropLast(1).toInt() * 1000
            lastChar == 'm' -> price.dropLast(1).toInt() * 1000000
            lastChar == 'b' -> price.dropLast(1).toInt() * 1000000000
            lastChar.isDigit() -> price.toIntOrNull() ?: -1
            else -> -1
        }
    }
}