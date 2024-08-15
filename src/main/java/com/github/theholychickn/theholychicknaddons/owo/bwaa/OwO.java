package com.github.theholychickn.theholychicknaddons.owo.bwaa;

import com.github.theholychickn.theholychicknaddons.owo.Awoo;
import com.github.theholychickn.theholychicknaddons.owo.Meower;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OwO extends CommandBase {

    @Override
    public String getCommandName() {
        return Meower.meow.owoCommand;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        LogManager.getLogger("OwO").info("owowowowowowowowo");
        Map<String, String> parseAwooo = Awoo.getAwooo();
        for (String item : parseAwooo.values()) {
            IChatComponent meow = new ChatComponentText(item + String.valueOf(Awoo.getAwA().getItemCount(item)));
            sender.addChatMessage(meow);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("goodmod:owo");
    }
}
