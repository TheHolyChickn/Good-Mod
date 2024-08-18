package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychickn.theholychicknaddons.owo.Meower
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import org.apache.logging.log4j.LogManager

class HelpCommand : CommandBase() {
    override fun getCommandName(): String { return "goodmod:commands" }

    override fun getCommandUsage(sender: ICommandSender): String { return "" }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {

        GoodMod.logger.info("Running cute rn")
        LogManager.getLogger("Cute").info("Running cute rn")
        sender.addChatMessage(ChatComponentText("§2Current command aliases:"))
        sender.addChatMessage(ChatComponentText("§3nicepb: " + Meower.meow.nicepbCommand))
        sender.addChatMessage(ChatComponentText("§3owo: " + Meower.meow.owoCommand))
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}