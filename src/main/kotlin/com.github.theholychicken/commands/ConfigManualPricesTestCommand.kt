package com.github.theholychicken.commands

import com.github.theholychicken.gui.manualprices.ConfigManualPrices
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import kotlin.jvm.Throws

class ConfigManualPricesTestCommand : CommandBase() {
    override fun getCommandName(): String {
        return "goodmod:manualprices"
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return ""
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        ConfigManualPrices.open()
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandAliases(): List<String> {
        return listOf<String>()
    }
}