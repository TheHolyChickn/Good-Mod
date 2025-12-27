package com.github.theholychicken.commands

import com.github.theholychicken.GoodMod
import com.github.theholychicken.config.GuiConfig
import com.github.theholychicken.managers.apiclients.CoflApiClient
import com.github.theholychicken.managers.apiclients.HypixelApiClient
import com.github.theholychicken.managers.apiclients.TrickedApiClient
import com.github.theholychicken.utils.modMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class UpdateAuctionsCommand : CommandBase() {
    override fun getCommandName(): String {
        return GuiConfig.commandNames["updateAuctions"] ?: "updateAuctions"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return ""
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        modMessage("Starting auction fetching protocol")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (GuiConfig.api) {
                    "HypixelApi" -> HypixelApiClient.fetchAllAuctions()
                    "CoflApi" -> CoflApiClient.fetchAllAuctions()
                    "TrickedApi" -> TrickedApiClient.fetchAllAuctions()
                    else -> modMessage("Could not figure out what API client you are using")
                }
            } catch (e: Exception) {
                modMessage("Error found, §4§lplease report this")
                GoodMod.logger.error(e.message)
            }
        }
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }

    override fun getCommandAliases(): List<String> {
        return listOf("goodmod:updateAuctions")
    }
}