package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiButton

class MiscGui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser
        .SellableItem.entries.filter {
            it.catalog == SellableItemParser.SellableItem.Catalog.MISC
                    && it.sellType == SellableItemParser.SellableItem.SellType.BAZAAR
        }
    override val guiName = "Miscellaneous"

    override fun initGui() {
        super.initGui()
        buttonList.clear()
        // init buttons here using util file
        renderRows(
            items,
            width,
            height,
            listOf(
                0xFFAA00,
                0xAA00AA,
                0xAA00AA,
                0x5555FF,
                0xFF55FF,
                0xFF55FF,
                0x5555FF
            )
        ).forEach { buttonList.add(it) }
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))
    }

    // allows gui to be opened with MiscGui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = MiscGui()
        }
    }
}