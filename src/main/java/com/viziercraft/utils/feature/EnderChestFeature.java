package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Opens the player's ender chest.
 */
public class EnderChestFeature extends CommandFeature {

    public EnderChestFeature(VizierUtilsPlugin plugin) {
        super(plugin, "enderchest", "EnderChest", "enderchest", "vizierutils.enderchest");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(player.getEnderChest());
        plugin.getMessages().sendMessage(player, "commands.enderchest");
        return true;
    }
}
