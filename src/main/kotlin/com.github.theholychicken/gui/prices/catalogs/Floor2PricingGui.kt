package com.github.theholychicken.gui.prices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.gui.prices.ConfigPricingGui
import com.github.theholychicken.managers.SellableItemParser

class Floor2PricingGui : AbstractPricingGui() {
    override val items: List<PricingElement> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.FLOOR_2
    }.map {
        PricingElement(it.displayName, it.sellType, it.hexColor)
    }
    override val guiName = "floor 2"

    override fun openMainMenu() {
        ConfigPricingGui.open()
    }

    companion object {
        fun open() {
            GoodMod.display = Floor2PricingGui()
        }
    }
}