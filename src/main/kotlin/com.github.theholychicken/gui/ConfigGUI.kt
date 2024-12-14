package com.github.theholychicken.gui

import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import org.lwjgl.input.Keyboard
import java.io.IOException

class ConfigGUI : GuiScreen() {
    private lateinit var getItemsNameField: GuiTextField
    private lateinit var openGuiNameField: GuiTextField

    override fun initGui() {
        Keyboard.enableRepeatEvents(true)

        // Button for opening nya
        buttonList.add(GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, buttonLabel))

        // Input Field for setting owoCommand
        getItemsNameField = GuiTextField(1, fontRendererObj, this.width / 2 - 100, this.height / 2 - 50, 200, 20)
        getItemsNameField.maxStringLength = 32
        getItemsNameField.enableBackgroundDrawing = true
        getItemsNameField.text = GuiConfig.commandNames["getItems"]

        openGuiNameField = GuiTextField(2, fontRendererObj, this.width / 2 - 100, this.height / 2 - 76, 200, 20)
        openGuiNameField.maxStringLength = 32
        openGuiNameField.enableBackgroundDrawing = true
        openGuiNameField.text = GuiConfig.commandNames["goodmod"]

        getItemsNameField.isFocused = true  // Set initial focus on the first field
    }

    @Throws(IOException::class)
    override fun actionPerformed(button: GuiButton) {
        ItemDropHUD.open()
        super.actionPerformed(button)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        drawCenteredString(fontRendererObj, "Good Mod Config", width / 2, height / 2 - 100, 0x00FFFF)

        drawCenteredString(fontRendererObj, "Set /nicepb alias", width / 2 + 150, height / 2 - 70, 0x00FFFF)
        getItemsNameField.drawTextBox()

        drawCenteredString(fontRendererObj, "Set /owo alias", width / 2 + 150, height / 2 - 45, 0x00FFFF)
        openGuiNameField.drawTextBox()

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (getItemsNameField.isFocused) getItemsNameField.textboxKeyTyped(typedChar, keyCode)
        else if (openGuiNameField.isFocused) openGuiNameField.textboxKeyTyped(typedChar, keyCode)

        if (keyCode == Keyboard.KEY_RETURN) {
            if (getItemsNameField.isFocused) {
                GuiConfig.commandNames["getItems"] = getItemsNameField.text
                saveAndReloadConfig("getItems", getItemsNameField.text)
            } else if (openGuiNameField.isFocused) {
                GuiConfig.commandNames["goodmod"] = openGuiNameField.text
                saveAndReloadConfig("goodmod", openGuiNameField.text)
            }
        } else if (keyCode == Keyboard.KEY_ESCAPE) mc.displayGuiScreen(null)
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        getItemsNameField.mouseClicked(mouseX, mouseY, mouseButton)
        openGuiNameField.mouseClicked(mouseX, mouseY, mouseButton)

        // Set focus based on where the click happened
        if (getItemsNameField.isFocused) openGuiNameField.isFocused = false
        else if (openGuiNameField.isFocused) getItemsNameField.isFocused = false

        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun updateScreen() {
        getItemsNameField.updateCursorCounter()
        openGuiNameField.updateCursorCounter()
        super.updateScreen()
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    private val buttonLabel: String
        get() = "Stuff Display"

    private fun saveAndReloadConfig(commandKey: String, newValue: String) {
        GuiConfig.saveConfig()
        GuiConfig.loadConfig()
        mc.displayGuiScreen(null)
        modMessage("Set /$commandKey to /$newValue! §r§2§lRestart game for changes to take effect.")
    }
}
