package com.github.theholychickn.theholychicknaddons.owo;

import java.util.LinkedHashMap;
import java.util.Map;

// The Awoo class parses chat messages that fit a key of the hashmap awooo, and save to an AwA
public class Awoo {

    // manages the item drop cache
    public static AwA awa = new AwA();

    // list of drops to track, key is chat message and value is message returned in /owo
    private static final Map<String, String> awooo = new LinkedHashMap<>();
    // dumping items to watch
    static {
        // Floor 7
        awooo.put("§5Necron's Handle", "§r§5Necron's Handles§r: ");
        awooo.put("§5Implosion", "§r§5Implosions§r: ");
        awooo.put("§5Shadow Warp", "§r§5Shadow Warps§r: ");
        awooo.put("§5Wither Shield", "§r§5Wither Shields§r: ");
        awooo.put("§6Dark Claymore", "§r§6Dark Claymores§r: ");
        awooo.put("§6Necron Dye", "§r§6Necron Dyes§r: ");
        awooo.put("§6Wither Helmet", "§r§6Wither Helmets§r: ");
        awooo.put("§6Wither Chestplate", "§r§6Wither Chestplates§r: ");
        awooo.put("§6Wither Leggings", "§r§6Wither Leggings§r: ");
        awooo.put("§6Wither Boots", "§r§6Wither Boots§r: ");
        awooo.put("§6Auto Recombobulator", "§r§6Auto Recombs§r: ");
        awooo.put("§5Wither Blood", "§r§5Wither Bloods§r: ");
        awooo.put("§9Wither Catalyst", "§r§5Wither Catalysts§r: ");
        awooo.put("§5Precursor Gear", "§r§5Precursor Gears§r: ");
        awooo.put("§aStorm The Fish", "§r§aStorm the Fish§r: ");
        awooo.put("§aGoldor The Fish", "§r§aGoldor the Fish§r: ");
        awooo.put("§aMaxor The Fish", "§r§aMaxor the Fish§r: ");
        // Floor 6
        awooo.put("§6Giant's Sword", "§r§6Giant's Swords§r: ");
        awooo.put("§6Precursor Eye", "§r§6Precursor Eyes§r: ");
        awooo.put("§6Fel Skull", "§r§6Fel Skulls§r: ");
        awooo.put("§5Soulweaver Gloves", "§r§5Soulweaver Gloves§r: ");
        awooo.put("§5Giant Tooth", "§r§5Giant Teeth§r: ");
        awooo.put("§9Summoning Ring", "§r§9Summoning Rings§r: ");
        awooo.put("§6Necromancer Lord Helmet", "§r§6Necromancer Lord Helmets§r: ");
        awooo.put("§6Necromancer Lord Chestplate", "§r§6Necromancer Lord Chestplates§r: ");
        awooo.put("§6Necromancer Lord Leggings", "§r§6Necromancer Lord Leggings§r: ");
        awooo.put("§6Necromancer Lord Boots", "§r§6Necromancer Lord Boots§r: ");
        awooo.put("§6Necromancer Sword", "§r§6Necromancer Sword§r: ");
        awooo.put("§5Sadan's Brooch", "§r§6Sadan's Brooch§r: ");
        // Floor 5
        awooo.put("§6Shadow Fury", "§r§6Shadow Furies§r: ");
        awooo.put("§6Last Breath", "§r§6Last Breaths§r: ");
        awooo.put("§6Livid Dagger", "§r§6Livid Dagger§r: ");
        awooo.put("§5Shadow Assassin Helmet", "§r§5Shadow Assassin Helmets§r: ");
        awooo.put("§5Shadow Assassin Chestplate", "§r§5Shadow Assassin Chestplates§r: ");
        awooo.put("§5Shadow Assassin Leggings", "§r§5Shadow Assassin Leggings§r: ");
        awooo.put("§5Shadow Assassin Boots", "§r§5Shadow Assassin Boots§r: ");
        awooo.put("§5Shadow Assassin Cloak", "§r§5Shadow Assassin Cloaks§r: ");
        awooo.put("§9Warped Stone", "§r§9Warped Stones§r: ");
        awooo.put("§9Dark Orb", "§r§9Dark Orbs§r: ");
        // Floor 4
        awooo.put("§6Spirit Shortbow", "§r§6Spirit Shortbows§r: ");
        awooo.put("§5Spirit Wing", "§r§5Spirit Wings§r: ");
        awooo.put("§5Spirit Bone", "§r§5Spirit Bones§r: ");
        awooo.put("§7[Lvl 1] §6Spirit", "§r§5Legendary Spirit Pets§r: ");
        awooo.put("§7[Lvl 1] §5Spirit", "§r§6Epic Spirit Pets§r: ");
        awooo.put("§6Spirit Sword", "§r§6Spirit Swords§r: ");
        awooo.put("§6Spirit Boots", "§r§6Spirit Boots§r: ");
        awooo.put("§9Spirit Stone", "§r§9Spirit Stones§r: ");
        // Floor 3
        awooo.put("§5Adaptive Helmet", "§r§5Adaptive Helmets§r: ");
        awooo.put("§5Adaptive Chestplate", "§r§5Adaptive Chestplates§r: ");
        awooo.put("§5Adaptive Leggings", "§r§5Adaptive Leggings§r: ");
        awooo.put("§5Adaptive Boots", "§r§5Adaptive Boots§r: ");
        awooo.put("§9Suspicious Vial", "§r§9Suspicious Vials§r: ");
        // Floor 2
        awooo.put("§9Red Scarf", "§r§9Red Scarfs§r: ");
        awooo.put("§5Adaptive Blade", "§r§5Adaptive Blades§r: ");
        awooo.put("§5Adaptive Belt", "§r§5Adaptive Belts§r: ");
        awooo.put("§9Scarf's Studies", "§r§9Scarf's Studies§r: ");
        // Floor 1
        awooo.put("§9Bonzo's Staff", "§r§9Bonzo's Staffs§r: ");
        awooo.put("§9Bonzo's Mask", "§r§9Bonzo's Masks§r: ");
        awooo.put("§9Balloon Snake", "§r§9Balloon Snakes§r: ");
        awooo.put("§9Red Nose", "§r§9Red Noses§r: ");
        // Stars
        awooo.put("§5Fifth Master Star", "§r§5Fifth Stars§r: ");
        awooo.put("§5Fourth Master Star", "§r§5First Stars§r: ");
        awooo.put("§5Third Master Star", "§r§5Second Stars§r: ");
        awooo.put("§5Second Master Star", "§r§5Third Stars§r: ");
        awooo.put("§5First Master Star", "§r§5Fourth Stars§r: ");
        // Skulls
        awooo.put("§9Master Skull - Tier 5", "§r§9T5 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 4", "§r§aT4 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 3", "§r§aT3 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 2", "§r§aT2 Skulls§r: ");
        awooo.put("§aMaster Skull - Tier 1", "§r§aT1 Skulls§r: ");
        // Regular Enchants
        awooo.put("§9Thunderlord VII", "§r§5Thunderlord VII§r: ");
        awooo.put("§9Overload I", "§r§9Overload I§r: ");
        awooo.put("§9Rejuvenate III", "§r§9Rejuvenate III§r: ");
        awooo.put("§9Rejuvenate II", "§r§9Rejuvenate II§r: ");
        awooo.put("§9Rejuvenate I", "§r§9Rejuvenate I§r: ");
        awooo.put("§9Feather Falling VII", "§r§9Feather Falling VII§r: ");
        awooo.put("§9Feather Falling VI", "§r§9Feather Falling VI§r: ");
        awooo.put("§9Overload VII", "§r§9Infinite Quiver VII§r: ");
        awooo.put("§9Overload VI", "§r§9Infinite Quiver VI§r: ");
        awooo.put("§9Lethality VI", "§r§9Lethality VI§r: ");
        // Ultimate Enchants
        awooo.put("§9§d§lOne For All I", "§r§d§lOne For All I§r: ");
        awooo.put("§9§d§lSoul Eater I", "§r§d§lSoul Eater I§r: ");
        awooo.put("§9§d§lSwarm I", "§r§d§lSwarm I§r: ");
        awooo.put("§9§d§lRend II", "§r§d§lRend II§r: ");
        awooo.put("§9§d§lLegion I", "§r§d§lLegion I§r: ");
        awooo.put("§9§d§lLast Stand II", "§r§d§lLast Stand II§r: ");
        awooo.put("§9§d§lLast Stand I", "§r§d§lLast Stand I§r: ");
        awooo.put("§9§d§lUltimate Wise II", "§r§d§lUltimate Wise II§r: ");
        awooo.put("§9§d§lUltimate Wise I", "§r§d§lUltimate Wise I§r: ");
        awooo.put("§9§d§lWisdom II", "§r§d§lWisdom II§r: ");
        awooo.put("§9§d§lWisdom I", "§r§d§lWisdom I§r: ");
        awooo.put("§9§d§lBank III", "§r§d§lBank III§r: ");
        awooo.put("§9§d§lBank II", "§r§d§lBank II§r: ");
        awooo.put("§9§d§lBank I", "§r§d§lBank I§r: ");
        awooo.put("§9§d§lNo Pain No Gain II", "§r§d§lNo Pain No Gain II§r: ");
        awooo.put("§9§d§lNo Pain No Gain I", "§r§d§lNo Pain No Gain I§r: ");
        awooo.put("§9§d§lCombo II", "§r§d§lCombo II§r: ");
        awooo.put("§9§d§lCombo I", "§r§d§lCombo I§r: ");
        awooo.put("§9§d§lUltimate Jerry III", "§r§d§lUltimate Jerry III§r: ");
        awooo.put("§9§d§lUltimate Jerry II", "§r§d§lUltimate Jerry II§r: ");
        awooo.put("§9§d§lUltimate Jerry I", "§r§d§lUltimate Jerry I§r: ");
        // Universal Items
        awooo.put("§6Recombobulator 3000", "§r§6Recombs§r: ");
        awooo.put("§5Fuming Potato Book", "§r§5Fumings§r: ");
        awooo.put("§5Hot Potato Book", "§r§5HPBs§r: ");
        awooo.put("§9Necromancer's Brooch", "§r§9Necromancer's Brooches§r: ");
        // Essence
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
