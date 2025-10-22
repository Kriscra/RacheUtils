package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Allows administrators to inspect other players' inventories.
 */
public class InvseeFeature extends CommandFeature {

    public InvseeFeature(VizierUtilsPlugin plugin) {
        super(plugin, "invsee", "Invsee", "invsee", "vizierutils.invsee");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            plugin.getMessages().sendMessage(sender, "player-only");
            return true;
        }
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.invsee-usage");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        player.openInventory(target.getInventory());
        plugin.getMessages().sendMessage(sender, "commands.invsee", Map.of("%player%", target.getName()));
        return true;
    }
}
