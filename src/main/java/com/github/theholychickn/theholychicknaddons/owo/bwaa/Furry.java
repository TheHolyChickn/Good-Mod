package com.github.theholychickn.theholychicknaddons.owo.bwaa;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import com.github.theholychickn.theholychicknaddons.owo.Awoo;
import com.github.theholychickn.theholychicknaddons.owo.Meower;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.logging.LogManager;

public class Furry extends CommandBase {

    @Override
    public String getCommandName() {
        return Meower.meow.furryCommand;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return ":3";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        GoodMod.Kitten.info("currently meowing");
        Awoo.reloadAwA();
        IChatComponent meow = new ChatComponentText("Dungeon drops successfully reloaded");
        sender.addChatMessage(meow);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
