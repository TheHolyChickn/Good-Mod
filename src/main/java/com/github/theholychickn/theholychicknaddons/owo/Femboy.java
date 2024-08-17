package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Femboy {

    private static final Pattern angy = Pattern.compile("1xtile.thinStainedGlass@\\d+$");
    private static final Pattern wither_ess = Pattern.compile("§dWither Essence §8x\\d+");
    private static final Pattern undead_ess = Pattern.compile("§dUndead Essence §8x\\d+");
    private static final Pattern ess_count = Pattern.compile("(\\d+)$");

    // Process instance of DUNGEON_CHEST
    public static void owo(ContainerChest the_owo) {

        ArrayList<ItemStack> many_owos = new ArrayList<>();

        for (int i = 9; i < 18; i++) {
            ItemStack owoed = the_owo.getLowerChestInventory().getStackInSlot(i);
            if (!angy.matcher(owoed.toString()).matches()) {
                many_owos.add(owoed);
                Yapping.log("Registered slot " + i + " (" + owoed.toString() + ") as dungeon loot");
            }
        }

        // Parse the item data
        for (ItemStack owoed : many_owos) {
            // Retrieve NBT Data
            NBTTagCompound tagCompound = owoed.getTagCompound();
            // Item name
            String displayName = tagCompound.getCompoundTag("display").getString("Name");

            // Check if item is instance of ENCHANTED_BOOK
            if (isReadableOwO(tagCompound)) {
                byte STRING_NBT_TAG = new NBTTagString().getId();
                String bookName = tagCompound.getCompoundTag("display").getTagList("Lore", STRING_NBT_TAG).getStringTagAt(0);
                Yapping.chat("Registered instance of ENCHANTED_BOOK: " + bookName);
            }
            else if(wither_ess.matcher(displayName).matches()) {
                Matcher matcher = ess_count.matcher(displayName);
                if (matcher.find()) {
                    Yapping.chat("Registered instance of WITHER_ESSENCE, count: " + matcher.group(1));
                }
            }
            else if(undead_ess.matcher(displayName).matches()) {
                Matcher matcher = ess_count.matcher(displayName);
                if (matcher.find()) {
                    Yapping.chat("Registered instance of UNDEAD_ESSENCE, count: " + matcher.group(1));
                }
            }
            else {
                Yapping.chat("Registered instance of DUNGEON_ITEM: " + displayName);
            }
        }
    }

    // Checks if NBT data defines instance of ENCHANTED_BOOK
    public static boolean isReadableOwO(NBTTagCompound tagCompound) {

        // Check is done with item ID to avoid different rarity books interfering, while also avoiding regex
        // You could do a regex statement like &\wEnchanted Book&r
        String itemId = tagCompound.getCompoundTag("ExtraAttributes").getString("id");
        return itemId.equals("ENCHANTED_BOOK");
    }
}
