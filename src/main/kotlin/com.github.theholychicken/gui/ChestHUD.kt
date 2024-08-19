package com.github.theholychicken.gui

import com.github.theholychicken.GoodMod
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.managers.ItemDropParser
import net.minecraft.client.gui.ScaledResolution
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ChestHUD {

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.Text?) {
        if (!GoodMod.showUwU) return
        val iwi = ScaledResolution(mc)

        val itemList = ItemDropParser.itemDropPatterns
        var previousStringWidth = 0
        for (item in itemList.values) {
            val stringWidth = mc.fontRendererObj.getStringWidth(item + ItemDropParser.dropsConfig.getItemCount(item).toString())
            if (stringWidth > previousStringWidth) previousStringWidth = stringWidth
        }
        val xPos = iwi.scaledWidth - previousStringWidth - 10
        var yPos = 10

        for (item in itemList.values) {
            mc.fontRendererObj.drawString(item + ItemDropParser.dropsConfig.getItemCount(item).toString(), xPos, yPos, 0x00FFFF)
            yPos += mc.fontRendererObj.FONT_HEIGHT + 2
        }
    }
}