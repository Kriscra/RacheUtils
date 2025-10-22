package com.viziercraft.utils.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Convenience wrappers around the Bukkit scheduler.
 */
public final class TaskUtil {

    private TaskUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static BukkitTask runLater(JavaPlugin plugin, Runnable runnable, long delayTicks) {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delayTicks);
    }

    public static BukkitTask runTimer(JavaPlugin plugin, Runnable runnable, long delayTicks, long periodTicks) {
        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delayTicks, periodTicks);
    }

    public static BukkitTask runAsync(JavaPlugin plugin, Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}
