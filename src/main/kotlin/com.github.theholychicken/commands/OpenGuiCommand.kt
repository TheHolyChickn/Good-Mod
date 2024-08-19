package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.gui.ConfigGUI
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class OpenGuiCommand : CommandBase() {
    override fun getCommandName(): String {
        return GuiConfig.commandNames["goodmod"] ?: "goodmod"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return ":3"
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        GoodMod.logger.info("Opening GUI.")
        GoodMod.display = ConfigGUI()
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun getCommandAliases(): List<String> {
        return mutableListOf("goodmod:nicepb")
    }
}