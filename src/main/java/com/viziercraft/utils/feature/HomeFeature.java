package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.PlayerData;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleports a player to one of their homes.
 */
public class HomeFeature extends CommandFeature {

    public HomeFeature(VizierUtilsPlugin plugin) {
        super(plugin, "home", "Home", "home", "vizierutils.home");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        String name = args.length == 0 ? "home" : args[0];
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        Location home = data.getHome(name);
        if (home == null) {
            plugin.getMessages().sendMessage(player, "commands.home-missing");
            return true;
        }
        data.setBackLocation(player.getLocation());
        player.teleport(home);
        plugin.getMessages().sendMessage(player, "commands.home");
        return true;
    }

    @Override
    protected java.util.List<String> tabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player player)) {
            return java.util.List.of();
        }
        if (args.length == 1) {
            PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
            return data.getHomes().keySet().stream()
                    .filter(name -> name.startsWith(args[0].toLowerCase()))
                    .sorted()
                    .toList();
        }
        return java.util.List.of();
    }
}
