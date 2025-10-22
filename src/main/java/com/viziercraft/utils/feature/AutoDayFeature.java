package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TaskUtil;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

/**
 * Periodically sets the time to day.
 */
public class AutoDayFeature extends ScheduledFeature {

    public AutoDayFeature(VizierUtilsPlugin plugin) {
        super(plugin, "auto-day", "Auto Day");
    }

    @Override
    protected BukkitTask startTask() {
        long intervalMinutes = plugin.getConfig().getLong("auto-day.interval-minutes", 10L);
        long ticks = Math.max(1, intervalMinutes) * 60L * 20L;
        return TaskUtil.runTimer(plugin, this::setDay, ticks, ticks);
    }

    private void setDay() {
        World world = plugin.getServer().getWorld(plugin.getConfig().getString("auto-day.world", "world"));
        if (world != null) {
            world.setTime(1000L);
        }
    }
}
