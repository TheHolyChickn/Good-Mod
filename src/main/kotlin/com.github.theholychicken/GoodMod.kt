package com.github.theholychicken

import com.github.theholychicken.commands.*
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.DungeonChestScanner
import com.github.theholychicken.managers.ItemDropParser
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
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
        GuiConfig.loadGuiConfig()
        ItemDropParser
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        // Event subscriptions
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(DungeonChestScanner)
        // Commands
        ClientCommandHandler.instance.registerCommand(OpenGuiCommand())
        ClientCommandHandler.instance.registerCommand(GetItemsCommand())
        ClientCommandHandler.instance.registerCommand(HelpCommand())
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

        var showUwU: Boolean = true
        var getItemsCommandName: String = "getItems"
        var openGUICommandName: String = "goodmod"
        var reloadCommandName: String = "goodmod:dev_commands:furry"
    }
}