package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor2Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor2Items
    override val guiName = "Floor 2"

    // allows gui to be opened with Floor2Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor2Gui()
        }
    }
}