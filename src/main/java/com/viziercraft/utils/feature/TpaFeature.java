package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.TeleportRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Handles /tpa requests (player teleporting to another player).
 */
public class TpaFeature extends CommandFeature {

    public TpaFeature(VizierUtilsPlugin plugin) {
        super(plugin, "tpa", "TPA", "tpa", "vizierutils.tpa");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.tpa-usage");
            return true;
        }
        Player requester = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        plugin.getTeleportRequestManager().createRequest(requester.getUniqueId(), target.getUniqueId(), TeleportRequestManager.Type.TO_TARGET);
        Map<String, String> replacements = Map.of("%player%", target.getName());
        plugin.getMessages().sendMessage(requester, "commands.tpa-sent", replacements);
        plugin.getMessages().sendMessage(target, "commands.tpa-received", Map.of("%player%", requester.getName()));
        return true;
    }
}
