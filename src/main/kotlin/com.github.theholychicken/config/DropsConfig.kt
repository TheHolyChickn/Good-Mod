package com.github.theholychicken.config

import com.github.theholychicken.GoodMod
import com.github.theholychicken.GoodMod.Companion.mc
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.*

class DropsConfig {
    private val configFile = File(mc.mcDataDir, "config/goodmod/drops.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            print(e.message)
        }
    }
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private var itemDrops: MutableMap<String, Int> = LinkedHashMap()

    fun loadConfig() {
        try {
            with(configFile.bufferedReader().use { it.readText() }) {
                if (this == "") return
                itemDrops = gson.fromJson(this, object : TypeToken<MutableMap<String, Int>>() {}.type)
            }

            if (itemDrops.keys.any() { it.contains("§") }) {
                migrateConfig()
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun saveConfig() {
        try {
            configFile.bufferedWriter().use {
                it.write(gson.toJson(itemDrops))
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun addItem(item: String) {
        itemDrops[item] = itemDrops.getOrDefault(item, 0) + 1
        saveConfig()
    }

    fun getItemCount(item: String): Int {
        return itemDrops.getOrDefault(item, 0)
    }

    fun addMany(item: String, count: Int) {
        itemDrops[item] = itemDrops.getOrDefault(item, 0) + count
        saveConfig()
    }

    fun generateItem(item: String) {
        itemDrops[item] = itemDrops.getOrDefault(item, 0)
        saveConfig()
    }

    fun getList(): MutableMap<String, Int> {
        return itemDrops
    }

    private fun migrateConfig() {
        GoodMod.logger.info("Migrating drops.json from old format...")
        val newItemDrops = LinkedHashMap<String, Int>()

        val migrationMap = mapOf(
            "§r§6Shiny Necron's Handles§r: " to "Shiny Necron's Handle",
            "§r§6Shiny Wither Helmets§r: " to "Shiny Wither Helmet",
            "§r§6Shiny Wither Chestplates§r: " to "Shiny Wither Chestplate",
            "§r§6Shiny Wither Leggings§r: " to "Shiny Wither Leggings",
            "§r§6Shiny Wither Boots§r: " to "Shiny Wither Boots",
            "§r§6Necron's Handles§r: " to "NECRON_HANDLE",
            "§r§5Implosions§r: " to "IMPLOSION_SCROLL",
            "§r§5Shadow Warps§r: " to "SHADOW_WARP_SCROLL",
            "§r§5Wither Shields§r: " to "WITHER_SHIELD_SCROLL",
            "§r§6Dark Claymores§r: " to "DARK_CLAYMORE",
            "§r§6Necron Dyes§r: " to "DYE_NECRON",
            "§r§6Wither Helmets§r: " to "WITHER_HELMET",
            "§r§6Wither Chestplates§r: " to "WITHER_CHESTPLATE",
            "§r§6Wither Leggings§r: " to "WITHER_LEGGINGS",
            "§r§6Wither Boots§r: " to "WITHER_BOOTS",
            "§r§6Auto Recombs§r: " to "AUTO_RECOMBOBULATOR",
            "§r§5Wither Cloaks§r: " to "WITHER_CLOAK",
            "§r§5Wither Bloods§r: " to "WITHER_BLOOD",
            "§r§5Wither Catalysts§r: " to "WITHER_CATALYST",
            "§r§5Precursor Gears§r: " to "PRECURSOR_GEAR",
            "§r§aStorm the Fish§r: " to "STORM_THE_FISH",
            "§r§aGoldor the Fish§r: " to "GOLDOR_THE_FISH",
            "§r§aMaxor the Fish§r: " to "MAXOR_THE_FISH",
            "§r§6Power Dragon Shard: " to "SHARD_POWER_DRAGON",
            "§r§6Apex Dragon Shard: " to "SHARD_APEX_DRAGON",
            "§r§9Wither Shard: " to "SHARD_WITHER",
            "§r§6Giant's Swords§r: " to "GIANTS_SWORD",
            "§r§6Precursor Eyes§r: " to "PRECURSOR_EYE",
            "§r§6Fel Skulls§r: " to "FEL_SKULL",
            "§r§5Soulweaver Gloves§r: " to "SOULWEAVER_GLOVES",
            "§r§5Giant Teeth§r: " to "GIANT_TOOTH",
            "§r§9Summoning Rings§r: " to "SUMMONING_RING",
            "§r§6Necromancer Lord Helmets§r: " to "NECROMANCER_LORD_HELMET",
            "§r§6Necromancer Lord Chestplates§r: " to "NECROMANCER_LORD_CHESTPLATE",
            "§r§6Necromancer Lord Leggings§r: " to "NECROMANCER_LORD_LEGGINGS",
            "§r§6Necromancer Lord Boots§r: " to "NECROMANCER_LORD_BOOTS",
            "§r§6Necromancer Sword§r: " to "NECROMANCER_SWORD",
            "§r§6Sadan's Brooch§r: " to "SADAN_BROOCH",
            "§r§6Shadow Furies§r: " to "SHADOW_FURY",
            "§r§6Last Breaths§r: " to "LAST_BREATH",
            "§r§6Livid Dagger§r: " to "LIVID_DAGGER",
            "§r§5Shadow Assassin Helmets§r: " to "SHADOW_ASSASSIN_HELMET",
            "§r§5Shadow Assassin Chestplates§r: " to "SHADOW_ASSASSIN_CHESTPLATE",
            "§r§5Shadow Assassin Leggings§r: " to "SHADOW_ASSASSIN_LEGGINGS",
            "§r§5Shadow Assassin Boots§r: " to "SHADOW_ASSASSIN_BOOTS",
            "§r§5Shadow Assassin Cloaks§r: " to "SHADOW_ASSASSIN_CLOAK",
            "§r§9Warped Stones§r: " to "AOTE_STONE",
            "§r§9Dark Orbs§r: " to "DARK_ORB",
            "§r§6Spirit Shortbows§r: " to "ITEM_SPIRIT_BOW",
            "§r§5Spirit Wings§r: " to "SPIRIT_WING",
            "§r§5Spirit Bones§r: " to "SPIRIT_BONE",
            "§r§5Legendary Spirit Pets§r: " to "PET_SPIRIT_LEGENDARY",
            "§r§6Epic Spirit Pets§r: " to "PET_SPIRIT_EPIC",
            "§r§6Spirit Swords§r: " to "SPIRIT_SWORD",
            "§r§6Spirit Boots§r: " to "THORNS_BOOTS",
            "§r§9Spirit Stones§r: " to "SPIRIT_DECOY",
            "§r§6Thorn Shard: " to "SHARD_THORN",
            "§r§5Adaptive Helmets§r: " to "ADAPTIVE_HELMET",
            "§r§5Adaptive Chestplates§r: " to "ADAPTIVE_CHESTPLATE",
            "§r§5Adaptive Leggings§r: " to "ADAPTIVE_LEGGINGS",
            "§r§5Adaptive Boots§r: " to "ADAPTIVE_BOOTS",
            "§r§9Suspicious Vials§r: " to "SUSPICIOUS_VIAL",
            "§r§9Red Scarfs§r: " to "RED_SCARF",
            "§r§5Adaptive Blades§r: " to "STONE_BLADE",
            "§r§5Adaptive Belts§r: " to "ADAPTIVE_BELT",
            "§r§9Scarf's Studies§r: " to "SCARF_STUDIES",
            "§r§6Scarf Shard: " to "SHARD_SCARF",
            "§r§9Bonzo's Staffs§r: " to "BONZO_STAFF",
            "§r§9Bonzo's Masks§r: " to "BONZO_MASK",
            "§r§9Balloon Snakes§r: " to "BALLOON_SNAKE",
            "§r§9Red Noses§r: " to "RED_NOSE",
            "§r§5Fifth Stars§r: " to "FIFTH_MASTER_STAR",
            "§r§5Fourth Stars§r: " to "FOURTH_MASTER_STAR",
            "§r§5Third Stars§r: " to "THIRD_MASTER_STAR",
            "§r§5Second Stars§r: " to "SECOND_MASTER_STAR",
            "§r§5First Stars§r: " to "FIRST_MASTER_STAR",
            "§r§9T5 Skulls§r: " to "MASTER_SKULL_TIER_5",
            "§r§aT4 Skulls§r: " to "MASTER_SKULL_TIER_4",
            "§r§aT3 Skulls§r: " to "MASTER_SKULL_TIER_3",
            "§r§aT2 Skulls§r: " to "MASTER_SKULL_TIER_2",
            "§r§aT1 Skulls§r: " to "MASTER_SKULL_TIER_1",
            "§r§5Thunderlord VII§r: " to "ENCHANTMENT_THUNDERLORD_7",
            "§r§9Overload I§r: " to "ENCHANTMENT_OVERLOAD_1",
            "§r§9Rejuvenate III§r: " to "ENCHANTMENT_REJUVENATE_3",
            "§r§9Rejuvenate II§r: " to "ENCHANTMENT_REJUVENATE_2",
            "§r§9Rejuvenate I§r: " to "ENCHANTMENT_REJUVENATE_1",
            "§r§9Feather Falling VII§r: " to "ENCHANTMENT_FEATHER_FALLING_7",
            "§r§9Feather Falling VI§r: " to "ENCHANTMENT_FEATHER_FALLING_6",
            "§r§9Infinite Quiver VII§r: " to "ENCHANTMENT_INFINITE_QUIVER_7",
            "§r§9Infinite Quiver VI§r: " to "ENCHANTMENT_INFINITE_QUIVER_6",
            "§r§9Lethality VI§r: " to "ENCHANTMENT_LETHALITY_6",
            "§r§d§lOne For All I§r: " to "ENCHANTMENT_ULTIMATE_ONE_FOR_ALL_1",
            "§r§d§lSoul Eater I§r: " to "ENCHANTMENT_ULTIMATE_SOUL_EATER_1",
            "§r§d§lSwarm I§r: " to "ENCHANTMENT_ULTIMATE_SWARM_1",
            "§r§d§lRend II§r: " to "ENCHANTMENT_ULTIMATE_REND_2",
            "§r§d§lRend I§r: " to "ENCHANTMENT_ULTIMATE_REND_1",
            "§r§d§lLegion I§r: " to "ENCHANTMENT_ULTIMATE_LEGION_1",
            "§r§d§lLast Stand II§r: " to "ENCHANTMENT_ULTIMATE_LAST_STAND_2",
            "§r§d§lLast Stand I§r: " to "ENCHANTMENT_ULTIMATE_LAST_STAND_1",
            "§r§d§lUltimate Wise II§r: " to "ENCHANTMENT_ULTIMATE_WISE_2",
            "§r§d§lUltimate Wise I§r: " to "ENCHANTMENT_ULTIMATE_WISE_1",
            "§r§d§lWisdom II§r: " to "ENCHANTMENT_ULTIMATE_WISDOM_2",
            "§r§d§lWisdom I§r: " to "ENCHANTMENT_ULTIMATE_WISDOM_1",
            "§r§d§lBank III§r: " to "ENCHANTMENT_ULTIMATE_BANK_3",
            "§r§d§lBank II§r: " to "ENCHANTMENT_ULTIMATE_BANK_2",
            "§r§d§lBank I§r: " to "ENCHANTMENT_ULTIMATE_BANK_1",
            "§r§d§lNo Pain No Gain II§r: " to "ENCHANTMENT_ULTIMATE_NO_PAIN_NO_GAIN_2",
            "§r§d§lNo Pain No Gain I§r: " to "ENCHANTMENT_ULTIMATE_NO_PAIN_NO_GAIN_1",
            "§r§d§lCombo II§r: " to "ENCHANTMENT_ULTIMATE_COMBO_2",
            "§r§d§lCombo I§r: " to "ENCHANTMENT_ULTIMATE_COMBO_1",
            "§r§d§lUltimate Jerry III§r: " to "ENCHANTMENT_ULTIMATE_JERRY_3",
            "§r§d§lUltimate Jerry II§r: " to "ENCHANTMENT_ULTIMATE_JERRY_2",
            "§r§d§lUltimate Jerry I§r: " to "ENCHANTMENT_ULTIMATE_JERRY_1",
            "§r§6Recombs§r: " to "RECOMBOBULATOR_3000",
            "§r§5Fumings§r: " to "FUMING_POTATO_BOOK",
            "§r§5HPBs§r: " to "HOT_POTATO_BOOK",
            "§r§9Necromancer's Brooches§r: " to "NECROMANCER_BROOCH",
            "§dWither Essence§r: §8" to "ESSENCE_WITHER",
            "§dUndead Essence§r: §8" to "ESSENCE_UNDEAD"
        )

        itemDrops.forEach { (oldKey, count) ->
            if (migrationMap.containsKey(oldKey)) {
                val newKey = migrationMap[oldKey]!!
                newItemDrops[newKey] = newItemDrops.getOrDefault(newKey, 0) + count
            } else {
                newItemDrops[oldKey] = count
            }
        }

        itemDrops = newItemDrops
        saveConfig()
        GoodMod.logger.info("Migration complete.")
    }
}