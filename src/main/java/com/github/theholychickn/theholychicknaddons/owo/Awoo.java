package com.github.theholychickn.theholychicknaddons.owo;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Awoo {
    int chatCount = 0;
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        chatCount++;
        System.out.println("Chats recieved total: " + chatCount);
    }
}
