package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.owo.Yapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class UwU {
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (Meower.meow.showUwU) {
            ScaledResolution iwi = new ScaledResolution(mc);
            FontRenderer qualcommSnapDragon = mc.fontRendererObj;

            Map<String, String> parseAwooo = Awoo.getAwooo();
            int UWU = 0;
            for (String item : parseAwooo.values()) {
                int OWO = qualcommSnapDragon.getStringWidth(item + String.valueOf(Awoo.getAwA().getItemCount(item)));
                if (OWO > UWU) {
                    UWU = OWO;
                }
            }
            int xPos = iwi.getScaledWidth() - UWU - 10;
            int yPos = 10;

            for (String item : parseAwooo.values()) {
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
            }
        }

    }
}
