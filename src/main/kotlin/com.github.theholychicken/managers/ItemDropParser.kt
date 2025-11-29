package com.github.theholychicken.managers

import com.github.theholychicken.config.DropsConfig

/**
 * The [ItemDropParser] class processes chat messages to detect item drops and store them in a DropsConfig object
 */
object ItemDropParser {
    // Configuration object for storing drops
    var dropsConfig: DropsConfig = DropsConfig()
    // Map of chat messages to their corresponding item descriptions
    val itemDropPatterns: MutableMap<String, String> = LinkedHashMap()

    // Initialize the item drop patterns
    init {
        // Floor 7 items
        itemDropPatterns["§6Shiny Necron's Handle"] = "§r§6Shiny Necron's Handles§r: "
        itemDropPatterns["§6Shiny Wither Helmet"] = "§r§6Shiny Wither Helmets§r: "
        itemDropPatterns["§6Shiny Wither Chestplate"] = "§r§6Shiny Wither Chestplates§r: "
        itemDropPatterns["§6Shiny Wither Leggings"] = "§r§6Shiny Wither Leggings§r: "
        itemDropPatterns["§6Shiny Wither Boots"] = "§r§6Shiny Wither Boots§r: "
        itemDropPatterns["§6Necron's Handle"] = "§r§6Necron's Handles§r: "
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
        itemDropPatterns["§5Wither Cloak Sword"] = "§r§5Wither Cloaks§r: "
        itemDropPatterns["§5Wither Blood"] = "§r§5Wither Bloods§r: "
        itemDropPatterns["§9Wither Catalyst"] = "§r§5Wither Catalysts§r: "
        itemDropPatterns["§5Precursor Gear"] = "§r§5Precursor Gears§r: "
        itemDropPatterns["§cStorm the Fish"] = "§r§aStorm the Fish§r: "
        itemDropPatterns["§cGoldor the Fish"] = "§r§aGoldor the Fish§r: "
        itemDropPatterns["§cMaxor the Fish"] = "§r§aMaxor the Fish§r: "
        itemDropPatterns["§6Power Dragon Shard"] = "§r§6Power Dragon Shard: "
        itemDropPatterns["§6Apex Dragon Shard"] = "§r§6Apex Dragon Shard: "
        itemDropPatterns["§9Wither Shard"] = "§r§9Wither Shard: "
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
        itemDropPatterns["§6Thorn Shard"] = "§r§6Thorn Shard: "
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
        itemDropPatterns["§6Scarf Shard"] = "§r§6Scarf Shard: "
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
        itemDropPatterns["§9Feather Falling VII"] = "§r§9Feather Falling VII§r: "
        itemDropPatterns["§9Feather Falling VI"] = "§r§9Feather Falling VI§r: "
        itemDropPatterns["§9Infinite Quiver VII"] = "§r§9Infinite Quiver VII§r: "
        itemDropPatterns["§9Infinite Quiver VI"] = "§r§9Infinite Quiver VI§r: "
        itemDropPatterns["§9Lethality VI"] = "§r§9Lethality VI§r: "
        // Ultimate Enchants
        itemDropPatterns["§9§d§lOne For All I"] = "§r§d§lOne For All I§r: "
        itemDropPatterns["§9§d§lSoul Eater I"] = "§r§d§lSoul Eater I§r: "
        itemDropPatterns["§9§d§lSwarm I"] = "§r§d§lSwarm I§r: "
        itemDropPatterns["§9§d§lRend II"] = "§r§d§lRend II§r: "
        itemDropPatterns["§9§d§lRend I"] = "§r§d§lRend I§r: "
        itemDropPatterns["§9§d§lLegion I"] = "§r§d§lLegion I§r: "
        itemDropPatterns["§9§d§lLast Stand II"] = "§r§d§lLast Stand II§r: "
        itemDropPatterns["§9§d§lLast Stand I"] = "§r§d§lLast Stand I§r: "
        itemDropPatterns["§9§d§lUltimate Wise II"] = "§r§d§lUltimate Wise II§r: "
        itemDropPatterns["§9§d§lUltimate Wise I"] = "§r§d§lUltimate Wise I§r: "
        itemDropPatterns["§9§d§lWisdom II"] = "§r§d§lWisdom II§r: "
        itemDropPatterns["§9§d§lWisdom I"] = "§r§d§lWisdom I§r: "
        itemDropPatterns["§9§d§lBank III"] = "§r§d§lBank III§r: "
        itemDropPatterns["§9§d§lBank II"] = "§r§d§lBank II§r: "
        itemDropPatterns["§9§d§lBank I"] = "§r§d§lBank I§r: "
        itemDropPatterns["§9§d§lNo Pain No Gain II"] = "§r§d§lNo Pain No Gain II§r: "
        itemDropPatterns["§9§d§lNo Pain No Gain I"] = "§r§d§lNo Pain No Gain I§r: "
        itemDropPatterns["§9§d§lCombo II"] = "§r§d§lCombo II§r: "
        itemDropPatterns["§9§d§lCombo I"] = "§r§d§lCombo I§r: "
        itemDropPatterns["§9§d§lUltimate Jerry III"] = "§r§d§lUltimate Jerry III§r: "
        itemDropPatterns["§9§d§lUltimate Jerry II"] = "§r§d§lUltimate Jerry II§r: "
        itemDropPatterns["§9§d§lUltimate Jerry I"] = "§r§d§lUltimate Jerry I§r: "
        // Universal Items
        itemDropPatterns["§6Recombobulator 3000"] = "§r§6Recombs§r: "
        itemDropPatterns["§5Fuming Potato Book"] = "§r§5Fumings§r: "
        itemDropPatterns["§5Hot Potato Book"] = "§r§5HPBs§r: "
        itemDropPatterns["§9Necromancer's Brooch"] = "§r§9Necromancer's Brooches§r: "
        // Essence
        itemDropPatterns["Wither Essence"] = "§dWither Essence§r: §8"
        itemDropPatterns["Undead Essence"] = "§dUndead Essence§r: §8"
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

    // is thsis useful?
    // idk it's not used anywhere
    fun getModifiedKeys(): List<String> {
         return itemDropPatterns.keys.map { key -> key.substring(2) }
    }
}
