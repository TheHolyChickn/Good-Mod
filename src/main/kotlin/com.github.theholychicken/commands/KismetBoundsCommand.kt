package com.github.theholychicken.commands

import com.github.theholychicken.utils.KismetCalculator
import com.github.theholychicken.utils.modMessage
import net.minecraft.command.*
import kotlin.jvm.Throws

class KismetBoundsCommand : CommandBase() {
    override fun getCommandName(): String {
        return "kismetBounds"
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return ""
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        val bounds = KismetCalculator.getRerollBounds()
        val EV = KismetCalculator.calculateExpectedProfit()
        modMessage("Reroll lower bound: ${bounds.first}")
        modMessage("Reroll upper bound: ${bounds.second}")
        modMessage("Expected value: $EV")
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandAliases(): List<String> {
        return listOf("goodmod:kismetBounds")
    }
}