package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Allows players to set a home location.
 */
public class SetHomeFeature extends CommandFeature {

    public SetHomeFeature(VizierUtilsPlugin plugin) {
        super(plugin, "sethome", "SetHome", "sethome", "vizierutils.sethome");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        String name = args.length == 0 ? "home" : args[0];
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        data.setHome(name, player.getLocation());
        plugin.getMessages().sendMessage(player, "commands.sethome");
        return true;
    }
}
