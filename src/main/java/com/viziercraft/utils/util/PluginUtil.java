package com.viziercraft.utils.util;

import org.bukkit.Bukkit;

/**
 * Helper utilities for interacting with other plugins.
 */
public final class PluginUtil {

    private PluginUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks whether PlaceholderAPI is available.
     *
     * @return true if the plugin is loaded
     */
    public static boolean hasPlaceholderApi() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
