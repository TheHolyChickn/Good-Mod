package com.github.theholychicken.gui.utils

import net.minecraft.client.gui.GuiButton

class DropdownMenu (
    val x: Int,
    val y: Int,
    val width: Int,
    val options: List<Pair<String, () -> Unit>>,
    private var selectedIndex: Int
) {
    var expanded: Boolean = false
    private var buttons: MutableList<GuiButton> = mutableListOf()

    fun initButtons(buttonList: MutableList<GuiButton>) {
        // render active button
        buttonList.add(GuiButton(100, x, y, width, 20, options[selectedIndex].first))

        options.forEachIndexed {index, (option, _) ->
            buttons.add(GuiButton(101 + index, x, y + (index + 1) * 20, width, 20, option).apply {
                visible = false
            })
        }
        buttonList.addAll(buttons)
    }

    fun handleButtonClick(button: GuiButton) {
        when (button.id) {
            100 -> {
                expanded = !expanded
                buttons.forEach { it.visible = expanded }
            }
            in 101..(100 + options.size) -> {
                selectedIndex = button.id - 101
                expanded = false
                buttons.forEach { it.visible = false }
                options[selectedIndex].second.invoke()
            }
        }
    }

    fun updateDropdownLabel(buttonList: MutableList<GuiButton>) {
        buttonList.find { it.id == 100 }?.displayString = options[selectedIndex].first
    }

    fun closeDropdown() {
        expanded = false
        buttons.forEach { it.visible = false }
    }
}