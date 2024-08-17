package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Pattern;

public class Fuwwy {

    private static final Pattern chestNamePattern = Pattern.compile("(Wood|Gold|Diamond|Emerald|Obsidian|Bedrock) Chest");
    private boolean isScanning = false;
    private ContainerChest currentContainer = null;
    private int scanTicks = 0;

    @SubscribeEvent
    public void owoing(GuiOpenEvent owo) {

        GuiScreen owoGui = owo.gui;
        if (!(owoGui instanceof GuiChest)) return;

        ContainerChest owoContainer = (ContainerChest) ((GuiChest) owoGui).inventorySlots;
        String owoName = owoContainer.getLowerChestInventory().getName();

        if (!chestNamePattern.matcher(owoName).matches()) return;

        GoodMod.Kitten.info("[good mod]" + owoName + " detected");

        currentContainer = owoContainer;
        isScanning = true;
        scanTicks = 0;
    }

    @SubscribeEvent
    public void searchOWOs(TickEvent.ClientTickEvent awooo) {
        // Only execute during the END phase to avoid running twice per tick
        if (awooo.phase != TickEvent.Phase.END || !isScanning || currentContainer == null) return;
        GoodMod.Kitten.info("[good mod] [searchOWOs] Scanning protocol initiated - awaiting fully loaded dungeon chest");

        GuiScreen currScrene = Minecraft.getMinecraft().currentScreen;
        if (!(currScrene instanceof GuiChest)) {
            isScanning = false;
            currentContainer = null;
            GoodMod.Kitten.info("[good mod] [searchOWOs] Chest closed, terminating scanning protocol");
            return;
        }
        else {
            currentContainer = (ContainerChest) ((GuiChest) currScrene).inventorySlots;
        }

        int numSlots = currentContainer.inventorySlots.size();
        int bottomRightSlotIndex = numSlots - 1;
        ItemStack bottomRight = currentContainer.getLowerChestInventory().getStackInSlot(bottomRightSlotIndex);

        if (bottomRight != null) {
            GoodMod.Kitten.info("[good mod] [searchOWOs] Instance of DUNGEON_CHEST has been fully loaded, scanning protocol terminated");
            isScanning = false;

            // run code here

            currentContainer = null;
            return;
        }

        scanTicks++;
        if (scanTicks > 200) {
            isScanning = false;
            currentContainer = null;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§r§bgood mod §r§f>> §r§4§lDungeon chest not loaded [timed out]. §r§bIs the server lagging?§r"));
            scanTicks = 0;
        }
    }
}
