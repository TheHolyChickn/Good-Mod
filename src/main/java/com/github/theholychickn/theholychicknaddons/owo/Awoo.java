package com.github.theholychickn.theholychicknaddons.owo;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

// The Awoo class parses chat messages that fit a key of the hashmap awooo, and save to an AwA
public class Awoo {

    // manages the item drop cache
    public static AwA awa = new AwA();

    // list of drops to track, key is chat message and value is message returned in /owo
    private static final Map<String, String> awooo = new LinkedHashMap<>();
    // dumping items to watch
    static {
        awooo.put("§5Necron's Handle", "§r§5Necron's Handles§r: ");
        awooo.put("§5Implosion", "§r§5Implosions§r: ");
        awooo.put("§5Shadow Warp", "§r§5Shadow Warps§r: ");
        awooo.put("§5Wither Shield", "§r§5Wither Shields§r: ");
        awooo.put("§6Dark Claymore", "§r§6Dark Claymores§r: ");
        awooo.put("§6Giant's Sword", "§r§6Giant's Swords§r: ");
        awooo.put("§5First Master Star", "§r§5First Stars§r: ");
        awooo.put("§5Second Master Star", "§r§5Second Stars§r: ");
        awooo.put("§5Third Master Star", "§r§5Third Stars§r: ");
        awooo.put("§5Fourth Master Star", "§r§5Fourth Stars§r: ");
        awooo.put("§5Fifth Master Star", "§r§5Fifth Stars§r: ");
        awooo.put("§9Master Skull - Tier 5", "§r§9T5 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 4", "§r§aT4 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 3", "§r§aT3 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 2", "§r§aT2 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 1", "§r§aT1 Skulls§r: ");
        awooo.put("§6Recombobulator 3000", "§r§6Recombs§r: ");
        awooo.put("§6Wither Helmet", "§r§6Wither Helmets§r: ");
        awooo.put("§6Wither Chestplate", "§r§6Wither Chestplates§r: ");
        awooo.put("§6Wither Leggings", "§r§6Wither Leggings§r: ");
        awooo.put("§6Wither Boots", "§r§6Wither Boots§r: ");
        awooo.put("§6Auto Recombobulator", "§r§6Auto Recombs§r: ");
        awooo.put("§5Wither Blood", "§r§5Wither Bloods§r: ");
        awooo.put("§5Precursor Gear", "§r§5Precursor Gears§r: ");
        awooo.put("§5Fuming Potato Book", "§r§5Fumings§r: ");
        awooo.put("Wither Essence", "§dWither Essence§r: §8");
        awooo.put("Undead Essence", "§dUndead Essence§r: §8");
    }


    // Initiates the config file or loads AwA.owowo
    public static void init() {
        awa.loadConfig();
        GoodMod.Kitten.info("Config initialized");
    }
    public static void reloadAwA() {
        awa.loadConfig();
        GoodMod.Kitten.info("AwA reload complete");
    }

    // Reads chat messages
    /*@SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        // Message parsed to variable uwa
        String uwa = event.message.getFormattedText();
        // idfk
        for (Map.Entry<String, String> entry : awooo.entrySet()) {
            if (Pattern.matches(entry.getKey() ,uwa)) {
                GoodMod.Kitten.info("Detected drop: {}", entry.getKey());
                awa.addItem(entry.getValue());
                break;
            }
        }
    }*/

    public static AwA getAwA() {
        return awa;
    }

    public static Map<String, String> getAwooo() {
        return awooo;
    }
}
