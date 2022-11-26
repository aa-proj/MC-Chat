package com.icloud.kayukawakaoru;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Mcchat extends JavaPlugin {

    public static JDA jda;
    private String token;
    private String channelID;
    private String webHookURL;
    private String kanrichanChannelName;
    private String kanrichanID;

    private static DiscordListener dListener;
    private static NewTpCommand tpCommand;
    private static MinecraftListener mListener;
    private static DiscordSender sender;

    @Override
    public void onEnable() {
        var logger = this.getServer().getLogger();
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        token = config.getString("Token", "");
        channelID = config.getString("Chat_ChannelID", "");
        webHookURL = config.getString("WebHookUrl","");
        kanrichanID = config.getString("Kanrichan_UserID", "");
        kanrichanChannelName = config.getString("Kanrichan_Name", "");


        dListener = new DiscordListener();
        dListener.setChannelID(channelID);

        if (token.equals("")) {
            logger.warning("Token not provided");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        } else try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException e) {
            logger.warning(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        jda.addEventListener(dListener);
        mListener = new MinecraftListener();
        mListener.setWebHookUrl(webHookURL);
        this.getServer().getPluginManager().registerEvents(mListener,this);
        //getCommand("tpa").setExecutor(new NewTpCommand());

        sender = new DiscordSender();
        sender.setJda(jda);
        sender.setKanrichanID(kanrichanID);
        sender.setChannelName(kanrichanChannelName);

        mListener.setDiscordSender(sender);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
