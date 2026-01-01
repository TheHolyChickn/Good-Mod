package com.github.theholychicken.gui

import com.github.theholychicken.GoodMod
import com.github.theholychicken.managers.ItemDropParser
import com.github.theholychicken.managers.SellableItemParser
import com.github.theholychicken.managers.SellableItemParser.SellableItem

class ItemDropHUD : AbstractScrollableGui() {

    override val guiTitle = "dungeon drops"
    private val rowHeight = 15

    // Wrapper to treat Enum items and Shiny strings uniformly for display
    data class DisplayItem(
        val name: String,
        val displayName: String,
        val catalog: SellableItem.Catalog,
        val color: Int
    )

    private val displayedItems: List<DisplayItem> = run {
        val list = mutableListOf<DisplayItem>()

        // 1. Add Shiny Items (Force to Floor 7)
        SellableItemParser.shinyItems.forEach { shinyName ->
            list.add(DisplayItem(
                name = shinyName, // Key for config lookup
                displayName = shinyName,
                catalog = SellableItem.Catalog.FLOOR_7,
                color = 0xFFAA00
            ))
        }

        // 2. Add Enum Items
        SellableItem.entries.forEach { item ->
            list.add(DisplayItem(
                name = item.name, // Key for config lookup
                displayName = item.displayName,
                catalog = item.catalog,
                color = item.hexColor
            ))
        }

        // 3. Sort by Catalog order, then by name
        list.sortedWith(compareBy<DisplayItem> { it.catalog }.thenBy { it.displayName })
    }

    override fun getContentHeight(): Int {
        var h = 0
        var currentCatalog: SellableItem.Catalog? = null
        displayedItems.forEach { item ->
            if (item.catalog != currentCatalog) {
                currentCatalog = item.catalog
                h += rowHeight
            }
            h += rowHeight
        }
        return h
    }

    override fun drawContent(mouseX: Int, mouseY: Int, partialTicks: Float) {
        var currentCatalog: SellableItem.Catalog? = null
        var yOffset = 0

        displayedItems.forEach { item ->
            // Draw Header if Catalog changes
            if (item.catalog != currentCatalog) {
                currentCatalog = item.catalog

                // Draw Header Background
                drawRect(leftMargin, yOffset, width - rightMargin, yOffset + rowHeight, 0xFF333333.toInt())
                drawCenteredString(
                    fontRendererObj,
                    "--- ${getCatalogName(item.catalog)} ---",
                    width / 2,
                    yOffset + (rowHeight - fontRendererObj.FONT_HEIGHT) / 2,
                    0x00FF99
                )
                yOffset += rowHeight
            }

            // Draw Item Row
            val count = ItemDropParser.dropsConfig.getItemCount(item.name)

            // Divider
            drawRect(leftMargin, yOffset + rowHeight - 1, width - rightMargin, yOffset + rowHeight, 0xFF555555.toInt())
            val textY = yOffset + (rowHeight - fontRendererObj.FONT_HEIGHT) / 2

            fontRendererObj.drawStringWithShadow(
                item.displayName,
                (leftMargin + 5).toFloat(),
                textY.toFloat(),
                item.color
            )

            val countString = count.toString()
            fontRendererObj.drawStringWithShadow(
                countString,
                (width - rightMargin - 5 - fontRendererObj.getStringWidth(countString)).toFloat(),
                textY.toFloat(),
                0x00FFFF
            )

            yOffset += rowHeight
        }
    }

    override fun doesGuiPauseGame() = false

    private fun getCatalogName(catalog: SellableItem.Catalog): String? {
        return when (catalog) {
            SellableItem.Catalog.FLOOR_7 -> "floor 7"
            SellableItem.Catalog.FLOOR_6 -> "floor 6"
            SellableItem.Catalog.FLOOR_5 -> "floor 5"
            SellableItem.Catalog.FLOOR_4 -> "floor 4"
            SellableItem.Catalog.FLOOR_3 -> "floor 3"
            SellableItem.Catalog.FLOOR_2 -> "floor 2"
            SellableItem.Catalog.FLOOR_1 -> "floor 1"
            SellableItem.Catalog.ULTS -> "ults"
            SellableItem.Catalog.ENCHANTS -> "enchants"
            SellableItem.Catalog.MISC -> "miscellaneous"
            SellableItem.Catalog.KUUDRA -> "kuudra"
        }
    }

    companion object {
        fun open() {
            GoodMod.display = ItemDropHUD()
        }
    }
}