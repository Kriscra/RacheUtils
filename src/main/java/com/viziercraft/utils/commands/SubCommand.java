package com.viziercraft.utils.commands;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Represents a sub command used under /utils.
 */
public abstract class SubCommand {

    protected final VizierUtilsPlugin plugin;

    protected SubCommand(VizierUtilsPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract String getName();

    public abstract String getPermission();

    public abstract boolean execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
