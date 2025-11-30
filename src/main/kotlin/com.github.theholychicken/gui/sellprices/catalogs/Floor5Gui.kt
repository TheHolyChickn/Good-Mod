package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor5Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor5Items
    override val guiName = "Floor 5"

    // allows gui to be opened with Floor5Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor5Gui()
        }
    }
}