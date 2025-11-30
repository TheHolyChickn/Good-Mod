package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser

class MiscGui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.SellableItem.bzMiscItems
    override val guiName = "Miscellaneous"

    // allows gui to be opened with MiscGui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = MiscGui()
        }
    }
}