package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Broadcasts a message to the server.
 */
public class BroadcastFeature extends CommandFeature {

    public BroadcastFeature(VizierUtilsPlugin plugin) {
        super(plugin, "broadcast", "Broadcast", "broadcast", "vizierutils.broadcast");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.broadcast-usage");
            return true;
        }
        String message = String.join(" ", args);
        Bukkit.broadcast(TextUtil.color(plugin.getMessages().getMessage("commands.broadcast-prefix") + message));
        return true;
    }
}
