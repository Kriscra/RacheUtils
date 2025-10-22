package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TaskUtil;
import com.viziercraft.utils.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

/**
 * Broadcasts messages in rotation at configured intervals.
 */
public class AutoMessageFeature extends ScheduledFeature {

    private List<String> messages;
    private int index;

    public AutoMessageFeature(VizierUtilsPlugin plugin) {
        super(plugin, "auto-message", "Auto Message");
    }

    @Override
    public void enable() {
        this.messages = plugin.getConfig().getStringList("auto-message.messages");
        this.index = 0;
        super.enable();
    }

    @Override
    protected BukkitTask startTask() {
        long intervalSeconds = plugin.getConfig().getLong("auto-message.interval-seconds", 120L);
        long ticks = Math.max(20L, intervalSeconds * 20L);
        return TaskUtil.runTimer(plugin, this::broadcastNext, ticks, ticks);
    }

    private void broadcastNext() {
        if (messages == null || messages.isEmpty()) {
            return;
        }
        String message = messages.get(index % messages.size());
        index++;
        Bukkit.broadcast(TextUtil.color(message));
    }
}
