package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor6Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor6Items
    override val guiName = "Floor 6"

    // allows gui to be opened with Floor6Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor6Gui()
        }
    }

}