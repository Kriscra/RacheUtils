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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;

/**
 * Freezes players in place using /freeze <player>.
 */
public class FreezeFeature extends CommandFeature implements Listener {

    public FreezeFeature(VizierUtilsPlugin plugin) {
        super(plugin, "freeze", "Freeze", "freeze", "vizierutils.freeze");
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
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.freeze-usage");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        PlayerData data = plugin.getPlayerStorage().get(target.getUniqueId());
        boolean frozen = !data.isFrozen();
        data.setFrozen(frozen);
        Map<String, String> replacements = Map.of("%player%", target.getName());
        plugin.getMessages().sendMessage(sender, frozen ? "commands.freeze-on" : "commands.freeze-off", replacements);
        plugin.getMessages().sendMessage(target, frozen ? "commands.freeze-you-on" : "commands.freeze-you-off");
        return true;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!isEnabled()) {
            return;
        }
        PlayerData data = plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
        if (data.isFrozen() && event.getFrom().distanceSquared(event.getTo()) > 0.01) {
            event.setTo(event.getFrom());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!isEnabled()) {
            return;
        }
        PlayerData data = plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
        if (data.isFrozen()) {
            event.setCancelled(true);
        }
    }
}
