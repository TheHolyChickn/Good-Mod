package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class UltEnchantsManualPricingGui : AbstractManualPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.ULTS
    }
    override val guiName = "ultimate enchants"

    companion object {
        fun open() {
            GoodMod.Companion.display = UltEnchantsManualPricingGui()
        }
    }
}