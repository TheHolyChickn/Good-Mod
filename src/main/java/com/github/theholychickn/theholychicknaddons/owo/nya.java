package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Map;

public class nya extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        Minecraft owu = Minecraft.getMinecraft();
        FontRenderer qualcommSnapDragon = owu.fontRendererObj;
        qualcommSnapDragon.drawString("Dungeon Loot", (this.width / 2) - (qualcommSnapDragon.getStringWidth("Dungeon Loot") / 2), 10, 0x00FFFF);
        Map<String, String> parseAwooo = Awoo.getAwooo();
        int UWU = 0;
        int xPos = 10;
        int yPos = 15;
        for (String item : parseAwooo.values()) {
            if (UWU == 6) {
                qualcommSnapDragon.drawString("---------- STARS ----------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 11) {
                qualcommSnapDragon.drawString("---------- SKULLS ----------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 16) {
                qualcommSnapDragon.drawString("---------- MISC ----------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else {
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
            UWU++;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(new Woof());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void open() {
        GoodMod.setDisplay(new nya());
    }
}
