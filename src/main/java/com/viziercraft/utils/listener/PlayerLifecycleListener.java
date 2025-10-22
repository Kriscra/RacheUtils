package com.viziercraft.utils.listener;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Ensures player data is initialised when they join and persists any pending changes on quit.
 */
public class PlayerLifecycleListener implements Listener {

    private final VizierUtilsPlugin plugin;

    public PlayerLifecycleListener(VizierUtilsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
    }
}
