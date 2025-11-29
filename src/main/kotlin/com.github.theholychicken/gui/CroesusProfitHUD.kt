package com.github.theholychicken.gui

import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.CroesusChestParser
import com.github.theholychicken.managers.DungeonChestScanner
import com.github.theholychicken.utils.modMessage
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11
import java.lang.reflect.Field

object CroesusProfitHUD {
    private val mc = Minecraft.getMinecraft()

    @SubscribeEvent
    fun onRenderGameOverlay(event: GuiScreenEvent.DrawScreenEvent.Post) {
        if (DungeonChestScanner.croesusIsParsed) {
            if (CroesusChestParser.runLoot.isEmpty()) return
            val scale = mc.gameSettings.guiScale
            val width = mc.displayWidth / scale
            val height = mc.displayHeight / scale
            var guiLeft = 0
            var guiTop = 0

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

            val offsetX = 100
            val chestWidth = 176
            val chestHeight = 166
            val rectX1 = width / 2 + offsetX
            val rectX2 = rectX1 + chestWidth
            val rectY1 = height / 2 - chestHeight / 2
            val rectY2 = rectY1 + chestHeight

            val slot = CroesusChestParser.runLoot.maxByOrNull {
                it.profit
            }?.location ?: Pair(0, 0)

            val slotX = slot.first + guiLeft
            val slotY = slot.second + guiTop

            GuiScreen.drawRect(rectX1, rectY1, rectX2, rectY2, 0x80000000.toInt())
            mc.fontRendererObj.drawString(
                "LOOT",
                rectX1 + chestWidth / 2 - mc.fontRendererObj.getStringWidth("LOOT") / 2,
                rectY1 + 10,
                0x00FFFF
            )
            CroesusChestParser.runLoot
                .filter { !it.purchased }
                .sortedByDescending { it.profit }
                .forEachIndexed { index, chest ->
                    mc.fontRendererObj.drawString(
                        chest.name
                                + "§r§f: §r§${if (chest.profit >= 0) "a" else "c"}"
                                + String.format("%,d", chest.profit.toInt())
                                + " coins",
                        rectX1 + 10,
                        rectY1 + 30 + index * mc.fontRendererObj.FONT_HEIGHT,
                        0x00FFFF
                    )
                }

            GL11.glPushMatrix()
            GL11.glTranslated(0.0, 0.0, 10.0)
            if (CroesusChestParser.keyStatus) {
                val slot2 = CroesusChestParser
                    .runLoot
                    .sortedByDescending { it.profit }
                    .getOrNull(1)
                    ?.location
                if (slot2 != null) {
                    val slotX2 = slot2.first + guiLeft
                    val slotY2 = slot2.second + guiTop
                    GuiScreen.drawRect(slotX2, slotY2, slotX2 + 16, slotY2 + 16, 0x80E4D0AA.toInt())
                }
                GuiScreen.drawRect(slotX, slotY, slotX + 16, slotY + 16, 0x8000FF00.toInt())
            } else if (CroesusChestParser.runLoot.maxOf { it.profit } > GuiConfig.minChestPurchase) {
                GuiScreen.drawRect(slotX, slotY, slotX + 16, slotY + 16, 0x8000FF00.toInt())
            }
            GL11.glPopMatrix()
        }
    }

    /*
    // Debug method
    fun sendLootChat() {
        CroesusChestParser.runLoot
            .filter { !it.purchased }
            .sortedByDescending { it.profit }
            .forEach { chest ->
                mc.thePlayer.addChatMessage(
                    ChatComponentText(
                        chest.name
                                + "§r§b: §r§5"
                                + String.format("%,d", chest.profit.toInt())
                                + " coins"
                    )
                )
            }
    }
     */
}