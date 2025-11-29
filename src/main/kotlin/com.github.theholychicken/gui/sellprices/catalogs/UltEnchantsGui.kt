package com.github.theholychicken.gui.sellprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiButton

class UltEnchantsGui : AbstractCatalogGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser
        .SellableItem.entries.filter {
            it.catalog == SellableItemParser.SellableItem.Catalog.ULTS
                    && it.sellType == SellableItemParser.SellableItem.SellType.BAZAAR
        }
    override val guiName = "Ultimate Enchantments"

    override fun initGui() {
        super.initGui()
        // init buttons here using util file
        renderRows(
            items,
            width,
            height,
            items.indices.map { _ -> 0xFF55FF }).forEach { buttonList.add(it) }
        buttonList.add(GuiButton(100, width - 103, 3, 100, 20, "Back"))
    }

    // allows gui to be opened with UltEnchantsGui.open()
    companion object {
        fun open() {
            GoodMod.Companion.display = UltEnchantsGui()
        }
    }
}