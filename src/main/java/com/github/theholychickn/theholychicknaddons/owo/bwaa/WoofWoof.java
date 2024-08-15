package com.github.theholychickn.theholychicknaddons.owo.bwaa;

import com.github.theholychickn.theholychicknaddons.ExampleMod;
import com.github.theholychickn.theholychicknaddons.owo.Meower;
import com.github.theholychickn.theholychicknaddons.owo.Woof;
import com.github.theholychickn.theholychicknaddons.owo.test_gui;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.List;

public class WoofWoof extends CommandBase {

    @Override
    public String getCommandName() {
        return "nicepb";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        LogManager.getLogger("OwO").info("" +
                "");
        sender.addChatMessage(new ChatComponentText("Opening goodmod GUI"));
        ExampleMod.setDisplay(new Woof());
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
