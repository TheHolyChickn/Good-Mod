package com.github.theholychickn.theholychicknaddons.owo;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedHashMap;
import java.util.Map;

// The Awoo class parses chat messages that fit a key of the hashmap awooo, and save to an AwA
public class Awoo {

    // manages the item drop cache
    public static final AwA awa = new AwA();

    // list of drops to track, key is chat message and value is message returned in /owo
    private static final Map<String, String> awooo = new LinkedHashMap<>();
    // dumping items to watch
    static {
        awooo.put("§r§5Necron's Handle§r", "§r§5Necron's Handles§r: ");
        awooo.put("§r§6Dark Claymore§r", "§r§6Dark Claymores§r: ");
        awooo.put("§r§5First Master Star§r", "§r§5First Stars§r: ");
        awooo.put("§r§5Second Master Star§r", "§r§5Second Stars§r: ");
        awooo.put("§r§5Third Master Star§r", "§r§5Third Stars§r: ");
        awooo.put("§r§5Fourth Master Star§r", "§r§5Fourth Stars§r: ");
        awooo.put("§r§5Fifth Master Star§r", "§r§5Fifth Stars§r: ");
    }

    // Initiates the config file or loads AwA.owowo
    public void init() {
        awa.loadConfig();
    }
    // Reads chat messages
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        // Message parsed to variable uwa
        String uwa = event.message.getFormattedText();
        // idfk
        for (Map.Entry<String, String> entry : awooo.entrySet()) {
            if (uwa.contains(entry.getKey())) {
                awa.addItem(entry.getValue());
                awa.saveConfig();
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
