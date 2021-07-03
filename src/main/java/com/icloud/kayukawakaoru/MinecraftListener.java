package com.icloud.kayukawakaoru;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MinecraftListener implements Listener{
    private String webHookUrl;
    public  void setWebHookUrl(String _url){
        webHookUrl = _url;
    }
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        //channel.sendMessage(createEmbed(e.getPlayer().getDisplayName(),e.getMessage())).queue();
        send(webHookUrl,e.getPlayer().getDisplayName(),e.getMessage(),e.getPlayer().getUniqueId().toString());
        //Bukkit.getServer().getLogger().info(e.getMessage());
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e){
        send(webHookUrl,e.getName(),e.getName()+"がゲームに参加しました",e.getUniqueId().toString());
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent e){
        send(webHookUrl,e.getPlayer().getName(),e.getPlayer().getName()+"がゲームから退出しました",e.getPlayer().getUniqueId().toString());
    }

    public void send(String url,String name,String context,String uuid){
        WebhookClientBuilder builder = new WebhookClientBuilder(url);
        builder.setThreadFactory(
                (job)->{Thread thread = new Thread(job);
                    thread.setName("WebHook");
                    return thread;
                });
        builder.setWait(true);
        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        messageBuilder.setUsername(name); // use this username
        messageBuilder.setAvatarUrl("https://mc-heads.net/avatar/"+uuid); // use this avatar
        messageBuilder.setContent(context);
        WebhookClient client = builder.build();
        client.send(messageBuilder.build());
        client.close();
    }
}
