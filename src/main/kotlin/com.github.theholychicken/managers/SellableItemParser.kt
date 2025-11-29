package com.github.theholychicken.managers

import com.github.theholychicken.GoodMod
import java.io.File
import com.github.theholychicken.GoodMod.Companion.mc
import com.github.theholychicken.config.SellPricesConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Object which contains parsed information about api pulls from sellable items.
 * Should only be used for Hypixel API pulls.
 *
 * @property auctionPrices contains current lowest ah prices, indexed by their name in itemDropPatterns minus the formatting stuff
 * @property items contains the list of unformatted items in itemDropPatterns
 * @property initFromFile pulls data from the saved file
 */
object SellableItemParser {
    var auctionPrices: MutableMap<String, Double> = mutableMapOf()
    //private val items = ItemDropParser.getModifiedKeys()
    val items: List<SellableItem> = SellableItem.entries
    val shinyItems: MutableList<String> = mutableListOf(
        "Shiny Necron's Handle",
        "Shiny Wither Helmet",
        "Shiny Wither Chestplate",
        "Shiny Wither Leggings",
        "Shiny Wither Boots",
    )
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    // pretty sure this stuff is saved in memory anyways i should prolly delete this crap
    // rawr :3
    private val PRICES_FILE = File(mc.mcDataDir, "config/goodmod/prices.json").apply {
        try {
            createNewFile()
        } catch (e: Exception) {
            print(e.message)
        }
    }

    // updates an entry as an auction thingy
    // TODO: make it account for listing fees and taxes
    fun updateAuction(item: String, price: Double, rarity: String = "") {
        if (!(isAuctionable(item) || item in shinyItems)) return

        // Have to check for spirit pet rarity
        var itemName = item
        if (rarity != "") {
            if (rarity == "EPIC") itemName = "[Lvl 1] §6Spirit"
            else if (rarity == "LEGENDARY") itemName = "[Lvl 1] §5Spirit"
        }

        auctionPrices[itemName] = auctionPriceLogic(price)
    }

    /**
     * Updates the [item]'s bazaar entry with its new [price].
     */
    fun updateBazaar(item: String, price: Double) {
        val itemName = getBazaarName(item) ?: return
        val multi = if (SellPricesConfig.sellPrices[item] == true) 0.9875F else 1F // tax
        auctionPrices[itemName] = (price * multi)
    }

    /**
     * Saves data to the file.
     */
    fun saveToFile() {
        try {
            PRICES_FILE.writeText(gson.toJson(auctionPrices))
            GoodMod.logger.info("Auction prices successfully saved")
        } catch (e: Exception) {
            GoodMod.logger.info("Error loading auction prices: ${e.message}")
        }
    }

    /**
     * Pulls data from the file.
     */
    fun initFromFile() {
        try {
            with(PRICES_FILE.bufferedReader().use { it.readText() }) {
                if (this == "") return

                auctionPrices = gson.fromJson(
                    this,
                    object : TypeToken<MutableMap<String, Double>>() {}.type
                )
            }
        } catch (e: Exception) {
            print(e.message)
        }
    }

    /**
     * Returns the ID of the provided [itemName]. Returns `null` if the ID could not be found.
     */
    fun getItemID(itemName: String): String? = SellableItem.toKey(itemName)

    /**
     * Checks if this [item] is auctionable. Input is using the item's name.
     */
    private fun isAuctionable(item: String): Boolean {
        return SellableItem.toItem(item)?.sellType == SellableItem.SellType.AUCTION
    }

