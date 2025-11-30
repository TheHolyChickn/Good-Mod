package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor7Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor7Items
    override val guiName = "Floor 7"

    companion object {
        fun open() {
            GoodMod.Companion.display = Floor7Gui()
        }
    }
}