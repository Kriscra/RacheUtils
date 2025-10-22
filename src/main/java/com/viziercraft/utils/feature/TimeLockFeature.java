package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TaskUtil;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

/**
 * Keeps the world time locked to a configured value.
 */
public class TimeLockFeature extends ScheduledFeature {

    public TimeLockFeature(VizierUtilsPlugin plugin) {
        super(plugin, "time-lock", "Time Lock");
    }

    @Override
    protected BukkitTask startTask() {
        long interval = plugin.getConfig().getLong("time-lock.interval", 200L);
        return TaskUtil.runTimer(plugin, this::applyTime, 0L, interval);
    }

    private void applyTime() {
        World world = plugin.getServer().getWorld(plugin.getConfig().getString("time-lock.world", "world"));
        if (world == null) {
            return;
        }
        String configValue = plugin.getConfig().getString("time-lock.time", "DAY").toUpperCase();
        long time;
        switch (configValue) {
            case "DAY" -> time = 1000L;
            case "NIGHT" -> time = 13000L;
            default -> {
                try {
                    time = Long.parseLong(configValue);
                } catch (NumberFormatException ex) {
                    time = 1000L;
                }
            }
        }
        world.setTime(time);
    }
}
