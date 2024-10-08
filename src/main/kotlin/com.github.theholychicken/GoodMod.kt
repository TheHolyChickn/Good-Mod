package com.github.theholychicken

import com.github.theholychicken.commands.*
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.DungeonChestScanner
import com.github.theholychicken.managers.ItemDropParser
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.item.Item
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = "goodmod", useMetadata = true, clientSideOnly = true)
class GoodMod {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent?) {
        GuiConfig.loadConfig()
        if (GuiConfig.commandNames.isEmpty()) {
            GuiConfig.initConfig()
        }
        ItemDropParser.dropsConfig.loadConfig()
        if (ItemDropParser.dropsConfig.getList().isEmpty()) {
            ItemDropParser.initConfig()
        }
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        // Event subscriptions
        listOf(
            this,
            DungeonChestScanner
        ).forEach { MinecraftForge.EVENT_BUS.register(it) }

        // Commands
        listOf(
            OpenGuiCommand(),
            GetItemsCommand(),
            HelpCommand()
        ).forEach { ClientCommandHandler.instance.registerCommand(it) }

        // Uncomment to access developer command - reloads AwA config when run
        //ClientCommandHandler.instance.registerCommand(ReloadCommand());
    }

    // Bonsai witchcraft
    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || display == null) return
        mc.displayGuiScreen(display)
        display = null
    }

    companion object {
        var display: GuiScreen? = null
        val mc: Minecraft = Minecraft.getMinecraft()
        val logger: Logger = LogManager.getLogger("goodmod")

        var showUwU: Boolean = false
    }
}