package com.github.theholychicken.gui

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.ItemDropParser
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

        drawHeader("------- FLOOR 7 -------", xPos, yPos)
        yPos += mc.fontRendererObj.FONT_HEIGHT + 2

        items.values.forEachIndexed { index, item ->
            when (index) {
                17 -> {
                    drawFloorHeader("------- FLOOR 6 -------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                29 -> {
                    xPos += columnWidth + 10
                    columnWidth = 0
                    yPos = 10 // Reset yPos for the new column
                    drawFloorHeader("------ FLOOR 5 ------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                39 -> {
                    drawFloorHeader("------ FLOOR 4 ------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                47 -> {
                    drawFloorHeader("------ FLOOR 3 ------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                52 -> {
                    drawFloorHeader("------ FLOOR 2 ------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                56 -> {
                    xPos += columnWidth + 10
                    columnWidth = 0
                    yPos = 10 // Reset yPos for the new column
                    drawFloorHeader("----- FLOOR 1 -----", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                60 -> {
                    drawFloorHeader("------ STARS ------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                65 -> {
                    drawFloorHeader("------ SKULLS -----", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                70 -> {
                    drawFloorHeader("---- ENCHANTS ----", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                80 -> {
                    xPos += columnWidth + 10
                    columnWidth = 0
                    yPos = 10 // Reset yPos for the new column
                    drawFloorHeader("----- ULTIMATES -----", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                105 -> {
                    drawFloorHeader("-- UNIVERSAL DROPS --", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                109 -> {
                    drawFloorHeader("----- ESSENCE ------", xPos, yPos)
                    yPos += mc.fontRendererObj.FONT_HEIGHT + 2
                }
                else -> drawItem(item, xPos, yPos)
            }
            yPos += mc.fontRendererObj.FONT_HEIGHT + 2
            columnWidth = updateColumnWidth(item, columnWidth)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    private fun drawHeader(text: String, x: Int, y: Int) {
        mc.fontRendererObj.drawString(text, x, y, 0x00FF99)
    }

    private fun drawFloorHeader(header: String, x: Int, y: Int) {
        drawHeader(header, x, y)
    }

    private fun drawItem(item: String, x: Int, y: Int) {
        mc.fontRendererObj.drawString("$item${ItemDropParser.dropsConfig.getItemCount(item)}", x, y, 0x00FFFF)
    }

    private fun updateColumnWidth(item: String, currentWidth: Int): Int {
        val itemWidth = mc.fontRendererObj.getStringWidth("$item${ItemDropParser.dropsConfig.getItemCount(item)}")
        return maxOf(currentWidth, itemWidth)
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

    override fun doesGuiPauseGame(): Boolean = false

    companion object {
        fun open() {
            GoodMod.display = ItemDropHUD()
        }
    }
}
