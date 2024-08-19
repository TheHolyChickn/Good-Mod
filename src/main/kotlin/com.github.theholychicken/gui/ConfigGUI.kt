package com.github.theholychicken.gui

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.*
import org.lwjgl.input.Keyboard
import java.io.IOException

class ConfigGUI : GuiScreen() {
    private var getItemsNameField: GuiTextField? = null
    private var openGuiNameField: GuiTextField? = null

    override fun initGui() {
        super.initGui()
        // Button for opening nya
        buttonList.add(
            GuiButton(
                0, (this.width / 2) - 100, (this.height / 2) - 24,
                buttonLabel
            )
        )

        // Input Field for setting owoCommand
        this.getItemsNameField = GuiTextField(1, this.fontRendererObj, (this.width / 2) - 100, (this.height / 2) - 50, 200, 20)
        getItemsNameField?.maxStringLength = 100
        getItemsNameField?.isFocused = true
        getItemsNameField?.enableBackgroundDrawing = true
        getItemsNameField?.text = GuiConfig.commandNames["getItems"]
        this.openGuiNameField = GuiTextField(2, this.fontRendererObj, (this.width / 2) - 100, (this.height / 2) - 76, 200, 20)
        openGuiNameField?.maxStringLength = 100
        openGuiNameField?.isFocused = true
        openGuiNameField?.enableBackgroundDrawing = true
        openGuiNameField?.text = GuiConfig.commandNames["goodmod"]
    }

    override fun updateScreen() {
        super.updateScreen()
        getItemsNameField?.updateCursorCounter()
        openGuiNameField?.updateCursorCounter()
    }

    @Throws(IOException::class)
    override fun actionPerformed(awa: GuiButton) {
        ItemDropHUD.open()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        //drawCenteredString(this.fontRendererObj, "§4§kOWO OWO§r good mod Config §4§kOWO OWO§r", this.width / 2, this.height / 2 - 100, 0x00FFFF);
        drawCenteredString(this.fontRendererObj, "good mod config", this.width / 2, this.height / 2 - 100, 0x00FFFF)

        // Draws owoCommand input field
        drawCenteredString(
            this.fontRendererObj, "set /nicepb alias",
            this.width / 2 + 150,
            this.height / 2 - 70, 0x00FFFF
        )
        getItemsNameField?.drawTextBox()

        drawCenteredString(this.fontRendererObj, "set /owo alias", this.width / 2 + 150, this.height / 2 - 45, 0x00FFFF)
        openGuiNameField?.drawTextBox()

        // Draws buttons
        for (button in this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY)
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (this.getItemsNameField?.textboxKeyTyped(typedChar, keyCode) == true) {
        }
        else if (this.openGuiNameField?.textboxKeyTyped(typedChar, keyCode) == true) {
        }
        if (keyCode == Keyboard.KEY_RETURN) {
            if (getItemsNameField?.isFocused == true) {
                GuiConfig.commandNames["getItems"] = getItemsNameField!!.text
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                mc.displayGuiScreen(null)
                modMessage("Set /getItems to /" + getItemsNameField!!.text + "! §r§2§lRestart game for changes to take effect.")
            } else if (openGuiNameField?.isFocused == true) {
                GuiConfig.commandNames["goodmod"] = openGuiNameField!!.text
                GuiConfig.saveConfig()
                GuiConfig.loadConfig()
                mc.displayGuiScreen(null)
                modMessage("Set /goodmod to /" + openGuiNameField!!.text + "! §r§2§lRestart game for changes to take effect.")
            }
        } else if (keyCode == Keyboard.KEY_ESCAPE) mc.displayGuiScreen(null)
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        getItemsNameField?.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    private val buttonLabel: String
        get() = "stuff display"
}