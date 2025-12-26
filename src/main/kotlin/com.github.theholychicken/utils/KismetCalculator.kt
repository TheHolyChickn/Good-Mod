package com.github.theholychicken.utils

import com.github.theholychicken.managers.SellableItemParser
import kotlin.math.max

/*
 * This is a test class for some computation.
 * ok so i did not write this i gotta chyeck these computations
 */
object KismetCalculator {

    // Configuration
    private const val MAX_CHEST_QUALITY = 476
    // Determine the price of a reroll (Kismet Feather).
    // You may want to hook this into your AuctionParser if you want it dynamic.
    var REROLL_ITEM_PRICE: Double = 1390864.0

    data class LootItem(
        val displayName: String,
        val unlockCost: Double, // The cost to open the chest if this is the max item
        val quality: Int,       // "Weight" in your terminology (Capacity cost)
        val dropWeight: Double  // "Probability" in your terminology
    )

    // The M7 Bedrock Loot Table based on the image provided
    private val lootTable = listOf(
        LootItem("Necron's Handle", 100_000_000.0, 380, 18.0),
        LootItem("Shadow Warp", 50_000_000.0, 380, 25.0),
        LootItem("Wither Shield", 50_000_000.0, 380, 25.0),
        LootItem("Implosion", 50_000_000.0, 380, 25.0),
        LootItem("Dark Claymore", 150_000_000.0, 360, 10.0),
        LootItem("Auto Recombobulator", 10_000_000.0, 330, 80.0),
        LootItem("Fifth Master Star", 9_000_000.0, 320, 30.0),
        LootItem("Wither Chestplate", 10_000_000.0, 310, 80.0),
        LootItem("One For All I", 2_000_000.0, 290, 80.0),
        LootItem("Master Skull - Tier 5", 32_000_000.0, 250, 24.0),
        LootItem("Recombobulator 3000", 6_000_000.0, 250, 500.0),
        LootItem("Wither Leggings", 6_000_000.0, 250, 320.0),
        LootItem("Wither Cloak Sword", 4_500_000.0, 230, 480.0),
        LootItem("Wither Helmet", 4_000_000.0, 210, 480.0),
        LootItem("Wither Blood", 3_000_000.0, 210, 480.0),
        LootItem("Thunderlord VII", 2_000_000.0, 200, 20.0),
        LootItem("Soul Eater I", 2_000_000.0, 180, 800.0),
        LootItem("Fuming Potato Book", 2_000_000.0, 175, 400.0),
        LootItem("Wither Boots", 2_500_000.0, 170, 480.0),
        LootItem("Wither Catalyst", 2_000_000.0, 160, 400.0),
        LootItem("Hot Potato Book", 2_000_000.0, 160, 800.0),
        LootItem("Precursor Gear", 2_000_000.0, 140, 1200.0),
        LootItem("No Pain No Gain II", 2_000_000.0, 120, 400.0),
        LootItem("Combo II", 2_000_000.0, 120, 1000.0),
        LootItem("Rejuvenate III", 2_000_000.0, 100, 1000.0),
        LootItem("Bank III", 2_000_000.0, 100, 500.0),
        LootItem("Wisdom II", 2_000_000.0, 100, 500.0),
        LootItem("Ultimate Wise II", 2_000_000.0, 100, 800.0),
        LootItem("Ultimate Jerry III", 2_000_000.0, 100, 600.0),
        LootItem("Last Stand II", 2_000_000.0, 100, 1000.0),
        LootItem("Infinite Quiver VII", 2_000_000.0, 80, 1000.0),
        LootItem("Feather Falling VII", 2_000_000.0, 80, 320.0),
        LootItem("Storm The Fish", 2_000_000.0, 61, 5.0),
        LootItem("Maxor The Fish", 2_000_000.0, 61, 5.0),
        LootItem("Goldor The Fish", 2_000_000.0, 61, 5.0)
    )

