package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiButton

class Floor3Gui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser
        .SellableItem.entries.filter {
            it.catalog == SellableItemParser.SellableItem.Catalog.FLOOR_3
                    && it.sellType == SellableItemParser.SellableItem.SellType.BAZAAR
        }
    override val guiName = "Floor 3"

    override fun initGui() {
        super.initGui()
        // init buttons here using util file
        renderRows(
            items,
            width,
            height,
            listOf(
                0x5555FF,
                0xAA00AA
            )
        ).forEach { buttonList.add(it) }
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))
    }

    // allows gui to be opened with Floor3Gui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = Floor3Gui()
        }
    }

}