package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Opens a crafting table for the player.
 */
public class WorkbenchFeature extends CommandFeature {

    public WorkbenchFeature(VizierUtilsPlugin plugin) {
        super(plugin, "workbench", "Workbench", "workbench", "vizierutils.workbench");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        player.openWorkbench(player.getLocation(), true);
        plugin.getMessages().sendMessage(player, "commands.workbench");
        return true;
    }
}
