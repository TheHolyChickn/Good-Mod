package com.github.theholychickn.theholychicknaddons.owo;

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
        Map<String, String> parseAwooo = Awoo.getAwooo();
        int UWU = 0;
        int xPos = 10;
        int yPos = 10;
        int newXPos = 0;
        int testXPos = 0;
        qualcommSnapDragon.drawString("------- FLOOR 7 -------", xPos, yPos, 0x00FF99);
        yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
        for (String item : parseAwooo.values()) {
            if (UWU == 17) {
                qualcommSnapDragon.drawString("------- FLOOR 6 -------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 29) {
                xPos += newXPos + 10;
                newXPos = 0;
                yPos = 5;
                qualcommSnapDragon.drawString("------ FLOOR 5 ------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 39) {
                qualcommSnapDragon.drawString("------ FLOOR 4 ------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 47) {
                //f3
                qualcommSnapDragon.drawString("------ FLOOR 3 ------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 52) {
                //f2
                qualcommSnapDragon.drawString("------ FLOOR 2 ------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 56) {
                //f1
                xPos += newXPos + 10;
                newXPos = 0;
                yPos = 5;
                qualcommSnapDragon.drawString("----- FLOOR 1 -----", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 60) {
                //stars: new column
                qualcommSnapDragon.drawString("------ STARS ------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 65) {
                //skulls
                qualcommSnapDragon.drawString("------ SKULLS -----", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 70) {
                //reg enchs
                qualcommSnapDragon.drawString("---- ENCHANTS ----", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 80) {
                //ult enchants: new column
                xPos += newXPos + 10;
                newXPos = 0;
                yPos = 5;
                qualcommSnapDragon.drawString("----- ULTIMATES -----", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 105) {
                //universals
                qualcommSnapDragon.drawString("-- UNIVERSAL DROPS --", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else if (UWU == 109) {
                //ess
                qualcommSnapDragon.drawString("----- ESSENCE ------", xPos, yPos, 0x00FF99);
                yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            else {
                qualcommSnapDragon.drawString(item + String.valueOf(Awoo.getAwA().getItemCount(item)), xPos, yPos, 0x00FFFF);
            }
            yPos += qualcommSnapDragon.FONT_HEIGHT + 2;
            UWU++;
            testXPos = qualcommSnapDragon.getStringWidth(item + Awoo.getAwA().getItemCount(item));
            if (testXPos > newXPos) {
                newXPos = testXPos;
            }
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
