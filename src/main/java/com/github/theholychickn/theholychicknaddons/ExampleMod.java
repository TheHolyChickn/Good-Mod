package com.github.theholychickn.theholychicknaddons;

import com.github.theholychickn.theholychicknaddons.owo.Awoo;
import com.github.theholychickn.theholychicknaddons.owo.UwU;
import com.github.theholychickn.theholychicknaddons.owo.bwaa.OwO;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "goodmod", useMetadata=true)
public class ExampleMod {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Dirt: " + Blocks.dirt.getUnlocalizedName());
		// Below is a demonstration of an access-transformed class access.
        System.out.println("Color State: " + new GlStateManager.Color());
        MinecraftForge.EVENT_BUS.register(new Awoo());
        MinecraftForge.EVENT_BUS.register(new UwU());
        ClientCommandHandler.instance.registerCommand(new OwO());
    }
}