    /**
     * Checks if this [itemID] can be sold on the bazaar.
     *
     * @return The display name of the item if it can be sold, or `null` if not.
     */
    private fun getBazaarName(itemID: String): String? {
        return try {
            val item = SellableItem.valueOf(itemID)
            if (item.sellType == SellableItem.SellType.BAZAAR) item.displayName else null
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private fun auctionPriceLogic(price: Double): Double {
        // Claim tax
        var modifiedPrice: Double = price - (price * 0.01)

        // Listing tax
        when {
            price <= 9_999_999.0 -> modifiedPrice -= (price * 0.01)
            price <= 99_999_999.0 -> modifiedPrice -= (price * 0.02)
            price >= 100_000_000 -> modifiedPrice -= (price * 0.025)
        }

        return modifiedPrice
    }

    /**
     * Inner enum class to hold all possible sellable items.
     */
    enum class SellableItem(val displayName: String, val catalog: Catalog, val sellType: SellType) {
        // Floor 7 - Auctions
        NECRON_HANDLE("Necron's Handle", Catalog.FLOOR_7, SellType.AUCTION),
        DARK_CLAYMORE("Dark Claymore", Catalog.FLOOR_7, SellType.AUCTION),
        DYE_NECRON("Necron Dye", Catalog.FLOOR_7, SellType.AUCTION),
        WITHER_HELMET("Wither Helmet", Catalog.FLOOR_7, SellType.AUCTION),
        WITHER_CHESTPLATE("Wither Chestplate", Catalog.FLOOR_7, SellType.AUCTION),
        WITHER_LEGGINGS("Wither Leggings", Catalog.FLOOR_7, SellType.AUCTION),
        WITHER_BOOTS("Wither Boots", Catalog.FLOOR_7, SellType.AUCTION),
        AUTO_RECOMBOBULATOR("Auto Recombobulator", Catalog.FLOOR_7, SellType.AUCTION),
        WITHER_CLOAK("Wither Cloak Sword", Catalog.FLOOR_7, SellType.AUCTION),
        STORM_THE_FISH("Storm the Fish", Catalog.FLOOR_7, SellType.AUCTION),
        GOLDOR_THE_FISH("Goldor the Fish", Catalog.FLOOR_7, SellType.AUCTION),
        MAXOR_THE_FISH("Maxor the Fish", Catalog.FLOOR_7, SellType.AUCTION),

        // Floor 7 - Bazaar
        IMPLOSION_SCROLL("Implosion", Catalog.FLOOR_7, SellType.BAZAAR),
        SHADOW_WARP_SCROLL("Shadow Warp", Catalog.FLOOR_7, SellType.BAZAAR),
        WITHER_SHIELD_SCROLL("Wither Shield", Catalog.FLOOR_7, SellType.BAZAAR),
        WITHER_BLOOD("Wither Blood", Catalog.FLOOR_7, SellType.BAZAAR),
        WITHER_CATALYST("Wither Catalyst", Catalog.FLOOR_7, SellType.BAZAAR),
        PRECURSOR_GEAR("Precursor Gear", Catalog.FLOOR_7, SellType.BAZAAR),
        FIFTH_MASTER_STAR("Fifth Master Star", Catalog.FLOOR_7, SellType.BAZAAR),
        SHARD_POWER_DRAGON("Power Dragon Shard", Catalog.FLOOR_7, SellType.BAZAAR),
        SHARD_APEX_DRAGON("Apex Dragon Shard", Catalog.FLOOR_7, SellType.BAZAAR),
        SHARD_WITHER("Wither Shard", Catalog.FLOOR_7, SellType.BAZAAR),

        // Floor 6 - Auctions
        GIANTS_SWORD("Giant's Sword", Catalog.FLOOR_6, SellType.AUCTION),
        PRECURSOR_EYE("Precursor Eye", Catalog.FLOOR_6, SellType.AUCTION),
        FEL_SKULL("Fel Skull", Catalog.FLOOR_6, SellType.AUCTION),
        SOULWEAVER_GLOVES("Soulweaver Gloves", Catalog.FLOOR_6, SellType.AUCTION),
        SUMMONING_RING("Summoning Ring", Catalog.FLOOR_6, SellType.AUCTION),
        NECROMANCER_LORD_HELMET("Necromancer Lord Helmet", Catalog.FLOOR_6, SellType.AUCTION),
        NECROMANCER_LORD_CHESTPLATE("Necromancer Lord Chestplate", Catalog.FLOOR_6, SellType.AUCTION),
        NECROMANCER_LORD_LEGGINGS("Necromancer Lord Leggings", Catalog.FLOOR_6, SellType.AUCTION),
        NECROMANCER_LORD_BOOTS("Necromancer Lord Boots", Catalog.FLOOR_6, SellType.AUCTION),
        NECROMANCER_SWORD("Necromancer Sword", Catalog.FLOOR_6, SellType.AUCTION),

        // Floor 6 - Bazaar
        GIANT_TOOTH("Giant Tooth", Catalog.FLOOR_6, SellType.BAZAAR),
        SADAN_BROOCH("Sadan's Brooch", Catalog.FLOOR_6, SellType.BAZAAR),
        FOURTH_MASTER_STAR("Fourth Master Star", Catalog.FLOOR_6, SellType.BAZAAR),

        // Floor 5 - Auctions
        SHADOW_FURY("Shadow Fury", Catalog.FLOOR_5, SellType.AUCTION),
        LAST_BREATH("Last Breath", Catalog.FLOOR_5, SellType.AUCTION),
        LIVID_DAGGER("Livid Dagger", Catalog.FLOOR_5, SellType.AUCTION),
        SHADOW_ASSASSIN_HELMET("Shadow Assassin Helmet", Catalog.FLOOR_5, SellType.AUCTION),
        SHADOW_ASSASSIN_CHESTPLATE("Shadow Assassin Chestplate", Catalog.FLOOR_5, SellType.AUCTION),
        SHADOW_ASSASSIN_LEGGINGS("Shadow Assassin Leggings", Catalog.FLOOR_5, SellType.AUCTION),
        SHADOW_ASSASSIN_BOOTS("Shadow Assassin Boots", Catalog.FLOOR_5, SellType.AUCTION),
        SHADOW_ASSASSIN_CLOAK("Shadow Assassin Cloak", Catalog.FLOOR_5, SellType.AUCTION),

        // Floor 5 - Bazaar
        AOTE_STONE("Warped Stone", Catalog.FLOOR_5, SellType.BAZAAR),
        DARK_ORB("Dark Orb", Catalog.FLOOR_5, SellType.BAZAAR),
        THIRD_MASTER_STAR("Third Master Star", Catalog.FLOOR_5, SellType.BAZAAR),

        // Floor 4 - Auctions
        PET_SPIRIT_LEGENDARY("[Lvl 1] §6Spirit", Catalog.FLOOR_4, SellType.AUCTION),
        PET_SPIRIT_EPIC("[Lvl 1] §5Spirit", Catalog.FLOOR_4, SellType.AUCTION),
        THORNS_BOOTS("Spirit Boots", Catalog.FLOOR_4, SellType.AUCTION),
        ITEM_SPIRIT_BOW("Spirit Shortbow", Catalog.FLOOR_4, SellType.AUCTION),
        SPIRIT_SWORD("Spirit Sword", Catalog.FLOOR_4, SellType.AUCTION),

        // Floor 4 - Bazaar
        SPIRIT_WING("Spirit Wing", Catalog.FLOOR_4, SellType.BAZAAR),
        SPIRIT_BONE("Spirit Bone", Catalog.FLOOR_4, SellType.BAZAAR),
        SPIRIT_DECOY("Spirit Stone", Catalog.FLOOR_4, SellType.BAZAAR),
        SECOND_MASTER_STAR("Second Master Star", Catalog.FLOOR_4, SellType.BAZAAR),
        SHARD_THORN("Thorn Shard", Catalog.FLOOR_4, SellType.BAZAAR),

        // Floor 3 - Auctions
        ADAPTIVE_HELMET("Adaptive Helmet", Catalog.FLOOR_3, SellType.AUCTION),
        ADAPTIVE_CHESTPLATE("Adaptive Chestplate", Catalog.FLOOR_3, SellType.AUCTION),
        ADAPTIVE_LEGGINGS("Adaptive Leggings", Catalog.FLOOR_3, SellType.AUCTION),
        ADAPTIVE_BOOTS("Adaptive Boots", Catalog.FLOOR_3, SellType.AUCTION),

        // Floor 3 - Bazaar
        SUSPICIOUS_VIAL("Suspicious Vial", Catalog.FLOOR_3, SellType.BAZAAR),
        FIRST_MASTER_STAR("First Master Star", Catalog.FLOOR_3, SellType.BAZAAR),

        // Floor 2 - Auctions
        STONE_BLADE("Adaptive Blade", Catalog.FLOOR_2, SellType.AUCTION),
        ADAPTIVE_BELT("Adaptive Belt", Catalog.FLOOR_2, SellType.AUCTION),
        SCARF_STUDIES("Scarf's Studies", Catalog.FLOOR_2, SellType.AUCTION),

        // Floor 2 - Bazaar
        RED_SCARF("Red Scarf", Catalog.FLOOR_2, SellType.BAZAAR),
        SHARD_SCARF("Scarf Shard", Catalog.FLOOR_2, SellType.BAZAAR),

        // Floor 1 - Auctions
        BONZO_MASK("Bonzo's Mask", Catalog.FLOOR_1, SellType.AUCTION),
        BONZO_STAFF("Bonzo's Staff", Catalog.FLOOR_1, SellType.AUCTION),
        BALLOON_SNAKE("Balloon Snake", Catalog.FLOOR_1, SellType.AUCTION),

        // Floor 1 - Bazaar
        RED_NOSE("Red Nose", Catalog.FLOOR_1, SellType.BAZAAR),

        // Miscellaneous - Auctions
        MASTER_SKULL_TIER_5("Master Skull - Tier 5", Catalog.MISC, SellType.AUCTION),
        MASTER_SKULL_TIER_4("Master Skull - Tier 4", Catalog.MISC, SellType.AUCTION),
        MASTER_SKULL_TIER_3("Master Skull - Tier 3", Catalog.MISC, SellType.AUCTION),
        MASTER_SKULL_TIER_2("Master Skull - Tier 2", Catalog.MISC, SellType.AUCTION),
        MASTER_SKULL_TIER_1("Master Skull - Tier 1", Catalog.MISC, SellType.AUCTION),

        // Enchantments - Bazaar
        ENCHANTMENT_THUNDERLORD_7("Thunderlord VII", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_OVERLOAD_1("Overload I", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_REJUVENATE_3("Rejuvenate III", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_REJUVENATE_2("Rejuvenate II", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_REJUVENATE_1("Rejuvenate I", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_FEATHER_FALLING_7("Feather Falling VII", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_FEATHER_FALLING_6("Feather Falling VI", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_INFINITE_QUIVER_7("Infinite Quiver VII", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_INFINITE_QUIVER_6("Infinite Quiver VI", Catalog.ENCHS, SellType.BAZAAR),
        ENCHANTMENT_LETHALITY_6("Lethality VI", Catalog.ENCHS, SellType.BAZAAR),

        // Ultimate Enchantments - Bazaar
        ENCHANTMENT_ULTIMATE_ONE_FOR_ALL_1("One For All I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_SOUL_EATER_1("Soul Eater I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_SWARM_1("Swarm I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_REND_2("Rend II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_REND_1("Rend I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_LEGION_1("Legion I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_LAST_STAND_2("Last Stand II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_LAST_STAND_1("Last Stand I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_WISE_2("Ultimate Wise II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_WISE_1("Ultimate Wise I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_WISDOM_2("Wisdom II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_WISDOM_1("Wisdom I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_BANK_3("Bank III", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_BANK_2("Bank II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_BANK_1("Bank I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_NO_PAIN_NO_GAIN_2("No Pain No Gain II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_NO_PAIN_NO_GAIN_1("No Pain No Gain I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_COMBO_2("Combo II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_COMBO_1("Combo I", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_JERRY_3("Ultimate Jerry III", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_JERRY_2("Ultimate Jerry II", Catalog.ULTS, SellType.BAZAAR),
        ENCHANTMENT_ULTIMATE_JERRY_1("Ultimate Jerry I", Catalog.ULTS, SellType.BAZAAR),

        // Miscellaneous - Bazaar
        RECOMBOBULATOR_3000("Recombobulator 3000", Catalog.MISC, SellType.BAZAAR),
        FUMING_POTATO_BOOK("Fuming Potato Book", Catalog.MISC, SellType.BAZAAR),
        HOT_POTATO_BOOK("Hot Potato Book", Catalog.MISC, SellType.BAZAAR),
        NECROMANCER_BROOCH("Necromancer's Brooch", Catalog.MISC, SellType.BAZAAR),
        ESSENCE_WITHER("Wither Essence", Catalog.MISC, SellType.BAZAAR),
        ESSENCE_UNDEAD("Undead Essence", Catalog.MISC, SellType.BAZAAR),
        DUNGEON_CHEST_KEY("Dungeon Chest Key", Catalog.MISC, SellType.BAZAAR);

        companion object {
            private val displayMap: Map<String, SellableItem> = entries.associateBy { it.displayName }

            fun toItem(displayName: String): SellableItem? {
                return displayMap[displayName]
            }

            fun toKey(displayName: String): String? {
                val filteredName = displayName.substringAfterLast("§")
                return displayMap[filteredName]?.name
            }
        }

        enum class Catalog {
            FLOOR_1,
            FLOOR_2,
            FLOOR_3,
            FLOOR_4,
            FLOOR_5,
            FLOOR_6,
            FLOOR_7,
            MISC,
            ENCHS,
            ULTS
        }

        enum class SellType {
            AUCTION,
            BAZAAR
        }
    }
}