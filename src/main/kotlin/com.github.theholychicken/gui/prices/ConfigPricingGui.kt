package com.github.theholychicken.gui.prices

import com.github.theholychicken.GoodMod
import com.github.theholychicken.gui.ConfigGUI
import com.github.theholychicken.gui.prices.catalogs.*
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard

class ConfigPricingGui : GuiScreen() {

    override fun initGui() {
        super.initGui()
        buttonList.clear()

        // Layout: 3 Columns
        val midX = width / 2
        val midY = height / 2
        val btnWidth = 100
        val btnHeight = 20

        // Column 1 (Floors 7, 4, 1)
        buttonList.add(GuiButton(0, midX - 175, midY - 25, btnWidth, btnHeight, "floor 7"))
        buttonList.add(GuiButton(3, midX - 175, midY,     btnWidth, btnHeight, "floor 4"))
        buttonList.add(GuiButton(6, midX - 175, midY + 25, btnWidth, btnHeight, "floor 1"))

        // Column 2 (Floors 6, 3, Enchants)
        buttonList.add(GuiButton(1, midX - 50, midY - 25, btnWidth, btnHeight, "floor 6"))
        buttonList.add(GuiButton(4, midX - 50, midY,     btnWidth, btnHeight, "floor 3"))
        buttonList.add(GuiButton(7, midX - 50, midY + 25, btnWidth, btnHeight, "enchants"))
        buttonList.add(GuiButton(9, midX - 50, midY + 50, btnWidth, btnHeight, "miscellaneous"))

        // Column 3 (Floors 5, 2, Ults)
        buttonList.add(GuiButton(2, midX + 75, midY - 25, btnWidth, btnHeight, "floor 5"))
        buttonList.add(GuiButton(5, midX + 75, midY,     btnWidth, btnHeight, "floor 2"))
        buttonList.add(GuiButton(8, midX + 75, midY + 25, btnWidth, btnHeight, "ult enchants"))

        // Exit
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "back"))
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> Floor7PricingGui.open()
            1 -> Floor6PricingGui.open()
            2 -> Floor5PricingGui.open()
            3 -> Floor4PricingGui.open()
            4 -> Floor3PricingGui.open()
            5 -> Floor2PricingGui.open()
            6 -> Floor1PricingGui.open()
            7 -> EnchantsPricingGui.open()
            8 -> UltEnchantsPricingGui.open()
            9 -> MiscPricingGui.open()
            100 -> GoodMod.display = ConfigGUI()
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        drawCenteredString(fontRendererObj, "pricing config", width / 2, height / 2 - 50, 0x00FFFF)
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE) GoodMod.display = ConfigGUI()
    }

    override fun doesGuiPauseGame() = false

    companion object {
        fun open() {
            GoodMod.display = ConfigPricingGui()
        }
    }
}