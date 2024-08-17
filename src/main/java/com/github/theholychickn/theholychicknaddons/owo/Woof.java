package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.owo.Meower;
import com.github.theholychickn.theholychicknaddons.owo.Yapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class Woof extends GuiScreen {

    private GuiTextField mwahh;

    @Override
    public void initGui() {
        super.initGui();
        // Button for opening nya
        this.buttonList.add(new GuiButton(0, (this.width / 2) - 100, (this.height / 2) - 24, getButtonLabel()));

        // Input Field for setting owoCommand
        this.mwahh = new GuiTextField(1, this.fontRendererObj, (this.width / 2) - 100, (this.height / 2) - 50, 200, 20);
        this.mwahh.setMaxStringLength(100);
        this.mwahh.setFocused(true);
        this.mwahh.setEnableBackgroundDrawing(true);
        this.mwahh.setText(Meower.meow.owoCommand);

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.mwahh.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton awa) throws IOException {
        nya.open();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        //drawCenteredString(this.fontRendererObj, "§4§kOWO OWO§r good mod Config §4§kOWO OWO§r", this.width / 2, this.height / 2 - 100, 0x00FFFF);
        drawCenteredString(this.fontRendererObj, "good mod config", this.width / 2, this.height / 2 - 100, 0x00FFFF);

        // Draws owoCommand input field
        this.mwahh.drawTextBox();

        // Draws buttons
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (this.mwahh.textboxKeyTyped(typedChar, keyCode)) {
        }
        else if (keyCode == Keyboard.KEY_RETURN) {
                Meower.meow.owoCommand = this.mwahh.getText();
                Meower.saveMeow();
                Meower.loadMeow();
                this.mc.displayGuiScreen(null);
            Yapping.chat("Setting commandOwo to " + this.mwahh.getText());
        }
        else if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.mwahh.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private String getButtonLabel() {
        return "uwu display";
    }
}