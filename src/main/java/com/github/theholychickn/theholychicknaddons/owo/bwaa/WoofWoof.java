package com.github.theholychickn.theholychicknaddons.owo.bwaa;

import com.github.theholychickn.theholychicknaddons.GoodMod;
import com.github.theholychickn.theholychicknaddons.owo.Meower;
import com.github.theholychickn.theholychicknaddons.owo.Woof;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.List;

public class WoofWoof extends CommandBase {

    @Override
    public String getCommandName() {
        return Meower.meow.nicepbCommand;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        LogManager.getLogger("OwO").info("");
        GoodMod.setDisplay(new Woof());
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("goodmod:nicepb");
    }
}
