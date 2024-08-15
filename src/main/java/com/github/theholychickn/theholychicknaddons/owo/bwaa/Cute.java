package com.github.theholychickn.theholychicknaddons.owo.bwaa;

import com.github.theholychickn.theholychicknaddons.owo.Meower;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.LogManager;

public class Cute extends CommandBase {
    @Override
    public String getCommandName() {
        return "goodmod:commands";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        LogManager.getLogger("Cute").info("Running cute rn");
        sender.addChatMessage(new ChatComponentText("§2Current command aliases:"));
        sender.addChatMessage(new ChatComponentText("§3nicepb: " + Meower.meow.nicepbCommand));
        sender.addChatMessage(new ChatComponentText("§3owo: " + Meower.meow.owoCommand));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
