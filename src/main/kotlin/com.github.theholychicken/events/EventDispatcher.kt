package com.github.theholychicken.events

import com.github.theholychicken.utils.name
import com.github.theholychicken.utils.waitUntilLastItem
import kotlinx.coroutines.*
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.EventBus
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object EventDispatcher {

    /**
     * Dispatches [GuiLoadedEvent]
     */
    @OptIn(DelicateCoroutinesApi::class)
    @SubscribeEvent
    fun onGuiOpen(event: GuiOpenEvent) = GlobalScope.launch {
        val container = (event.gui as? GuiChest)?.inventorySlots as? ContainerChest ?: return@launch

        val deferred = waitUntilLastItem(container)
        try { deferred.await() } catch (e: Exception) { return@launch } // Wait until the last item in the chest isn't null

        MinecraftForge.EVENT_BUS.post(GuiLoadedEvent(container.name, container))
    }
}
