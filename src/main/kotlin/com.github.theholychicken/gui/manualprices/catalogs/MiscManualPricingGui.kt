package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class MiscManualPricingGui : AbstractManualPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.MISC
    }
    override val guiName = "miscellanious items"

    companion object {
        fun open() {
            GoodMod.Companion.display = MiscManualPricingGui()
        }
    }
}