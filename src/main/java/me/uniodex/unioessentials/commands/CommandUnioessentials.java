package me.uniodex.unioessentials.commands;

import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.commands.subcommands.scBungeeCommand;
import me.uniodex.unioessentials.commands.subcommands.scReload;
import me.uniodex.unioessentials.commands.subcommands.scSendmessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandUnioessentials implements CommandExecutor {

    private UnioEssentials plugin;

    private Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandUnioessentials(UnioEssentials plugin) {
        this.plugin = plugin;
        initializeSubCommand();
    }

    private void initializeSubCommand() {
        scSendmessage scSendmessage = new scSendmessage(plugin);
        scReload scReload = new scReload(plugin);
        scBungeeCommand scBungeeCommand = new scBungeeCommand(plugin);

        subCommands.put(scSendmessage.getName(), scSendmessage);
        subCommands.put(scReload.getName(), scReload);
        subCommands.put(scBungeeCommand.getName(), scBungeeCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(plugin.getMessage("general.invalidCommand"));
            return true;
        }

        for (SubCommand subCommand : subCommands.values()) {
            if (!args[0].equalsIgnoreCase(subCommand.getName())) {
                continue;
            }

            if (!sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage(plugin.getMessage("general.noPermission"));
                return true;
            }

            if (args.length < subCommand.getMinArgs()) {
                sender.sendMessage(subCommand.getUsage());
                return true;
            }

            subCommand.run(sender, command, label, args);
            return true;
        }
        return true;
    }
}
