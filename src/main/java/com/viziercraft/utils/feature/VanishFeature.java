package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles vanish toggling using /vanish.
 */
public class VanishFeature extends CommandFeature implements Listener {

    public VanishFeature(VizierUtilsPlugin plugin) {
        super(plugin, "vanish", "Vanish", "vanish", "vizierutils.vanish");
    }

    @Override
    public void enable() {
        super.enable();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        // Restore vanish state for online players when enabling mid-session
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
            if (data.isVanished()) {
                applyVanish(player, true);
            }
        }
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            showPlayer(player);
        }
        super.disable();
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        boolean vanished = !data.isVanished();
        data.setVanished(vanished);
        applyVanish(player, vanished);
        plugin.getMessages().sendMessage(player, vanished ? "commands.vanish-on" : "commands.vanish-off");
        return true;
    }

    private void applyVanish(Player player, boolean vanished) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.equals(player)) {
                continue;
            }
            if (vanished) {
                if (!other.hasPermission("vizierutils.vanish.see")) {
                    other.hidePlayer(plugin, player);
                }
            } else {
                other.showPlayer(plugin, player);
            }
        }
        player.setInvisible(vanished);
    }

    private void showPlayer(Player player) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            other.showPlayer(plugin, player);
        }
        player.setInvisible(false);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!isEnabled()) {
            return;
        }
        Player joined = event.getPlayer();
        PlayerData joinedData = plugin.getPlayerStorage().get(joined.getUniqueId());
        if (joinedData.isVanished()) {
            applyVanish(joined, true);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.equals(joined)) {
                continue;
            }
            PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
            if (data.isVanished() && !joined.hasPermission("vizierutils.vanish.see")) {
                joined.hidePlayer(plugin, player);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!isEnabled()) {
            return;
        }
        PlayerData data = plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
        if (data.isVanished()) {
            showPlayer(event.getPlayer());
        }
    }
}
