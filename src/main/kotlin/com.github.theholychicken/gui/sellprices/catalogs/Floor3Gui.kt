package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor3Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor3Items
    override val guiName = "Floor 3"

    // allows gui to be opened with Floor3Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor3Gui()
        }
    }

}