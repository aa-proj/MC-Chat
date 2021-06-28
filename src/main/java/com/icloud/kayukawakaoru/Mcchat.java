package com.icloud.kayukawakaoru;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Mcchat extends JavaPlugin {

    public static JDA jda;

    private String token;

    @Override
    public void onEnable() {


        saveDefaultConfig();
        FileConfiguration config = getConfig();

        token = config.getString("Token","");

        // Plugin startup logic
        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException e) {
            this.getServer().getLogger().warning(e.getMessage());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
