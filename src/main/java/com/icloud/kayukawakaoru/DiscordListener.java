package com.icloud.kayukawakaoru;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordListener extends ListenerAdapter {
    private String channelID;

    public void setChannelID(String id) {
        channelID = id;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getChannel().getId().equals(channelID)) return;
        Bukkit.getServer().getLogger().info(event.getMessage().getContentRaw());
    }
}
