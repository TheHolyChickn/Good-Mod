package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Pattern;

public class Fuwwy {

    private static final Pattern owoPattern = Pattern.compile("(Wood|Gold|Diamond|Emerald|Obsidian|Bedrock) Chest");
    private boolean is_owoing = false;
    private ContainerChest the_owo = null;
    private int many_owos = 0;

    @SubscribeEvent
    public void owoing(GuiOpenEvent owo) {

        GuiScreen owoGui = owo.gui;
        if (!(owoGui instanceof GuiChest)) return;

        ContainerChest owoContainer = (ContainerChest) ((GuiChest) owoGui).inventorySlots;
        String owoName = owoContainer.getLowerChestInventory().getName();

        if (!owoPattern.matcher(owoName).matches()) return;

        GoodMod.Kitten.info("[good mod]" + owoName + " detected");

        the_owo = owoContainer;
        is_owoing = true;
        many_owos = 0;
    }

    @SubscribeEvent
    public void searchOWOs(TickEvent.ClientTickEvent awooo) {
        // Only execute during the END phase to avoid running twice per tick
        if (awooo.phase != TickEvent.Phase.END || !is_owoing || the_owo == null) return;
        GoodMod.Kitten.info("[good mod] [searchOWOs] Scanning protocol active - awaiting fully loaded dungeon chest");

        GuiScreen currScrene = Minecraft.getMinecraft().currentScreen;
        if (!(currScrene instanceof GuiChest)) {
            stopOwoing();
            GoodMod.Kitten.info("[good mod] [searchOWOs] Chest closed, terminating scanning protocol");
            return;
        }
        else {
            the_owo = (ContainerChest) ((GuiChest) currScrene).inventorySlots;
        }

        int paws = the_owo.getLowerChestInventory().getSizeInventory();
        int bottomRightSlotIndex = paws - 1;
        ItemStack bottomRight = the_owo.getLowerChestInventory().getStackInSlot(bottomRightSlotIndex);

        if (bottomRight != null) {
            GoodMod.Kitten.info("[good mod] [searchOWOs] Instance of DUNGEON_CHEST has been fully loaded, scanning protocol terminated");
            is_owoing = false;
            many_owos = 0;

            // run code here
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§r§3good mod §r§f» §r§eInstance of DUNGEON_CHEST fully loaded. Scanning protocol terminated. Dungeon chest loaded: " + the_owo.getLowerChestInventory().getName() + "§r"));

            Femboy.owo(the_owo);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§r§3good mod §r§f» §r§2Items logged!"));

            the_owo = null;
            return;
        }

        many_owos++;
        if (many_owos > 200) {
            stopOwoing();
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§r§3good mod §r§f» §r§4§lDungeon chest not loaded [timed out]. §r§bIs the server lagging?§r"));
        }
    }

    private void stopOwoing() {
        is_owoing = false;
        the_owo = null;
        many_owos = 0;
    }
}
