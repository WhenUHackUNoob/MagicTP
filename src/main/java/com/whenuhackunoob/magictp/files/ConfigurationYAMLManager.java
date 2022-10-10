package com.whenuhackunoob.magictp.files;

import com.whenuhackunoob.magictp.MagicTP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigurationYAMLManager {

    private final MagicTP plugin;

    public ConfigurationYAMLManager(MagicTP plugin) {
        this.plugin = plugin;
        saveDefaultConfiguration();
    }

    private FileConfiguration configuration;
    private File configurationFile;

    public void reloadConfiguration() {
        if(configurationFile == null) configurationFile = new File(plugin.getDataFolder(), "configuration.yml");

        configuration = YamlConfiguration.loadConfiguration(configurationFile);
        InputStream defaultStream = plugin.getResource("configuration.yml");

        if(defaultStream != null) {
            YamlConfiguration defaultConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            configuration.setDefaults(defaultConfiguration);
        }
    }

    public FileConfiguration getConfiguration() {
        if(configuration == null) reloadConfiguration();
        return configuration;
    }

    public File getConfigurationFile() {
        if(!configurationFile.exists()) return null;
        return configurationFile;
    }

    public void saveConfiguration() {
        if(configuration == null || configurationFile == null) return;

        try {
            getConfiguration().save(configurationFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfiguration() {
        if(configurationFile == null) configurationFile = new File(plugin.getDataFolder(), "configuration.yml");

        if(!configurationFile.exists()) {
            plugin.saveResource("configuration.yml", false);
        }
    }

}
