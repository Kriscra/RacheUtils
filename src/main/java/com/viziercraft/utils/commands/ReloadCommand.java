package com.viziercraft.utils.commands;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.command.CommandSender;

/**
 * /utils reload - reloads configuration and messages.
 */
public class ReloadCommand extends SubCommand {

    public ReloadCommand(VizierUtilsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "vizierutils.reload";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        plugin.getMessages().reload();
        plugin.getFeatureManager().reloadAll();
        plugin.getMessages().sendMessage(sender, "utils.reload");
        return true;
    }
}
