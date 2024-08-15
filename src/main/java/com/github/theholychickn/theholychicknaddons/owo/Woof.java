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
        System.out.println("Button Pressed");
    }

    @Override
    protected void actionPerformed(GuiButton awa) throws IOException {
        if (awa.enabled) {
            if (awa.id == 0) {
                Meower.meow.showUwU = !Meower.meow.showUwU;
                Meower.saveMeow();
                awa.displayString = getButtonLabel();
            }
            System.out.println("UWU Toggled");
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        System.out.println("Drawing screen");
        drawDefaultBackground();
        System.out.println("Background Drawn!");
        drawCenteredString(this.fontRendererObj, "good mod Config", this.width / 2, this.height / 2 - 60, 0x00FFFF);
        System.out.println("Text Drawn!");
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
            System.out.println("Button Drawn!");
        }
        System.out.println("Screen drawn!");
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
        return "UwU Display: " + (Meower.meow.showUwU ? "On" : "Off");
    }

    //public static void open() {
     //   System.out.println("Calling Woof.open()");
      //  Minecraft.getMinecraft().displayGuiScreen(new Woof());
    //}
}