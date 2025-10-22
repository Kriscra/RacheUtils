package com.viziercraft.utils.commands;

import com.viziercraft.utils.VizierUtilsPlugin;
import com.viziercraft.utils.feature.Feature;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * /utils toggle <feature>
 */
public class ToggleCommand extends SubCommand {

    public ToggleCommand(VizierUtilsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getPermission() {
        return "vizierutils.toggle";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "utils.toggle-usage");
            return true;
        }
        boolean success = plugin.getFeatureManager().toggleFeature(args[0], true);
        if (!success) {
            plugin.getMessages().sendMessage(sender, "utils.unknown-feature");
            return true;
        }
        Feature feature = plugin.getFeatureManager().getFeature(args[0]).orElse(null);
        if (feature != null) {
            Map<String, String> replacements = new HashMap<>();
            replacements.put("%feature%", feature.getDisplayName());
            replacements.put("%state%", feature.isEnabled() ? plugin.getMessages().getMessage("state.enabled") : plugin.getMessages().getMessage("state.disabled"));
            plugin.getMessages().sendMessage(sender, "utils.toggle", replacements);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return plugin.getFeatureManager().getFeatures().stream()
                    .map(Feature::getKey)
                    .filter(key -> key.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
