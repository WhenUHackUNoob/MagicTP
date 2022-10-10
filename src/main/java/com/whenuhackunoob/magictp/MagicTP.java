package com.whenuhackunoob.magictp;

import com.whenuhackunoob.magictp.commands.RtpCommand;
import com.whenuhackunoob.magictp.files.ConfigurationYAMLManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicTP extends JavaPlugin {

    public ConfigurationYAMLManager configuration;

    public static MagicTP instance;
    public static MagicTP get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        get().getLogger().info("[MagicTP] Loading MagicTP v" + getDescription().getVersion() + " ...");
        initializeYAMLManagers();
        initCommands();
        // events
        get().getLogger().info("[MagicTP] Successfully loaded MagicTP v" + getDescription().getVersion() + "!");
    }

    @Override
    public void onDisable() {
        get().getLogger().info("[MagicTP] Successfully shut down v" + getDescription().getVersion() + ".");
    }

    public void initCommands() {
        getCommand("rtp").setExecutor(new RtpCommand(this));
    }

    public void initializeYAMLManagers() {
        configuration = new ConfigurationYAMLManager(this);
    }
}
