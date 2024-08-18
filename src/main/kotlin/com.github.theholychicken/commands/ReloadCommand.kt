package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.ItemDropParser
import com.github.theholychicken.utils.modMessage
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class ReloadCommand : CommandBase() {

    override fun getCommandName(): String {
        return GuiConfig.commandNames["reloadLoot"] ?: "goodmod:dev_commands:furry"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return ":3"
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        GoodMod.logger.info("Reloading dungeon drops")
        ItemDropParser.reloadConfig()
        modMessage("§3Dungeon drops successfully reloaded")
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}