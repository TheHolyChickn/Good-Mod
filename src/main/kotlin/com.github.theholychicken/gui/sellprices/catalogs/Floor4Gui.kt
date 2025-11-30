package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class Floor4Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzFloor4Items
    override val guiName = "Floor 4"

    // allows gui to be opened with Floor4Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor4Gui()
        }
    }
}