package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Femboy {

    private static final Pattern angy = Pattern.compile("1xtile.thinStainedGlass@\\d+$");


    public static void owo(ContainerChest the_owo) {

        ArrayList<ItemStack> many_owos = new ArrayList<>();

        for (int i = 9; i < 18; i++) {
            ItemStack owoed = the_owo.getLowerChestInventory().getStackInSlot(i);
            if (!angy.matcher(owoed.toString()).matches()) {
                many_owos.add(owoed);
                GoodMod.Kitten.info("[good mod] Registered slot " + i + " (" + owoed.toString() + ") as dungeon loot");
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§r§3good mod §r§f» §r§4§lRegistered slot " + i + " (" + owoed.toString() + ") as dungeon loot§r"));
            }
        }
    }
}
