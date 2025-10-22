package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Prevents phantoms from spawning.
 */
public class AntiPhantomFeature extends ListenerFeature {

    public AntiPhantomFeature(VizierUtilsPlugin plugin) {
        super(plugin, "anti-phantom", "Anti Phantom");
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (!isEnabled()) {
            return;
        }
        if (event.getEntityType() == EntityType.PHANTOM) {
            event.setCancelled(true);
        }
    }
}
