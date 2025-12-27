package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor4ManualPricingGui : AbstractManualPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.FLOOR_4
    }
    override val guiName = "floor 4"

    companion object {
        fun open() {
            GoodMod.Companion.display = Floor4ManualPricingGui()
        }
    }
}