    /**
     * Calculates the Expected Value (EV) of the profit of the Bedrock Chest.
     * This performs a DFS on the probability tree defined by the Quality constraint.
     */
    fun calculateExpectedProfit(): Double {
        // Fetch current market prices
        val prices = SellableItemParser.auctionPrices

        // Recursive helper to sum EV
        // currentQuality: The capacity remaining in the chest
        // probabilityStack: The probability of reaching this specific state
        // currentItems: The items currently "in" this hypothetical chest
        // pool: The items remaining in the loot table to be picked
        fun getEVRecursive(
            currentQuality: Int,
            probabilityStack: Double,
            currentItems: List<LootItem>,
            pool: List<LootItem>
        ): Double {

            // 1. Identify valid drops (Quality <= Remaining Capacity)
            val validDrops = pool.filter { it.quality <= currentQuality }

            // Base Case: No more items can drop.
            // We calculate the profit of this specific chest outcome and weight it by its probability.
            if (validDrops.isEmpty()) {
                val grossValue = currentItems.sumOf { prices[it.displayName] ?: 0.0 }
                // Cost is the max unlock cost of the items inside
                val unlockCost = currentItems.maxOfOrNull { it.unlockCost } ?: 0.0
                val netProfit = grossValue - unlockCost

                return netProfit * probabilityStack
            }

            // Recursive Step: Iterate through valid drops
            var totalBranchEV = 0.0

            // Calculate normalization factor for conditional probability
            // "Slots are calculated sequentially... one of the remaining items is selected by drop chance"
            val totalDropWeight = validDrops.sumOf { it.dropWeight }

            for (item in validDrops) {
                // Conditional probability of this item dropping next
                val conditionalProb = item.dropWeight / totalDropWeight

                // New probability for the next state
                val nextProb = probabilityStack * conditionalProb

                // Continue recursion
                // Remove item from pool, subtract quality, add to chest
                totalBranchEV += getEVRecursive(
                    currentQuality - item.quality,
                    nextProb,
                    currentItems + item,
                    pool - item
                )
            }

            return totalBranchEV
        }

        // Start recursion
        return getEVRecursive(MAX_CHEST_QUALITY, 1.0, emptyList(), lootTable)
    }

    /**
     * Returns the [LowerBound, UpperBound] for profit.
     * * UpperBound (Reroll Threshold): If current profit < UpperBound, REROLL.
     * LowerBound: The theoretical minimum profit of the chest (worst case scenario).
     */
    fun getRerollBounds(): Pair<Double, Double> {
        val expectedValue = calculateExpectedProfit()

        // From Question 7: Reroll if Profit < E(Chest) - Cost(Reroll)
        val upperBound = expectedValue - REROLL_ITEM_PRICE

        // Calculate absolute worst case (Lower Bound) just for display/info
        // This is a quick approximation of the "worst luck" path
        // (This part is optional, but helpful for the bounds request)
        val prices = SellableItemParser.auctionPrices
        fun getMinRecursive(currentQuality: Int, currentItems: List<LootItem>, pool: List<LootItem>): Double {
            val validDrops = pool.filter { it.quality <= currentQuality }
            if (validDrops.isEmpty()) {
                val gross = currentItems.sumOf { prices[it.displayName] ?: 0.0 }
                val cost = currentItems.maxOfOrNull { it.unlockCost } ?: 0.0
                return gross - cost
            }
            // In a "min" search, we must check all paths because greedy min isn't always true
            // due to the "max cost" mechanic. However, simply picking the lowest value path works.
            return validDrops.minOf { item ->
                getMinRecursive(currentQuality - item.quality, currentItems + item, pool - item)
            }
        }
        val lowerBound = getMinRecursive(MAX_CHEST_QUALITY, emptyList(), lootTable)

        return Pair(lowerBound, upperBound)
    }

    /**
     * Helper to be called by your mod's rendering logic.
     * returns true if you should reroll.
     */
    fun shouldReroll(currentChestProfit: Double): Boolean {
        val (_, threshold) = getRerollBounds()
        return currentChestProfit < threshold
    }
}