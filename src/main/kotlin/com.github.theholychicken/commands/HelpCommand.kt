package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.utils.modMessage
import net.minecraft.command.*

class HelpCommand : CommandBase() {
    override fun getCommandName(): String { return "goodmod:commands" }

    override fun getCommandUsage(sender: ICommandSender): String { return "" }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        GoodMod.logger.info("Printing command aliases to chat.")
        modMessage("§2Current command aliases:", false)
        modMessage("§3Open Configuration Menu: /" + GuiConfig.commandNames["goodmod"], false)
        modMessage("§3List Dungeon Drops: /" + GuiConfig.commandNames["getItems"], false)
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}