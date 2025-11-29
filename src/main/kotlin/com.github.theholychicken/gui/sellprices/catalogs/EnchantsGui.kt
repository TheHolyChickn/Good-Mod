package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiButton

class EnchantsGui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser
        .SellableItem.entries.filter {
            it.catalog == SellableItemParser.SellableItem.Catalog.ENCHS
                    && it.sellType == SellableItemParser.SellableItem.SellType.BAZAAR
        }
    override val guiName = "Enchants"

    override fun initGui() {
        super.initGui()
        // init buttons here using util file
        renderRows(
            items,
            width,
            height,
            listOf(
                0xAA00AA,
                0xAAAAAA,
                0xAAAAAA,
                0xAAAAAA,
                0xAAAAAA,
                0xAA00AA,
                0x5555FF,
                0xAA00AA,
                0x5555FF,
                0x5555FF
            )
        ).forEach { buttonList.add(it) }
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))
    }

    // allows gui to be opened with EnchantsGui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = EnchantsGui()
        }
    }
}