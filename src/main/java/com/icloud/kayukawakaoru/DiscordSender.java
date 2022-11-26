package com.icloud.kayukawakaoru;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiscordSender {
    private String kanrichanID;
    private String channelName;
    private JDA jda;

    public void setJda(JDA _jda){
        jda = _jda;
    }

    public void setKanrichanID(String k){
        kanrichanID = k;
    }

    public void setChannelName(String c){
        channelName = c;
    }
    public void send(){
        List<TextChannel> c = jda.getTextChannelsByName(channelName,true);
        if(c.size()==0){
            //Bukkit.getLogger().warning("sakdfhwuigfhif");
            return;
        }
        for (TextChannel ch:c) {
            ch.sendMessage("<@"+kanrichanID+"> だれもいなくなってから、10分が経過しました。とじてください。").queue();
        }
    }
}
