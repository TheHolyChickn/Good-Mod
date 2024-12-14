package com.github.theholychicken.events

import net.minecraft.inventory.ContainerChest
import net.minecraftforge.fml.common.eventhandler.Event

data class GuiLoadedEvent(val name: String, val gui: ContainerChest) : Event()
