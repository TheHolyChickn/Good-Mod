package com.github.theholychicken.gui

import com.github.theholychicken.managers.DungeonChestScanner
import com.github.theholychicken.managers.MainCroesusGuiParser
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11
import java.lang.reflect.Field

object MainCroesusHUD {
    private var guiLeft = 0
    private var guiTop = 0

    @SubscribeEvent
    fun onRenderGameOverlay(event: GuiScreenEvent.DrawScreenEvent.Post) {
        if (DungeonChestScanner.mainCroesusIsParsed) {
            if (event.gui is GuiChest) {
                val guiChest = event.gui as GuiContainer

                try {
                    val guiLeftField: Field = GuiContainer::class.java.getDeclaredField("field_147003_i")
                    guiLeftField.isAccessible = true
                    guiLeft = guiLeftField.getInt(guiChest)

                    val guiTopField: Field = GuiContainer::class.java.getDeclaredField("field_147009_r")
                    guiTopField.isAccessible = true
                    guiTop = guiTopField.getInt(guiChest)
                } catch (e: Exception) {
                    modMessage(e)
                }
            }

            GL11.glPushMatrix()
            GL11.glTranslated(0.0, 0.0, 10.0)
            MainCroesusGuiParser.openedChests.forEach { (pair, s) ->
                val x = pair.first + guiLeft
                val y = pair.second + guiTop
                when (s) {
                    MainCroesusGuiParser.RunStatus.FULLY_OPENED -> GuiScreen.drawRect(x, y, x + 16, y + 16, 0x80000000.toInt())
                    MainCroesusGuiParser.RunStatus.ONE_REMAINING -> GuiScreen.drawRect(x, y, x + 16, y + 16, 0x80E4D0AA.toInt())
                    MainCroesusGuiParser.RunStatus.UNOPENED -> GuiScreen.drawRect(x, y, x + 16, y + 16, 0x8000FF00.toInt())
                }
            }
            GL11.glPopMatrix()
        }
    }
}