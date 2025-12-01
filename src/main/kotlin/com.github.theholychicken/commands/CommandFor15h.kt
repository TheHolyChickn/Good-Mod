package com.github.theholychicken.commands

import com.github.theholychicken.config.ManualPricesConfig
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import kotlin.jvm.Throws

class CommandFor15h : CommandBase() {
    override fun getCommandName(): String {
        return "goodmod:manualpricing:set_all_vanishing_prices_to_api"
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return ""
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        ManualPricesConfig.manualPrices.forEach { (displayName, value) ->
            if (value == 0.0) {
                ManualPricesConfig.manualPrices[displayName] = -1.0
            }
        }
        ManualPricesConfig.saveConfig()
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandAliases(): List<String> {
        return listOf()
    }
}