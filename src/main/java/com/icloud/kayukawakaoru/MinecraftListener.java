package com.icloud.kayukawakaoru;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.BroadcastMessageEvent;

public class MinecraftListener implements Listener{
    private JDA jda;
    private String channelID;
    private String webHookUrl;

    public void setJDA(JDA _jda){
        jda = _jda;
    }
    public void setChannelID(String id){
        channelID = id;
    }
    public  void setWebHookUrl(String _url){
        webHookUrl = _url;
    }
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        TextChannel channel = jda.getTextChannelById(channelID);
        //channel.sendMessage(createEmbed(e.getPlayer().getDisplayName(),e.getMessage())).queue();
        send(webHookUrl,e.getPlayer().getDisplayName(),e.getMessage(),e.getPlayer().getUniqueId().toString());
        Bukkit.getServer().getLogger().info(e.getMessage());
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
