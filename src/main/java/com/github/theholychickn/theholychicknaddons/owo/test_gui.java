package com.github.theholychickn.theholychicknaddons.owo;

import net.java.games.input.Controller;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.logging.LogManager;

public class test_gui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        System.out.println("Calling drawScreen");
        try {
            drawDefaultBackground();
            Minecraft minecraft = Minecraft.getMinecraft();
            FontRenderer fr = minecraft.fontRendererObj;
            fr.drawString("Hello, World!", 10, 10, 0x00FFFF);
            super.drawScreen(mouseX, mouseY, partialTicks);
            System.out.println("Drawscreen finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
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
}
