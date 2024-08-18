package com.github.theholychicken.utils

import com.github.theholychicken.GoodMod.Companion.mc
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

fun modMessage(message: Any?, prefix: Boolean = true) {
    mc.thePlayer?.addChatMessage(ChatComponentText(if (prefix) "§r§3good mod §r§f» $message" else message.toString()))
}
