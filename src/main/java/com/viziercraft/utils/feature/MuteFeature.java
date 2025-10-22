package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;

/**
 * Toggles mute state for players via /mute <player>.
 */
public class MuteFeature extends CommandFeature implements Listener {

    public MuteFeature(VizierUtilsPlugin plugin) {
        super(plugin, "mute", "Mute", "mute", "vizierutils.mute");
    }

    @Override
    public void enable() {
        super.enable();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(this);
        super.disable();
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "commands.mute-usage");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        PlayerData data = plugin.getPlayerStorage().get(target.getUniqueId());
        boolean muted = !data.isMuted();
        data.setMuted(muted);
        plugin.getMessages().sendMessage(sender, muted ? "commands.mute-on" : "commands.mute-off", Map.of("%player%", target.getName()));
        plugin.getMessages().sendMessage(target, muted ? "commands.mute-you-on" : "commands.mute-you-off");
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!isEnabled()) {
            return;
        }
        PlayerData data = plugin.getPlayerStorage().get(event.getPlayer().getUniqueId());
        if (data.isMuted()) {
            event.setCancelled(true);
            plugin.getMessages().sendMessage(event.getPlayer(), "commands.mute-muted");
        }
    }
}
