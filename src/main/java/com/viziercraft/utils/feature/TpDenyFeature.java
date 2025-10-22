package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.TeleportRequestManager.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Allows players to deny pending teleport requests.
 */
public class TpDenyFeature extends CommandFeature {

    public TpDenyFeature(VizierUtilsPlugin plugin) {
        super(plugin, "tpdeny", "TPDeny", "tpdeny", "vizierutils.tpdeny");
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
        plugin.getMessages().sendMessage(target, "commands.tpa-denied");
        if (requester != null) {
            plugin.getMessages().sendMessage(requester, "commands.tpa-denied-notify", java.util.Map.of("%player%", target.getName()));
        }
        return true;
    }
}
