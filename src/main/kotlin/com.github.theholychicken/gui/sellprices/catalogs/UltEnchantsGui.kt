package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class UltEnchantsGui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzUltEnchants
    override val guiName = "Ultimate Enchantments"

    // allows gui to be opened with UltEnchantsGui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = UltEnchantsGui()
        }
    }
}