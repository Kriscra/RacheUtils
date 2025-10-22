package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Feature that registers a listener while active.
 */
public abstract class ListenerFeature extends AbstractFeature implements Listener {

    protected ListenerFeature(VizierUtilsPlugin plugin, String key, String displayName) {
        super(plugin, key, displayName);
    }

    @Override
    public void enable() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        setEnabled(true);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(this);
        setEnabled(false);
    }
}
