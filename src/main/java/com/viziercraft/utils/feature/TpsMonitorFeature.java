package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TaskUtil;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

/**
 * Monitors TPS and logs warnings when the value drops below a threshold.
 */
public class TpsMonitorFeature extends ScheduledFeature {

    public TpsMonitorFeature(VizierUtilsPlugin plugin) {
        super(plugin, "tps-monitor", "TPS Monitor");
    }

    @Override
    protected BukkitTask startTask() {
        long intervalSeconds = plugin.getConfig().getLong("tps-monitor.interval-seconds", 60L);
        long ticks = Math.max(20L, intervalSeconds * 20L);
        return TaskUtil.runTimer(plugin, this::checkTps, ticks, ticks);
    }

    private void checkTps() {
        double threshold = plugin.getConfig().getDouble("tps-monitor.threshold", 18.5D);
        try {
            double[] tps = plugin.getServer().getTPS();
            if (tps.length > 0 && tps[0] < threshold) {
                plugin.getLogger().log(Level.WARNING, plugin.getMessages().getMessage("tps-monitor.warning").replace("%tps%", String.format("%.2f", tps[0])));
            }
        } catch (NoSuchMethodError error) {
            plugin.getLogger().log(Level.WARNING, "Server implementation does not expose getTPS(), skipping TPS monitor.");
            disable();
        }
    }
}
