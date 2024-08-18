package com.github.theholychickn.theholychicknaddons;

import com.github.theholychickn.theholychicknaddons.owo.Awoo;
import com.github.theholychickn.theholychicknaddons.owo.Fuwwy;
import com.github.theholychickn.theholychicknaddons.owo.Meower;
import com.github.theholychickn.theholychicknaddons.owo.UwU;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.Cute;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.Furry;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.OwO;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.WoofWoof;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "goodmod", useMetadata=true, clientSideOnly = true)
public class GoodMod {

    // Logger
    public static final Logger Kitten = LogManager.getLogger("good mod");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Meower.loadMeow();
        Awoo.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Event subscriptions
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Fuwwy());
        // Commands
        ClientCommandHandler.instance.registerCommand(new OwO());
        ClientCommandHandler.instance.registerCommand(new WoofWoof());
        ClientCommandHandler.instance.registerCommand(new Cute());
        ClientCommandHandler.instance.registerCommand(new Furry());
    }

    // Bonsai witchcraft
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (display != null) {
            Minecraft.getMinecraft().displayGuiScreen(display);
            display = null;
        }
    }

    static GuiScreen display = null;
    static public void setDisplay(GuiScreen newDisplay) {
        display = newDisplay;
    }
}
