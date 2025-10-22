package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TaskUtil;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

/**
 * Automatically clears weather at intervals.
 */
public class AutoClearWeatherFeature extends ScheduledFeature {

    public AutoClearWeatherFeature(VizierUtilsPlugin plugin) {
        super(plugin, "auto-clear-weather", "Auto Clear Weather");
    }

    @Override
    protected BukkitTask startTask() {
        long intervalMinutes = plugin.getConfig().getLong("auto-clear-weather.interval-minutes", 10L);
        long ticks = Math.max(1, intervalMinutes) * 60L * 20L;
        return TaskUtil.runTimer(plugin, this::clearWeather, ticks, ticks);
    }

    private void clearWeather() {
        World world = plugin.getServer().getWorld(plugin.getConfig().getString("auto-clear-weather.world", "world"));
        if (world != null) {
            world.setStorm(false);
            world.setThundering(false);
        }
    }
}
