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
        awooo.put("^(§r\\s*)§r§5Necron's Handle§r$", "§r§5Necron's Handles§r: ");
        awooo.put("^(§r\\s*)§r§5Implosion§r$", "§r§5Implosions§r: ");
        awooo.put("^(§r\\s*)§r§5Shadow Warp§r$", "§r§5Shadow Warps§r: ");
        awooo.put("^(§r\\s*)§r§5Wither Shield§r$", "§r§5Wither Shields§r: ");
        awooo.put("^(§r\\s*)§r§6Dark Claymore§r$", "§r§6Dark Claymores§r: ");
        awooo.put("^(§r\\s*)§r§6Giant's Sword§r$", "§r§6Giant's Swords§r: ");
        awooo.put("^(§r\\s*)§r§5First Master Star§r$", "§r§5First Stars§r: ");
        awooo.put("^(§r\\s*)§r§5Second Master Star§r$", "§r§5Second Stars§r: ");
        awooo.put("^(§r\\s*)§r§5Third Master Star§r$", "§r§5Third Stars§r: ");
        awooo.put("^(§r\\s*)§r§5Fourth Master Star§r$", "§r§5Fourth Stars§r: ");
        awooo.put("^(§r\\s*)§r§5Fifth Master Star§r$", "§r§5Fifth Stars§r: ");
        awooo.put("^(§r\\s*)§r§9Master Skull - Tier 5§r$", "§r§9T5 Skulls§r: ");
        awooo.put("^(§r\\s*)§r§aMaster Skull - Tier 4§r$", "§r§aT4 Skulls§r: ");
        awooo.put("^(§r\\s*)§r§aMaster Skull - Tier 3§r$", "§r§aT3 Skulls§r: ");
        awooo.put("^(§r\\s*)§r§aMaster Skull - Tier 2§r$", "§r§aT2 Skulls§r: ");
        awooo.put("^(§r\\s*)§r§aMaster Skull - Tier 1§r$", "§r§aT1 Skulls§r: ");
        awooo.put("^(§r\\s*)§r§6§lRARE REWARD! §r§6Recombobulator 3000§r$", "§r§6Recombs§r: ");
        awooo.put("^(§r\\s*)§r§6Wither Helmet§r$", "§r§6Wither Helmets§r: ");
        awooo.put("^(§r\\s*)§r§6Wither Chestplate§r$", "§r§6Wither Chestplates§r: ");
        awooo.put("^(§r\\s*)§r§6Wither Leggings§r$", "§r§6Wither Leggings§r: ");
        awooo.put("^(§r\\s*)§r§6Wither Boots§r$", "§r§6Wither Boots§r: ");
        awooo.put("^(§r\\s*)§r§6Auto Recombobulator§r$", "§r§6Auto Recombs§r: ");
        awooo.put("^(§r\\s*)§r§5Wither Blood§r$", "§r§5Wither Bloods§r: ");
        awooo.put("^(§r\\s*)§r§5Precursor Gear§r$", "§r§5Precursor Gears§r: ");
        awooo.put("Fuck Ironman players", "§r§5Tests§r: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor I§r$", "§r§cM1 §r§eRuns Tracked: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor II§r$", "§r§cM2 §r§eRuns Tracked: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor III§r$", "§r§cM3 §r§eRuns Tracked: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor IV§r$", "§r§cM4 §r§eRuns Tracked: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor V§r$", "§r§cM5 §r§eRuns Tracked: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor VI§r$", "§r§cM6 §r§eRuns Tracked: ");
        awooo.put("^(§r§f\\s*)§r§cMaster Mode The Catacombs §r§8- §r§eFloor VII§r$", "§r§cM7 §r§eRuns Tracked: ");
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
    @SubscribeEvent
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
    }

    public static AwA getAwA() {
        return awa;
    }

    public static Map<String, String> getAwooo() {
        return awooo;
    }
}
