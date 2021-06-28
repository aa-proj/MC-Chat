package com.icloud.kayukawakaoru;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Mcchat extends JavaPlugin {

    public static JDA jda;

    private String token;
    private String ChannelID;

    private static DiscordListener listener;

    @Override
    public void onEnable() {
        var logger = this.getServer().getLogger();
        this.getServer().getPluginManager().registerEvents(new MinecraftListener(),this);
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        token = config.getString("Token", "");
        ChannelID = config.getString("ChannelID", "");
        listener = new DiscordListener();
        listener.setChannelID(ChannelID);
        if (token == "") {
            logger.warning("Token not provided");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        } else try {
            jda = JDABuilder.createDefault(token).addEventListeners(listener).build();
        } catch (LoginException e) {
            logger.warning(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
