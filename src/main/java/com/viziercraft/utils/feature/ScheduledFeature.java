package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Feature that manages a repeating task while enabled.
 */
public abstract class ScheduledFeature extends AbstractFeature {

    private BukkitTask task;

    protected ScheduledFeature(VizierUtilsPlugin plugin, String key, String displayName) {
        super(plugin, key, displayName);
    }

    @Override
    public void enable() {
        if (task == null) {
            task = startTask();
        }
        setEnabled(true);
    }

    @Override
    public void disable() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        setEnabled(false);
    }

    /**
     * Starts the scheduled task.
     *
     * @return the running task
     */
    protected abstract BukkitTask startTask();
}
