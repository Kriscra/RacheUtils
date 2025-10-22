package com.viziercraft.utils.feature;

import com.viziercraft.utils.VizierUtilsPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Places the item in hand onto the player's head.
 */
public class HatFeature extends CommandFeature {

    public HatFeature(VizierUtilsPlugin plugin) {
        super(plugin, "hat", "Hat", "hat", "vizierutils.hat");
    }

    @Override
    protected boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!ensurePlayer(sender)) {
            return true;
        }
        Player player = (Player) sender;
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand.getType() == Material.AIR) {
            plugin.getMessages().sendMessage(player, "commands.hat-empty");
            return true;
        }
        ItemStack helmet = player.getInventory().getHelmet();
        player.getInventory().setHelmet(hand);
        player.getInventory().setItemInMainHand(helmet == null ? new ItemStack(Material.AIR) : helmet);
        plugin.getMessages().sendMessage(player, "commands.hat");
        return true;
    }
}
