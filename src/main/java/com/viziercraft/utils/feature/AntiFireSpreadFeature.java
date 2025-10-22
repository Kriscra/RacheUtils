package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;

/**
 * Prevents fire from spreading across blocks.
 */
public class AntiFireSpreadFeature extends ListenerFeature {

    public AntiFireSpreadFeature(VizierUtilsPlugin plugin) {
        super(plugin, "anti-fire-spread", "Anti Fire Spread");
    }

    @EventHandler
    public void onSpread(BlockSpreadEvent event) {
        if (!isEnabled()) {
            return;
        }
        if (event.getSource().getType() == Material.FIRE || event.getSource().getType() == Material.SOUL_FIRE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        if (!isEnabled()) {
            return;
        }
        if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            event.setCancelled(true);
        }
    }
}
