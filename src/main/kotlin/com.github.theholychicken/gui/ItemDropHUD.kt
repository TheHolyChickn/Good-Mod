package com.github.theholychicken.gui

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.ItemDropParser
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard
import java.io.IOException

class ItemDropHUD : GuiScreen() {
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        val items = ItemDropParser.itemDropPatterns
        var xPos = 10
        var yPos = 10
        var columnWidth = 0

        mc.fontRendererObj.drawString("------- FLOOR 7 -------", xPos, yPos, 0x00FF99)
        yPos += mc.fontRendererObj.FONT_HEIGHT + 2

        for ((index, item) in items.values.withIndex()) {
            when (index) {
                17 -> {
                    mc.fontRendererObj.drawString("------- FLOOR 6 -------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                29 -> {
                    xPos += columnWidth + 10
                    columnWidth = 0
                    yPos = 5
                    mc.fontRendererObj.drawString("------ FLOOR 5 ------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                39 -> {
                    mc.fontRendererObj.drawString("------ FLOOR 4 ------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                47 -> {
                    mc.fontRendererObj.drawString("------ FLOOR 3 ------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                52 -> {
                    mc.fontRendererObj.drawString("------ FLOOR 2 ------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                56 -> {
                    xPos += columnWidth + 10
                    columnWidth = 0
                    yPos = 5
                    mc.fontRendererObj.drawString("----- FLOOR 1 -----", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                60 -> {
                    mc.fontRendererObj.drawString("------ STARS ------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                65 -> {
                    mc.fontRendererObj.drawString("------ SKULLS -----", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                70 -> {
                    mc.fontRendererObj.drawString("---- ENCHANTS ----", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                80 -> {
                    xPos += columnWidth + 10
                    columnWidth = 0
                    yPos = 5
                    mc.fontRendererObj.drawString("----- ULTIMATES -----", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                105 -> {
                    mc.fontRendererObj.drawString("-- UNIVERSAL DROPS --", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                109 -> {
                    mc.fontRendererObj.drawString("----- ESSENCE ------", xPos, yPos, 0x00FF99)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                else -> {
                    mc.fontRendererObj.drawString(item + ItemDropParser.dropsConfig.getItemCount(item).toString(), xPos, yPos, 0x00FFFF)
                }
            }
            yPos += mc.fontRendererObj.FONT_HEIGHT + 2
            val itemWidth = mc.fontRendererObj.getStringWidth(item + ItemDropParser.dropsConfig.getItemCount(item))
            if (itemWidth > columnWidth) {
                columnWidth = itemWidth
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(ConfigGUI())
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    companion object {
        fun open() {
            GoodMod.display = ItemDropHUD()
        }
    }
}
