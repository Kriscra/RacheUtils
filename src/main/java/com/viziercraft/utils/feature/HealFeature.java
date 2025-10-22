package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Heals players using /heal.
 */
public class HealFeature extends CommandFeature {

    public HealFeature(VizierUtilsPlugin plugin) {
        super(plugin, "heal", "Heal", "heal", "vizierutils.heal");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setSaturation(20f);
        plugin.getMessages().sendMessage(player, "commands.heal");
        return true;
    }
}
