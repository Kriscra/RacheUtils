package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Customises the server list MOTD.
 */
public class MotdFeature extends ListenerFeature {

    public MotdFeature(VizierUtilsPlugin plugin) {
        super(plugin, "motd", "MOTD");
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (!isEnabled()) {
            return;
        }
        String motd = plugin.getConfig().getString("motd.message", "&aVizierUtils Server");
        if (plugin.isPlaceholderApiEnabled()) {
            motd = PlaceholderAPI.setPlaceholders(null, motd);
        }
        event.setMotd(com.viziercraft.utils.util.TextUtil.color(motd));
    }
}
