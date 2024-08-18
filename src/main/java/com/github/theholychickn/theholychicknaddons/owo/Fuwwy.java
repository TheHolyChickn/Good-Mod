package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import com.github.theholychickn.theholychicknaddons.owo.Yapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.regex.Pattern;

public class Fuwwy {

    private static final Pattern owoPattern = Pattern.compile("(Wood|Gold|Diamond|Emerald|Obsidian|Bedrock) Chest");
    private boolean is_owoing = false;
    private ContainerChest the_owo = null;
    private int many_owos = 0;
    private boolean chest_gui = false;
    private Femboy femboy = new Femboy();

    // Check for instance of DUNGEON_CHEST
    @SubscribeEvent
    public void owoing(GuiOpenEvent owo) {

        GuiScreen owoGui = owo.gui;
        if (chest_gui && !(owoGui instanceof GuiChest)) {
            chest_gui = false;
            return;
        }
        if (!(owoGui instanceof GuiChest)) return;

        ContainerChest owoContainer = (ContainerChest) ((GuiChest) owoGui).inventorySlots;
        String owoName = owoContainer.getLowerChestInventory().getName();

        if (!owoPattern.matcher(owoName).matches()) {
            chest_gui = false;
            return;
        }
        else {
            chest_gui = true;
        }

        Yapping.log(owoName + " detected");

        the_owo = owoContainer;
        is_owoing = true;
        many_owos = 0;
    }

    // Await loaded chest
    @SubscribeEvent
    public void searchOWOs(TickEvent.ClientTickEvent awooo) {
        // Only execute during the END phase to avoid running twice per tick
        if (awooo.phase != TickEvent.Phase.END || !is_owoing || the_owo == null) return;
        Yapping.log("[searchOWOs] Scanning protocol active - awaiting fully loaded dungeon chest");

        GuiScreen currScrene = Minecraft.getMinecraft().currentScreen;
        if (!(currScrene instanceof GuiChest)) {
            stopOwoing();
            Yapping.log("[searchOWOs] Chest closed, terminating scanning protocol");
            chest_gui = false;
            return;
        }
        else {
            the_owo = (ContainerChest) ((GuiChest) currScrene).inventorySlots;
        }

        int paws = the_owo.getLowerChestInventory().getSizeInventory();
        int bottomRightSlotIndex = paws - 1;
        ItemStack bottomRight = the_owo.getLowerChestInventory().getStackInSlot(bottomRightSlotIndex);

        if (bottomRight != null) {
            // Chest has loaded
            Yapping.log("[searchOWOs] Instance of DUNGEON_CHEST has been fully loaded, scanning protocol terminated");
            is_owoing = false;
            many_owos = 0;
            Yapping.chat("§r§eInstance of DUNGEON_CHEST fully loaded. Scanning protocol terminated. Dungeon chest loaded: " + the_owo.getLowerChestInventory().getName());

            // Store items
            femboy.owo(the_owo);

            Yapping.chat("§r§2Items logged!");

            // End protocol (chest gui will remain open)
            the_owo = null;
            return;
        }

        // Timeout protocol
        many_owos++;
        if (many_owos > 200) {
            stopOwoing();
            Yapping.chat("§r§4§lDungeon chest not loaded [timed out]. §r§bIs the server lagging?§r");
            chest_gui = false;
        }
    }

    // Store loot on chest close
    @SubscribeEvent
    public void onSlotClick(GuiScreenEvent.MouseInputEvent event) {
        // Check if items have already been saved
        if (!chest_gui) return;
        // Check if instance of DUNGEON_CHEST
        if (!(event.gui instanceof GuiChest)) return;
        if (Mouse.getEventButton() == -1) return;

        GuiScreen currScreen = event.gui;
        ContainerChest containerChest = (ContainerChest) ((GuiChest) currScreen).inventorySlots;
        if (!owoPattern.matcher(containerChest.getLowerChestInventory().getName()).matches()) return;
        // Safeguard against null pointer things
        Slot clickedSlot = null;
        try {
            clickedSlot = ((GuiChest) currScreen).getSlotUnderMouse();
        } catch (Exception e){
            //
        }
        if (clickedSlot != null && Mouse.getEventButtonState() && clickedSlot.slotNumber == 31) {
            Yapping.chat("§r§c§lDungeon chest claimed! §r§bParsing data now");
            Femboy.dumpOWO();
            Yapping.chat("§r§aData parsed!");
            chest_gui = false;
        }
    }

    private void stopOwoing() {
        is_owoing = false;
        the_owo = null;
        many_owos = 0;
    }
}
