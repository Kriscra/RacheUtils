package com.viziercraft.utils.commands;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.feature.Feature;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.StringJoiner;

/**
 * /utils list - lists active features.
 */
public class ListCommand extends SubCommand {

    public ListCommand(VizierUtilsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "vizierutils.list";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        StringJoiner enabled = new StringJoiner(", ");
        StringJoiner disabled = new StringJoiner(", ");
        for (Feature feature : plugin.getFeatureManager().getFeatures()) {
            if (feature.isEnabled()) {
                enabled.add(feature.getDisplayName());
            } else {
                disabled.add(feature.getDisplayName());
            }
        }
        plugin.getMessages().sendMessage(sender, "utils.list-header");
        plugin.getMessages().sendMessage(sender, "utils.list-enabled", Map.of("%list%", enabled.length() == 0 ? "-" : enabled.toString()));
        plugin.getMessages().sendMessage(sender, "utils.list-disabled", Map.of("%list%", disabled.length() == 0 ? "-" : disabled.toString()));
        return true;
    }
}
