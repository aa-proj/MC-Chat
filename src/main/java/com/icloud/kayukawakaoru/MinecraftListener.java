package com.icloud.kayukawakaoru;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.BroadcastMessageEvent;

public class MinecraftListener implements Listener{
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        Bukkit.getServer().getLogger().info("ssssssss"+e.getMessage());
    }
}
