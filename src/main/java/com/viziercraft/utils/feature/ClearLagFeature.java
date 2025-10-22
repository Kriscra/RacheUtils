package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TaskUtil;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitTask;

/**
 * Periodically removes dropped items to reduce lag.
 */
public class ClearLagFeature extends ScheduledFeature {

    public ClearLagFeature(VizierUtilsPlugin plugin) {
        super(plugin, "clear-lag", "ClearLag");
    }

    @Override
    protected BukkitTask startTask() {
        long intervalSeconds = plugin.getConfig().getLong("clear-lag.interval-seconds", 180L);
        long ticks = Math.max(20L, intervalSeconds * 20L);
        return TaskUtil.runTimer(plugin, this::runCleanup, ticks, ticks);
    }

    private void runCleanup() {
        int removed = 0;
        for (World world : plugin.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                    removed++;
                }
            }
        }
        plugin.getServer().broadcastMessage(plugin.getMessages().getMessage("clearlag.broadcast").replace("%amount%", String.valueOf(removed)));
    }
}
