package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.storage.PlayerData;
import com.viziercraft.utils.util.TextUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.title.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;

/**
 * Customises join/quit messaging and welcome titles.
 */
public class JoinQuitFeature extends ListenerFeature {

    public JoinQuitFeature(VizierUtilsPlugin plugin) {
        super(plugin, "join-quit", "Join/Quit Messages");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!isEnabled()) {
            return;
        }
        org.bukkit.entity.Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        if (data.isVanished()) {
            event.joinMessage(null);
            return;
        }
        String message = plugin.getConfig().getString("join-quit.join", "&a%player% joined the server");
        message = applyPlaceholders(player, message);
        event.joinMessage(TextUtil.color(message));
        if (plugin.getConfig().getBoolean("join-quit.send-welcome-title", true)) {
            String title = applyPlaceholders(player, plugin.getConfig().getString("join-quit.title", "&aHoş geldin, %player%!"));
            String subtitle = applyPlaceholders(player, plugin.getConfig().getString("join-quit.subtitle", "&7İyi oyunlar"));
            player.showTitle(Title.title(net.kyori.adventure.text.Component.text(TextUtil.color(title)), net.kyori.adventure.text.Component.text(TextUtil.color(subtitle)), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))));
        }
        plugin.getMessages().sendMessage(player, "join-quit.welcome");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!isEnabled()) {
            return;
        }
        org.bukkit.entity.Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerStorage().get(player.getUniqueId());
        if (data.isVanished()) {
            event.quitMessage(null);
            return;
        }
        String message = plugin.getConfig().getString("join-quit.quit", "&c%player% left the server");
        message = applyPlaceholders(player, message);
        event.quitMessage(TextUtil.color(message));
    }

    private String applyPlaceholders(org.bukkit.entity.Player player, String message) {
        if (message == null) {
            return "";
        }
        message = message.replace("%player%", player.getName());
        if (plugin.isPlaceholderApiEnabled()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }
}
