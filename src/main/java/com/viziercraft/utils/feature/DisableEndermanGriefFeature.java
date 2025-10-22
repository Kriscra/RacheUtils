package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 * Prevents endermen from picking up blocks.
 */
public class DisableEndermanGriefFeature extends ListenerFeature {

    public DisableEndermanGriefFeature(VizierUtilsPlugin plugin) {
        super(plugin, "disable-enderman-grief", "Disable Enderman Grief");
    }

    @EventHandler
    public void onChange(EntityChangeBlockEvent event) {
        if (!isEnabled()) {
            return;
        }
        if (event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
