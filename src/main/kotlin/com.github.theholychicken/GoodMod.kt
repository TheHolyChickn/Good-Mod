package com.github.theholychicken

import com.github.theholychicken.commands.*
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.config.SellPricesConfig
import com.github.theholychicken.gui.ChestProfitTooltip
import com.github.theholychicken.gui.CroesusProfitHUD
import com.github.theholychicken.gui.MainCroesusHUD
import com.github.theholychicken.managers.*
import com.github.theholychicken.managers.apiclients.HttpClient
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLModDisabledEvent
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
        SellPricesConfig.loadConfig()
        SellPricesConfig.absorbManualPrices() // for migration from legacy vers
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        // Event subscriptions
        listOf(
            this,
            DungeonChestScanner,
            CroesusProfitHUD,
            MainCroesusHUD,
            ChestProfitTooltip
        ).forEach { MinecraftForge.EVENT_BUS.register(it) }

        // Commands
        listOf(
            OpenGuiCommand(),
            GetItemsCommand(),
            HelpCommand(),
            UpdateAuctionsCommand(),
            ConfigManualPricesTestCommand(), // test command
            KismetBoundsCommand()
        ).forEach { ClientCommandHandler.instance.registerCommand(it) }

        HttpClient.scheduleApiPulls()

        // Uncomment to access developer command - reloads AwA config when run
        // me, a few months later: wtf does this mean
        //ClientCommandHandler.instance.registerCommand(ReloadCommand());
    }

    @Mod.EventHandler
    fun onShutdown(event: FMLModDisabledEvent) {
        HttpClient.executor.shutdown()
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