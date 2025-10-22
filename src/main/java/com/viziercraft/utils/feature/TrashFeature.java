package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Opens a disposable inventory to throw items away.
 */
public class TrashFeature extends CommandFeature {

    public TrashFeature(VizierUtilsPlugin plugin) {
        super(plugin, "trash", "Trash", "trash", "vizierutils.trash");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getMessages().getMessage("inventories.trash-title"));
        player.openInventory(inventory);
        plugin.getMessages().sendMessage(player, "commands.trash");
        return true;
    }
}
