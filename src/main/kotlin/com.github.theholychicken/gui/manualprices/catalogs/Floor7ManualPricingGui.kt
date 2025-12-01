package com.github.theholychicken.gui.manualprices.catalogs

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.SellableItemParser
import net.minecraft.client.gui.GuiTextField

class Floor7ManualPricingGui : AbstractManualPricingGui() {
    override val items: List<SellableItemParser.SellableItem> = SellableItemParser.items.filter { it.catalog == SellableItemParser.SellableItem.Catalog.FLOOR_7 }
    override val guiName: String = "Floor 7"

    companion object {
        fun open() {
            GoodMod.Companion.display = Floor7ManualPricingGui()
        }
    }
}