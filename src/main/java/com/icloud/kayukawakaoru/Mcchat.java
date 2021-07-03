package com.icloud.kayukawakaoru;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Mcchat extends JavaPlugin {

    public static JDA jda;

    private String token;
    private String channelID;
    private String webHookURL;

    private static DiscordListener dListener;

    private static MinecraftListener mListener;

    @Override
    public void onEnable() {
        var logger = this.getServer().getLogger();
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        token = config.getString("Token", "");
        channelID = config.getString("ChannelID", "");
        webHookURL = config.getString("WebHookUrl","");

        dListener = new DiscordListener();
        dListener.setChannelID(channelID);

        if (token.equals("")) {
            logger.warning("Token not provided");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        } else try {
            jda = JDABuilder.createDefault(token).addEventListeners(dListener).build();
        } catch (LoginException e) {
            logger.warning(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        mListener = new MinecraftListener();
        mListener.setWebHookUrl(webHookURL);
        this.getServer().getPluginManager().registerEvents(mListener,this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
