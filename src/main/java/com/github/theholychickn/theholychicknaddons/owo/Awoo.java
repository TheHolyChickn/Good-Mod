package com.github.theholychickn.theholychicknaddons.owo;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

// The Awoo class parses chat messages that fit a key of the hashmap awooo, and save to an AwA
public class Awoo {

    // manages the item drop cache
    public static final AwA awa = new AwA();

    // list of drops to track, key is chat message and value is message returned in /owo
    private static final Map<String, String> awooo = new HashMap<>();
    // dumping items to watch
    static {
        awooo.put("§r§5Necron's Handle§r", "Necron's Handles: ");
        awooo.put("Dark Claymore", "Dark Claymores: ");
        awooo.put("First Master Star", "First Stars: ");
        awooo.put("Second Master Star", "Second Stars: ");
        awooo.put("Third Master Star", "Third Stars: ");
        awooo.put("Fourth Master Star", "Fourth Stars: ");
        awooo.put("Fifth Master Star", "Fifth Stars: ");
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
