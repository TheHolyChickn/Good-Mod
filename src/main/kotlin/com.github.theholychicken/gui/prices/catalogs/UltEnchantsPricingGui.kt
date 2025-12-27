package com.github.theholychicken.gui.prices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.gui.prices.ConfigPricingGui
import com.github.theholychicken.managers.SellableItemParser

class UltEnchantsPricingGui : AbstractPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter {
        it.catalog == SellableItemParser.SellableItem.Catalog.ULTS
    }
    override val guiName = "ult enchs"

    override fun openMainMenu() {
        ConfigPricingGui.open()
    }

    companion object {
        fun open() {
            GoodMod.display = UltEnchantsPricingGui()
        }
    }
}