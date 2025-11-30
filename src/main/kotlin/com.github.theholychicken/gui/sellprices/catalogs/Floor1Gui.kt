package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor1Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor1Items
    override val guiName = "Floor 1"

    // allows gui to be opened with Floor1Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor1Gui()
        }
    }
}