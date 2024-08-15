package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.owo.Meower;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class Woof extends GuiScreen {

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width / 2) - 100, (this.height / 2) - 24, getButtonLabel()));
    }

    @Override
    protected void actionPerformed(GuiButton awa) throws IOException {
        nya.open();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        //drawCenteredString(this.fontRendererObj, "§4§kOWO OWO§r good mod Config §4§kOWO OWO§r", this.width / 2, this.height / 2 - 100, 0x00FFFF);
        drawCenteredString(this.fontRendererObj, "good mod Config", this.width / 2, this.height / 2 - 100, 0x00FFFF);
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private String getButtonLabel() {
        return "Stuff Display: " + (Meower.meow.showUwU ? "On" : "Off");
    }
}