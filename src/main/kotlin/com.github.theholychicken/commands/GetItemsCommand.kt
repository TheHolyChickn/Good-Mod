package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.ItemDropParser
import com.github.theholychicken.managers.SellableItemParser
import com.github.theholychicken.utils.modMessage
import net.minecraft.command.*


class GetItemsCommand : CommandBase() {
    override fun getCommandName(): String {
        return GuiConfig.commandNames["getItems"] ?: "getItems"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return ""
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        SellableItemParser.shinyItems.forEach { name ->
            modMessage("§6$name§r: ${ItemDropParser.dropsConfig.getItemCount(name)}", false)
        }
        SellableItemParser.items.sortedByDescending { it.catalog }.forEach { item ->
            val count = ItemDropParser.dropsConfig.getItemCount(item.name)
            modMessage("${item.displayName}§r: $count", false)
        }
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }

    override fun getCommandAliases(): List<String> {
        return listOf("goodmod:owo")
    }
}