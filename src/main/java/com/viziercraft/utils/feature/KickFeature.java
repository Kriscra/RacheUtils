package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Provides /kick command with message support from messages.yml.
 */
public class KickFeature extends CommandFeature {

    public KickFeature(VizierUtilsPlugin plugin) {
        super(plugin, "kick", "Kick", "kick", "vizierutils.kick");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.kick-usage");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        String reason = args.length >= 2 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : plugin.getMessages().getMessage("commands.kick-default-reason");
        target.kick(net.kyori.adventure.text.Component.text(reason));
        plugin.getMessages().sendMessage(sender, "commands.kick", Map.of("%player%", target.getName(), "%reason%", reason));
        return true;
    }
}
