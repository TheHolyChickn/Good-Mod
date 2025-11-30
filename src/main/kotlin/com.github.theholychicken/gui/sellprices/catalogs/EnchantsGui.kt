package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class EnchantsGui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzEnchants
    override val guiName = "Enchants"

    // allows gui to be opened with EnchantsGui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = EnchantsGui()
        }
    }
}