package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Feature backed by a command executor.
 */
public abstract class CommandFeature extends AbstractFeature implements CommandExecutor, TabCompleter {

    private final String commandName;
    private final String permission;

    protected CommandFeature(VizierUtilsPlugin plugin, String key, String displayName, String commandName, String permission) {
        super(plugin, key, displayName);
        this.commandName = commandName;
        this.permission = permission;
    }

    @Override
    public void enable() {
        if (plugin.getCommand(commandName) != null) {
            plugin.getCommand(commandName).setExecutor(this);
            plugin.getCommand(commandName).setTabCompleter(this);
        }
        setEnabled(true);
    }

    @Override
    public void disable() {
        setEnabled(false);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isEnabled()) {
            plugin.getMessages().sendMessage(sender, "feature-disabled");
            return true;
        }
        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            plugin.getMessages().sendMessage(sender, "no-permission");
            return true;
        }
        return handleCommand(sender, command, label, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!isEnabled()) {
            return Collections.emptyList();
        }
        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            return Collections.emptyList();
        }
        return tabComplete(sender, command, alias, args);
    }

    protected boolean ensurePlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            plugin.getMessages().sendMessage(sender, "player-only");
            return false;
        }
        return true;
    }

    protected abstract boolean handleCommand(CommandSender sender, Command command, String label, String[] args);

    protected List<String> tabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
