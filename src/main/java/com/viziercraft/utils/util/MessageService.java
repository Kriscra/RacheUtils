package com.viziercraft.utils.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Provides formatted access to values inside messages.yml.
 */
public class MessageService {

    private final JavaPlugin plugin;
    private FileConfiguration messages;
    private final Map<String, String> cache = new HashMap<>();

    public MessageService(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    /**
     * Reloads the backing messages configuration file.
     */
    public final void reload() {
        cache.clear();
        try {
            plugin.saveResource("messages.yml", false);
        } catch (IllegalArgumentException ignored) {
            // already exists
        }
        messages = YamlConfiguration.loadConfiguration(plugin.getDataFolder().toPath().resolve("messages.yml").toFile());
    }

    /**
     * Fetches a message by path.
     *
     * @param path config path
     * @return coloured message string
     */
    public String getMessage(String path) {
        return cache.computeIfAbsent(path, key -> TextUtil.color(messages.getString(key, "")));
    }

    /**
     * Sends a formatted message to the target.
     *
     * @param sender command sender
     * @param path message path
     * @param replacements replacements map
     */
    public void sendMessage(CommandSender sender, String path, Map<String, String> replacements) {
        String message = getMessage(path);
        if (message.isEmpty()) {
            return;
        }
        if (replacements != null) {
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
        }
        sender.sendMessage(message);
    }

    public void sendMessage(CommandSender sender, String path) {
        sendMessage(sender, path, null);
    }

    public void log(Level level, String path) {
        plugin.getLogger().log(level, getMessage(path));
    }
}
