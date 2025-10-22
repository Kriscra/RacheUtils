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
 * Allows staff members to toggle chat spy mode.
 */
public class ChatSpyFeature extends CommandFeature implements Listener {

    public ChatSpyFeature(VizierUtilsPlugin plugin) {
        super(plugin, "chatspy", "ChatSpy", "chatspy", "vizierutils.chatspy");
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
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        boolean enabled = !data.isChatSpy();
        data.setChatSpy(enabled);
        plugin.getMessages().sendMessage(player, enabled ? "commands.chatspy-on" : "commands.chatspy-off");
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!isEnabled()) {
            return;
        }
        String formatted = plugin.getMessages().getMessage("commands.chatspy-format")
                .replace("%player%", event.getPlayer().getName())
                .replace("%message%", event.getMessage());
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
            if (data.isChatSpy() && !player.equals(event.getPlayer())) {
                player.sendMessage(formatted);
            }
        }
    }
}
