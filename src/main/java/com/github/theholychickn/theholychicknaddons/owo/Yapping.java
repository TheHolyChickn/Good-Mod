package com.github.theholychickn.theholychicknaddons.owo;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Yapping {

    public static void log(String meow) {
        GoodMod.Kitten.info("[good mod] " + meow);
    }

    public static void chat(String meow) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§r§3good mod §r§f» " + meow + "§r"));
    }
}
