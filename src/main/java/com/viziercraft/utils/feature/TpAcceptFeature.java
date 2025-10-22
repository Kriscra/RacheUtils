package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.TeleportRequestManager;
import com.viziercraft.utils.storage.TeleportRequestManager.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Allows players to accept pending teleport requests.
 */
public class TpAcceptFeature extends CommandFeature {

    public TpAcceptFeature(VizierUtilsPlugin plugin) {
        super(plugin, "tpaccept", "TPAccept", "tpaccept", "vizierutils.tpaccept");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player target = (Player) sender;
        TeleportRequest request = plugin.getTeleportRequestManager().removeRequest(target.getUniqueId()).orElse(null);
        if (request == null) {
            plugin.getMessages().sendMessage(target, "commands.tpa-none");
            return true;
        }
        Player requester = Bukkit.getPlayer(request.requester());
        if (requester == null) {
            plugin.getMessages().sendMessage(target, "commands.tpa-requester-offline");
            return true;
        }
        switch (request.type()) {
            case TO_TARGET -> requester.teleport(target);
            case HERE -> target.teleport(requester);
        }
        plugin.getMessages().sendMessage(target, "commands.tpa-accepted");
        plugin.getMessages().sendMessage(requester, "commands.tpa-accepted-notify", java.util.Map.of("%player%", target.getName()));
        return true;
    }
}
