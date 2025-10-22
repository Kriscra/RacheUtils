package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Restores hunger for /feed.
 */
public class FeedFeature extends CommandFeature {

    public FeedFeature(VizierUtilsPlugin plugin) {
        super(plugin, "feed", "Feed", "feed", "vizierutils.feed");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        player.setFoodLevel(20);
        player.setSaturation(20f);
        plugin.getMessages().sendMessage(player, "commands.feed");
        return true;
    }
}
