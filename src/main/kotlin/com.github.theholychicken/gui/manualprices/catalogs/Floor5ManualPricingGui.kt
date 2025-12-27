package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor5ManualPricingGui : AbstractManualPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.FLOOR_5
    }
    override val guiName = "floor 5"

    companion object {
        fun open() {
            GoodMod.Companion.display = Floor5ManualPricingGui()
        }
    }
}