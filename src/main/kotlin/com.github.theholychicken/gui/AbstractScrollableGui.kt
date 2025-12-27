package com.github.theholychicken.gui

import net.minecraft.client.gui.GuiScreen
import org.lwjgl.opengl.GL11
import org.lwjgl.input.Mouse
import java.io.IOException

abstract class AbstractScrollableGui : GuiScreen() {

    // scrolling state
    protected var scrollY = 0f
    protected var maxScroll = 0f
    private var isDraggingScrollbar = false

    // layout config
    open val topMargin: Int = 30
    open val bottomMargin: Int = 10
    open val leftMargin: Int = 20
    open val rightMargin: Int = 20
    open val contentTopPadding: Int = 10
    abstract val guiTitle: String


    /**
     * @return The total height of the scrollable content in pixels
     */
    abstract fun getContentHeight() : Int

    /**
     * Draw the gui content here
     * NOTE: Draw the content starting at y=0; the class handles the translation
     */
    abstract fun drawContent(mouseX: Int, mouseY: Int, partialTicks: Float)

    override fun initGui() {
        super.initGui()
        scrollY = 0f
        isDraggingScrollbar = false
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val dWheel = Mouse.getDWheel()
        if (dWheel != 0) {
            val scrollAmount = if (dWheel > 0) -20f else 20f
            scrollY += scrollAmount
            clampScroll()
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()

        val viewHeight = height - topMargin - bottomMargin
        val contentHeight = getContentHeight()
        val totalContentHeight = contentHeight + contentTopPadding
        maxScroll = (totalContentHeight - viewHeight).toFloat().coerceAtLeast(0f)

        clampScroll()

        val scale = mc.gameSettings.guiScale
        val factor = if (scale == 0) 1000 else scale

        GL11.glPushMatrix()
        GL11.glTranslatef(0f, -scrollY + topMargin + contentTopPadding, 0f)

        drawContent(mouseX, (mouseY + scrollY - topMargin - contentTopPadding).toInt(), partialTicks)

        GL11.glPopMatrix()

        // draw header and footer
        drawRect(0, 0, width, topMargin, 0xFF101010.toInt())
        drawGradientRect(0, topMargin, width, topMargin + 5, 0xFF0A0A0A.toInt(), 0x000A0A0A)

        drawRect(0, height - bottomMargin, width, height, 0xFF101010.toInt())
        drawGradientRect(0, height - bottomMargin - 5, width, height - bottomMargin, 0x000A0A0A, 0xFF0A0A0A.toInt()) // Shadow inverse

        // scrollbar
        if (maxScroll > 0) {
            drawScrollbar(mouseX, mouseY, viewHeight, totalContentHeight)
        }

        // title
        drawCenteredString(fontRendererObj, guiTitle, width / 2, 10, 0x00FFFF)

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    private fun drawScrollbar(mouseX: Int, mouseY: Int, viewHeight: Int, contentHeight: Int) {
        val barWidth = 6
        val barRight = width - 5
        val barLeft = barRight - barWidth
        val barTop = topMargin
        val barBottom = height - bottomMargin
        val trackHeight = barBottom - barTop

        // Calculate Thumb Height (proportional to view)
        // Ensure it's at least 30px so it's clickable
        val ratio = viewHeight.toFloat() / contentHeight.toFloat()
        val thumbHeight = (trackHeight * ratio).coerceAtLeast(30f).toInt()

        // Calculate Thumb Y Position
        // Map scrollY (0 to maxScroll) to track (0 to trackHeight - thumbHeight)
        val remainingTrack = trackHeight - thumbHeight
        val thumbY = barTop + (scrollY / maxScroll) * remainingTrack

        // Draw Track (Dark Grey)
        drawRect(barLeft, barTop, barRight, barBottom, 0x80000000.toInt())

        // Draw Thumb (Light Grey or White if hovering/dragging)
        val isHovering = mouseX in barLeft..barRight && mouseY in barTop..barBottom
        val color = if (isDraggingScrollbar || isHovering) 0xFFCCCCCC.toInt() else 0xFF808080.toInt()

        drawRect(barLeft, thumbY.toInt(), barRight, thumbY.toInt() + thumbHeight, color)

        // Handle dragging logic update
        if (isDraggingScrollbar) {
            // Inverse math: Calculate scrollY based on mouseY
            // relativeY is where the mouse is within the track
            val relativeMouseY = mouseY - barTop - (thumbHeight / 2) // Center thumb on mouse
            val scrollPercentage = relativeMouseY.toFloat() / remainingTrack

            scrollY = scrollPercentage * maxScroll
            clampScroll()
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        if (mouseButton == 0 && maxScroll > 0) {
            val barWidth = 6
            val barRight = width - 5
            val barLeft = barRight - barWidth

            // Check if clicked in scrollbar area
            if (mouseX in barLeft..barRight && mouseY in topMargin..(height - bottomMargin)) {
                isDraggingScrollbar = true
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        isDraggingScrollbar = false
    }

    private fun clampScroll() {
        if (scrollY < 0) scrollY = 0f
        if (scrollY > maxScroll) scrollY = maxScroll
    }

    protected fun getRelativeMouseY(absoluteMouseY: Int): Int {
        return (absoluteMouseY + scrollY - topMargin - contentTopPadding).toInt()
    }
}