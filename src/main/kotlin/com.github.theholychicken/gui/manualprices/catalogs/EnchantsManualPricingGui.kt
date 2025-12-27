package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class EnchantsManualPricingGui : AbstractManualPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.ENCHANTS
    }
    override val guiName = "enchants"

    companion object {
        fun open() {
            GoodMod.Companion.display = EnchantsManualPricingGui()
        }
    }
}