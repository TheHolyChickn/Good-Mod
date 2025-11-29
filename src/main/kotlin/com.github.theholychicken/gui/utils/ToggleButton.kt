package com.github.theholychicken.gui.utils

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import org.lwjgl.opengl.GL11
import kotlin.math.cos
import kotlin.math.sin

/**
 * Implements a toggleable slider
 * @param x the x pos of the slider
 * @param y the y pos of the slider
 * @param width the width of the slider
 * @param height the height of the slider
 * @param initialState whether the slider should start enabled or disabled
 * @param label an optional label to be dynamically rendered above the toggle
 * @param color the color of the optional label
 * is this code ai generated...? - ilo
 */
class ToggleButton(id: Int,
                   private val x: Int,
                   private val y: Int,
                   private val width: Int,
                   private val height: Int,
                   initialState: Boolean,
                   private val label: String?,
                   private val color: Int?
) : GuiButton(id, x, y, width, height, "") {

    var toggled = initialState
        private set

    private val knobWidth = height

    override fun drawButton(mc: Minecraft?, mouseX: Int, mouseY: Int) {
        if (!visible) return

        // determine hover state
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height

        // draws a track - border rectangle first, then inner track
        drawRect(x, y, x + width, y + height, 0xFF333333.toInt())
        drawRect(x + 1, y + 1, x + width - 1, y + height - 1, 0xFF888888.toInt())

        // determine knob X position
        // when enabled, knob goes on the right, when disabled, knob goes on the left
        val knobX = if (toggled) x + width - knobWidth - 1 else x + 1

        // knob color - green for on, red for off
        val knobColor = if (toggled) 0xFF00AA00.toInt() else 0xFFAA0000.toInt()

        // draw the knob
        drawRect(knobX, y + 1, knobX + knobWidth, y + height - 1, knobColor)

        label?.let { drawLabelAboveToggle(mc, it, x, y, width, color ?: 0xFFFFFF) }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        if (hovered) {
            toggled = !toggled
        }
    }

    private fun drawLabelAboveToggle(mc:Minecraft?, label: String?, toggleX: Int, toggleY: Int, toggleWidth: Int, color: Int) {
        if (mc == null) return

        val maxTextWidth = toggleWidth + 45
        val lines = mc.fontRendererObj.listFormattedStringToWidth(label, maxTextWidth)

        val verticalMargin = 3
        val maxVerticalSpace = 80 - verticalMargin
        val lineHeight = mc.fontRendererObj.FONT_HEIGHT
        val maxLines = maxVerticalSpace / lineHeight

        val drawnLines = if (lines.size > maxLines) lines.takeLast(maxLines) else lines

        val textBlockHeight = drawnLines.size * lineHeight
        var currentY = toggleY - verticalMargin - textBlockHeight

        drawnLines.forEach { line ->
            val lineWidth = mc.fontRendererObj.getStringWidth(line)
            val textX = toggleX + (toggleWidth - lineWidth) / 2
            mc.fontRendererObj.drawString(line, textX, currentY, color)
            currentY += lineHeight
        }
    }
}