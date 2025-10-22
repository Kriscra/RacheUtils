package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.PlayerData;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Allows players to return to their previous location using /back.
 */
public class BackFeature extends CommandFeature implements Listener {

    public BackFeature(VizierUtilsPlugin plugin) {
        super(plugin, "back", "Back", "back", "vizierutils.back");
    }

    @Override
    public void enable() {
        super.enable();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(this);
        super.disable();
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        Location back = data.getBackLocation();
        if (back == null) {
            plugin.getMessages().sendMessage(player, "commands.back-none");
            return true;
        }
        data.setBackLocation(player.getLocation());
        player.teleport(back);
        plugin.getMessages().sendMessage(player, "commands.back");
        return true;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (!isEnabled()) {
            return;
        }
        PlayerData data = plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
        data.setBackLocation(event.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!isEnabled()) {
            return;
        }
        PlayerData data = plugin.getPlayerStorage().get(event.getEntity().getUniqueId());
        data.setBackLocation(event.getEntity().getLocation());
    }
}
