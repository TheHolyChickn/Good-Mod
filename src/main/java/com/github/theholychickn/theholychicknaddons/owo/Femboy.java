package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Femboy {

    private static final Pattern angy = Pattern.compile("1xtile.thinStainedGlass@\\d+$");
    private static final Pattern wither_ess = Pattern.compile("§dWither Essence §8x\\d+");
    private static final Pattern undead_ess = Pattern.compile("§dUndead Essence §8x\\d+");
    private static final Pattern ess_count = Pattern.compile("(\\d+)$");
    private static ArrayList<String> many_owos = new ArrayList<>();
    private static Map<String, Integer> essence_owos = new HashMap<>();

    // Process instance of DUNGEON_CHEST
    public static void owo(ContainerChest the_owo) {
        // Ensure no owos save from past chests
        many_owos.clear();
        essence_owos.clear();

        // Log owos in many_owos
        for (int i = 9; i < 18; i++) {
            ItemStack owoed = the_owo.getLowerChestInventory().getStackInSlot(i);
            if (!angy.matcher(owoed.toString()).matches()) {
                // is a dungeon drop

                // Retrieve NBT Data
                NBTTagCompound tagCompound = owoed.getTagCompound();
                // Item name
                String displayName = tagCompound.getCompoundTag("display").getString("Name");

                // Check if item is instance of ENCHANTED_BOOK
                if (isReadableOwO(tagCompound)) {
                    byte STRING_NBT_TAG = new NBTTagString().getId();
                    String bookName = tagCompound.getCompoundTag("display").getTagList("Lore", STRING_NBT_TAG).getStringTagAt(0);
                    many_owos.add(bookName);
                }
                else if(wither_ess.matcher(displayName).matches()) {
                    // Check if item is instance of WITHER_ESSENCE
                    Matcher matcher = ess_count.matcher(displayName);
                    if (matcher.find()) {
                        essence_owos.put("Wither Essence", Integer.valueOf(matcher.group(1)));
                    }
                }
                else if(undead_ess.matcher(displayName).matches()) {
                    // Check if item is instance of UNDEAD_ESSENCE
                    Matcher matcher = ess_count.matcher(displayName);
                    if (matcher.find()) {
                        essence_owos.put("Undead Essence", Integer.valueOf(matcher.group(1)));
                    }
                }
                else {
                    // Item is instance of DUNGEON_ITEM
                    many_owos.add(displayName);
                }
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

    public static void dumpOWO() {
        // Save owo data
        for (String itemName : many_owos) {
            for (Map.Entry<String, String> thingy : Awoo.getAwooo().entrySet()) {
                if (thingy.getKey().equals(itemName)) {
                    Awoo.getAwA().addItem(thingy.getValue());
                    break;
                }
            }
        }
        for (Map.Entry<String, Integer> thingy : essence_owos.entrySet()) {
            if (thingy.getKey().equals("Wither Essence")) {
                Awoo.getAwA().addMany("§dWither Essence§r: §8", thingy.getValue());
            }
            else if (thingy.getKey().equals("Undead Essence")) {
                Awoo.getAwA().addMany("§dUndead Essence§r: §8", thingy.getValue());
            }
        }
    }
}
