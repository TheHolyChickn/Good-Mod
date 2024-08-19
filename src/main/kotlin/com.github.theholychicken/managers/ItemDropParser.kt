package com.github.theholychicken.managers

import com.github.theholychicken.config.DropsConfig

// The ItemDropParser class processes chat messages to detect item drops and store them in a DropsConfig object
object ItemDropParser {
    // Configuration object for storing drops
    var dropsConfig: DropsConfig = DropsConfig()

    // Map of chat messages to their corresponding item descriptions
    val itemDropPatterns: MutableMap<String, String> = LinkedHashMap()

    // Initialize the item drop patterns
    init {
        // Floor 7 items
        itemDropPatterns["§5Necron's Handle"] = "§r§5Necron's Handles§r: "
        itemDropPatterns["§5Implosion"] = "§r§5Implosions§r: "
        itemDropPatterns["§5Shadow Warp"] = "§r§5Shadow Warps§r: "
        itemDropPatterns["§5Wither Shield"] = "§r§5Wither Shields§r: "
        itemDropPatterns["§6Dark Claymore"] = "§r§6Dark Claymores§r: "
        itemDropPatterns["§6Necron Dye"] = "§r§6Necron Dyes§r: "
        itemDropPatterns["§6Wither Helmet"] = "§r§6Wither Helmets§r: "
        itemDropPatterns["§6Wither Chestplate"] = "§r§6Wither Chestplates§r: "
        itemDropPatterns["§6Wither Leggings"] = "§r§6Wither Leggings§r: "
        itemDropPatterns["§6Wither Boots"] = "§r§6Wither Boots§r: "
        itemDropPatterns["§6Auto Recombobulator"] = "§r§6Auto Recombs§r: "
        itemDropPatterns["§5Wither Blood"] = "§r§5Wither Bloods§r: "
        itemDropPatterns["§9Wither Catalyst"] = "§r§5Wither Catalysts§r: "
        itemDropPatterns["§5Precursor Gear"] = "§r§5Precursor Gears§r: "
        itemDropPatterns["§aStorm The Fish"] = "§r§aStorm the Fish§r: "
        itemDropPatterns["§aGoldor The Fish"] = "§r§aGoldor the Fish§r: "
        itemDropPatterns["§aMaxor The Fish"] = "§r§aMaxor the Fish§r: "
        // Floor 6 items
        itemDropPatterns["§6Giant's Sword"] = "§r§6Giant's Swords§r: "
        itemDropPatterns["§6Precursor Eye"] = "§r§6Precursor Eyes§r: "
        itemDropPatterns["§6Fel Skull"] = "§r§6Fel Skulls§r: "
        itemDropPatterns["§5Soulweaver Gloves"] = "§r§5Soulweaver Gloves§r: "
        itemDropPatterns["§5Giant Tooth"] = "§r§5Giant Teeth§r: "
        itemDropPatterns["§9Summoning Ring"] = "§r§9Summoning Rings§r: "
        itemDropPatterns["§6Necromancer Lord Helmet"] = "§r§6Necromancer Lord Helmets§r: "
        itemDropPatterns["§6Necromancer Lord Chestplate"] = "§r§6Necromancer Lord Chestplates§r: "
        itemDropPatterns["§6Necromancer Lord Leggings"] = "§r§6Necromancer Lord Leggings§r: "
        itemDropPatterns["§6Necromancer Lord Boots"] = "§r§6Necromancer Lord Boots§r: "
        itemDropPatterns["§6Necromancer Sword"] = "§r§6Necromancer Sword§r: "
        itemDropPatterns["§5Sadan's Brooch"] = "§r§6Sadan's Brooch§r: "
        // Floor 5 items
        itemDropPatterns["§6Shadow Fury"] = "§r§6Shadow Furies§r: "
        itemDropPatterns["§6Last Breath"] = "§r§6Last Breaths§r: "
        itemDropPatterns["§6Livid Dagger"] = "§r§6Livid Dagger§r: "
        itemDropPatterns["§5Shadow Assassin Helmet"] = "§r§5Shadow Assassin Helmets§r: "
        itemDropPatterns["§5Shadow Assassin Chestplate"] = "§r§5Shadow Assassin Chestplates§r: "
        itemDropPatterns["§5Shadow Assassin Leggings"] = "§r§5Shadow Assassin Leggings§r: "
        itemDropPatterns["§5Shadow Assassin Boots"] = "§r§5Shadow Assassin Boots§r: "
        itemDropPatterns["§5Shadow Assassin Cloak"] = "§r§5Shadow Assassin Cloaks§r: "
        itemDropPatterns["§9Warped Stone"] = "§r§9Warped Stones§r: "
        itemDropPatterns["§9Dark Orb"] = "§r§9Dark Orbs§r: "
        // Floor 4 items
        itemDropPatterns["§6Spirit Shortbow"] = "§r§6Spirit Shortbows§r: "
        itemDropPatterns["§5Spirit Wing"] = "§r§5Spirit Wings§r: "
        itemDropPatterns["§5Spirit Bone"] = "§r§5Spirit Bones§r: "
        itemDropPatterns["§7[Lvl 1] §6Spirit"] = "§r§5Legendary Spirit Pets§r: "
        itemDropPatterns["§7[Lvl 1] §5Spirit"] = "§r§6Epic Spirit Pets§r: "
        itemDropPatterns["§6Spirit Sword"] = "§r§6Spirit Swords§r: "
        itemDropPatterns["§6Spirit Boots"] = "§r§6Spirit Boots§r: "
        itemDropPatterns["§9Spirit Stone"] = "§r§9Spirit Stones§r: "
        // Floor 3 items
        itemDropPatterns["§5Adaptive Helmet"] = "§r§5Adaptive Helmets§r: "
        itemDropPatterns["§5Adaptive Chestplate"] = "§r§5Adaptive Chestplates§r: "
        itemDropPatterns["§5Adaptive Leggings"] = "§r§5Adaptive Leggings§r: "
        itemDropPatterns["§5Adaptive Boots"] = "§r§5Adaptive Boots§r: "
        itemDropPatterns["§9Suspicious Vial"] = "§r§9Suspicious Vials§r: "
        // Floor 2 items
        itemDropPatterns["§9Red Scarf"] = "§r§9Red Scarfs§r: "
        itemDropPatterns["§5Adaptive Blade"] = "§r§5Adaptive Blades§r: "
        itemDropPatterns["§5Adaptive Belt"] = "§r§5Adaptive Belts§r: "
        itemDropPatterns["§9Scarf's Studies"] = "§r§9Scarf's Studies§r: "
        // Floor 1 items
        itemDropPatterns["§9Bonzo's Staff"] = "§r§9Bonzo's Staffs§r: "
        itemDropPatterns["§9Bonzo's Mask"] = "§r§9Bonzo's Masks§r: "
        itemDropPatterns["§9Balloon Snake"] = "§r§9Balloon Snakes§r: "
        itemDropPatterns["§9Red Nose"] = "§r§9Red Noses§r: "
        // Stars
        itemDropPatterns["§5Fifth Master Star"] = "§r§5Fifth Stars§r: "
        itemDropPatterns["§5Fourth Master Star"] = "§r§5Fourth Stars§r: "
        itemDropPatterns["§5Third Master Star"] = "§r§5Third Stars§r: "
        itemDropPatterns["§5Second Master Star"] = "§r§5Second Stars§r: "
        itemDropPatterns["§5First Master Star"] = "§r§5First Stars§r: "
        // Skulls
        itemDropPatterns["§9Master Skull - Tier 5"] = "§r§9T5 Skulls§r: "
        itemDropPatterns["§aMaster Skull - Tier 4"] = "§r§aT4 Skulls§r: "
        itemDropPatterns["§aMaster Skull - Tier 3"] = "§r§aT3 Skulls§r: "
        itemDropPatterns["§aMaster Skull - Tier 2"] = "§r§aT2 Skulls§r: "
        itemDropPatterns["§aMaster Skull - Tier 1"] = "§r§aT1 Skulls§r: "
        // Regular Enchants
        itemDropPatterns["§9Thunderlord VII"] = "§r§5Thunderlord VII§r: "
        itemDropPatterns["§9Overload I"] = "§r§9Overload I§r: "
        itemDropPatterns["§9Rejuvenate III"] = "§r§9Rejuvenate III§r: "
        itemDropPatterns["§9Rejuvenate II"] = "§r§9Rejuvenate II§r: "
        itemDropPatterns["§9Rejuvenate I"] = "§r§9Rejuvenate I§r: "

    } // CONTINUE ADDING STUFF I CBA RN

    // Method to parse chat messages and update the drops configuration
    fun parseChatMessage(message: String) {
        itemDropPatterns.forEach { (itemPattern, itemDescription) ->
            if (message.contains(itemPattern)) {
                dropsConfig.addItem(itemDescription + message)
            }
        }
    }

    fun reloadConfig() {
        dropsConfig.loadConfig()
    }

    fun initConfig() {
        println("Empty config registered, initiating config")
        itemDropPatterns.values.forEach { item ->
            dropsConfig.generateItem(item)
            println("Initiated $item")
        }
        println("Config initiated, reloading now")
        dropsConfig.loadConfig()
    }
}
