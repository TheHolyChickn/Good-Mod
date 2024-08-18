package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychicken.utils.modMessage
import net.minecraft.command.*

class HelpCommand : CommandBase() {
    override fun getCommandName(): String { return "goodmod:commands" }

    override fun getCommandUsage(sender: ICommandSender): String { return "" }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        GoodMod.logger.info("Printing command aliases to chat.")
        modMessage("Current command aliases:", false)
        modMessage("nicepb: " + GoodMod.openGUICommandName, false)
        modMessage("owo: " + GoodMod.getItemsCommandName, false)
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}