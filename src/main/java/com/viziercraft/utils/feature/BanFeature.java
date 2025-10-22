package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * Implements /ban <player> [reason].
 */
public class BanFeature extends CommandFeature {

    public BanFeature(VizierUtilsPlugin plugin) {
        super(plugin, "ban", "Ban", "ban", "vizierutils.ban");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.ban-usage");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null || target.getName() == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        String reason = args.length >= 2 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : plugin.getMessages().getMessage("commands.ban-default-reason");
        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, sender.getName());
        if (target.isOnline()) {
            target.getPlayer().kick(net.kyori.adventure.text.Component.text(reason));
        }
        plugin.getMessages().sendMessage(sender, "commands.ban", Map.of("%player%", target.getName(), "%reason%", reason));
        return true;
    }
}
