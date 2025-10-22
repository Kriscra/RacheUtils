package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Map;

/**
 * Implements /tempban <player> <duration> [reason].
 */
public class TempBanFeature extends CommandFeature {

    public TempBanFeature(VizierUtilsPlugin plugin) {
        super(plugin, "tempban", "TempBan", "tempban", "vizierutils.tempban");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            plugin.getMessages().sendMessage(sender, "commands.tempban-usage");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null || target.getName() == null) {
            plugin.getMessages().sendMessage(sender, "player-not-found");
            return true;
        }
        Duration duration = parseDuration(args[1]);
        if (duration == null) {
            plugin.getMessages().sendMessage(sender, "commands.tempban-invalid");
            return true;
        }
        String reason = args.length >= 3 ? String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length)) : plugin.getMessages().getMessage("commands.tempban-default-reason");
        long expires = System.currentTimeMillis() + duration.toMillis();
        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, new java.util.Date(expires), sender.getName());
        if (target.isOnline()) {
            target.getPlayer().kick(net.kyori.adventure.text.Component.text(reason));
        }
        Map<String, String> replacements = Map.of(
                "%player%", target.getName(),
                "%reason%", reason,
                "%duration%", formatDuration(duration)
        );
        plugin.getMessages().sendMessage(sender, "commands.tempban", replacements);
        return true;
    }

    private Duration parseDuration(String input) {
        try {
            long number = Long.parseLong(input.substring(0, input.length() - 1));
            char unit = Character.toLowerCase(input.charAt(input.length() - 1));
            return switch (unit) {
                case 's' -> Duration.ofSeconds(number);
                case 'm' -> Duration.ofMinutes(number);
                case 'h' -> Duration.ofHours(number);
                case 'd' -> Duration.ofDays(number);
                default -> null;
            };
        } catch (Exception ex) {
            return null;
        }
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "s";
        }
        long minutes = seconds / 60;
        if (minutes < 60) {
            return minutes + "m";
        }
        long hours = minutes / 60;
        if (hours < 24) {
            return hours + "h";
        }
        long days = hours / 24;
        return days + "d";
    }
}
