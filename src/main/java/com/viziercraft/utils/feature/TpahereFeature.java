package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.TeleportRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Handles /tpahere requests (bringing another player).
 */
public class TpahereFeature extends CommandFeature {

    public TpahereFeature(VizierUtilsPlugin plugin) {
        super(plugin, "tpahere", "TPAHere", "tpahere", "vizierutils.tpahere");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.tpahere-usage");
            return true;
        }
        Player requester = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        plugin.getTeleportRequestManager().createRequest(requester.getUniqueId(), target.getUniqueId(), TeleportRequestManager.Type.HERE);
        plugin.getMessages().sendMessage(requester, "commands.tpahere-sent", Map.of("%player%", target.getName()));
        plugin.getMessages().sendMessage(target, "commands.tpahere-received", Map.of("%player%", requester.getName()));
        return true;
    }
}
