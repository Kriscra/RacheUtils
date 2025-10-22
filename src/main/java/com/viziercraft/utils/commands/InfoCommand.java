package com.viziercraft.utils.commands;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.feature.Feature;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * /utils info - shows plugin info.
 */
public class InfoCommand extends SubCommand {

    public InfoCommand(VizierUtilsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getPermission() {
        return "vizierutils.info";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        long enabled = plugin.getFeatureManager().getFeatures().stream().filter(Feature::isEnabled).count();
        Map<String, String> replacements = Map.of(
                "%version%", plugin.getDescription().getVersion(),
                "%enabled%", String.valueOf(enabled),
                "%total%", String.valueOf(plugin.getFeatureManager().getFeatures().size()),
                "%author%", String.join(", ", plugin.getDescription().getAuthors())
        );
        plugin.getMessages().sendMessage(sender, "utils.info", replacements);
        return true;
    }
}
