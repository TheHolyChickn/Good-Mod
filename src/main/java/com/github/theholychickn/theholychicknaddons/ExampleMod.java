package com.github.theholychickn.theholychicknaddons;

import com.github.theholychickn.theholychicknaddons.owo.Awoo;
import com.github.theholychickn.theholychicknaddons.owo.Meower;
import com.github.theholychickn.theholychicknaddons.owo.UwU;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.OwO;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.WoofWoof;
import com.github.theholychickn.theholychicknaddons.owo.test_gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "goodmod", useMetadata=true)
public class ExampleMod {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Meower.loadMeow();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Awoo());
        MinecraftForge.EVENT_BUS.register(new UwU());
        ClientCommandHandler.instance.registerCommand(new OwO());
        ClientCommandHandler.instance.registerCommand(new WoofWoof());
    }
}
