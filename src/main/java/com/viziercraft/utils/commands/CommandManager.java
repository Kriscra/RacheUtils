package com.viziercraft.utils.commands;

import com.viziercraft.utils.VizierUtilsPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Handles registration and execution of /utils sub commands.
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    private final VizierUtilsPlugin plugin;
    private final Map<String, SubCommand> commands = new HashMap<>();

    public CommandManager(VizierUtilsPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(SubCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            plugin.getMessages().sendMessage(sender, "utils.usage");
            return true;
        }
        SubCommand subCommand = commands.get(args[0].toLowerCase());
        if (subCommand == null) {
            plugin.getMessages().sendMessage(sender, "utils.unknown");
            return true;
        }
        String permission = subCommand.getPermission();
        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            plugin.getMessages().sendMessage(sender, "no-permission");
            return true;
        }
        return subCommand.execute(sender, shiftArgs(args));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length <= 1) {
            return commands.values().stream()
                    .filter(sub -> sender.hasPermission(sub.getPermission()))
                    .map(SubCommand::getName)
                    .filter(name -> name.startsWith(args.length == 0 ? "" : args[0].toLowerCase()))
                    .sorted()
                    .collect(Collectors.toList());
        }
        SubCommand subCommand = commands.get(args[0].toLowerCase());
        if (subCommand == null || (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission()))) {
            return List.of();
        }
        return subCommand.tabComplete(sender, shiftArgs(args));
    }

    private String[] shiftArgs(String[] args) {
        if (args.length <= 1) {
            return new String[0];
        }
        String[] shifted = new String[args.length - 1];
        System.arraycopy(args, 1, shifted, 0, shifted.length);
        return shifted;
    }

    public List<SubCommand> getCommands() {
        return new ArrayList<>(commands.values());
    }
}
