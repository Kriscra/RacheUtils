package com.viziercraft.utils.storage;

import com.viziercraft.utils.VizierUtilsPlugin;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Handles loading and saving player data to disk.
 */
public class PlayerStorage {

    private final VizierUtilsPlugin plugin;
    private final Map<UUID, PlayerData> dataMap = new HashMap<>();
    private File file;
    private FileConfiguration configuration;

    public PlayerStorage(VizierUtilsPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        file = plugin.getDataFolder().toPath().resolve("playerdata.yml").toFile();
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create playerdata.yml", e);
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        dataMap.clear();
        ConfigurationSection section = configuration.getConfigurationSection("players");
        if (section == null) {
            return;
        }
        for (String key : section.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                PlayerData data = new PlayerData(uuid);
                ConfigurationSection playerSection = section.getConfigurationSection(key);
                if (playerSection == null) {
                    continue;
                }
                ConfigurationSection homesSection = playerSection.getConfigurationSection("homes");
                if (homesSection != null) {
                    for (String homeName : homesSection.getKeys(false)) {
                        Location home = LocationSerializer.deserialize(homesSection.getConfigurationSection(homeName));
                        if (home != null) {
                            data.setHome(homeName, home);
                        }
                    }
                }
                ConfigurationSection backSection = playerSection.getConfigurationSection("back");
                data.setBackLocation(LocationSerializer.deserialize(backSection));
                data.setVanished(playerSection.getBoolean("vanished", false));
                data.setFrozen(playerSection.getBoolean("frozen", false));
                data.setMuted(playerSection.getBoolean("muted", false));
                data.setChatSpy(playerSection.getBoolean("chatspy", false));
                dataMap.put(uuid, data);
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().log(Level.WARNING, "Ignoring invalid UUID in playerdata.yml: {0}", key);
            }
        }
    }

    public void save() {
        if (configuration == null || file == null) {
            return;
        }
        configuration.set("players", null);
        ConfigurationSection root = configuration.createSection("players");
        for (PlayerData data : dataMap.values()) {
            ConfigurationSection playerSection = root.createSection(data.getUniqueId().toString());
            ConfigurationSection homesSection = playerSection.createSection("homes");
            for (Map.Entry<String, Location> entry : data.getHomes().entrySet()) {
                ConfigurationSection homeSection = homesSection.createSection(entry.getKey());
                LocationSerializer.serialize(homeSection, entry.getValue());
            }
            if (data.getBackLocation() != null) {
                ConfigurationSection backSection = playerSection.createSection("back");
                LocationSerializer.serialize(backSection, data.getBackLocation());
            }
            playerSection.set("vanished", data.isVanished());
            playerSection.set("frozen", data.isFrozen());
            playerSection.set("muted", data.isMuted());
            playerSection.set("chatspy", data.isChatSpy());
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save playerdata.yml", e);
        }
    }

    public PlayerData get(UUID uuid) {
        return dataMap.computeIfAbsent(uuid, PlayerData::new);
    }

    public Optional<PlayerData> find(UUID uuid) {
        return Optional.ofNullable(dataMap.get(uuid));
    }

    public Collection<PlayerData> values() {
        return dataMap.values();
    }
}
