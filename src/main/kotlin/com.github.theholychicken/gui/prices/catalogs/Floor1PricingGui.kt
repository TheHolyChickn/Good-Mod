package com.github.theholychicken.gui.prices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.gui.prices.ConfigPricingGui
import com.github.theholychicken.managers.SellableItemParser

class Floor1PricingGui : AbstractPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.FLOOR_1
    }
    override val guiName = "floor 1"

    override fun openMainMenu() {
        ConfigPricingGui.open()
    }

    companion object {
        fun open() {
            GoodMod.display = Floor1PricingGui()
        }
    }
}