package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Stops creeper explosions from damaging blocks.
 */
public class NoCreeperGriefFeature extends ListenerFeature {

    public NoCreeperGriefFeature(VizierUtilsPlugin plugin) {
        super(plugin, "no-creeper-grief", "No Creeper Grief");
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (!isEnabled()) {
            return;
        }
        if (event.getEntityType() == EntityType.CREEPER) {
            event.blockList().clear();
        }
    }
}
