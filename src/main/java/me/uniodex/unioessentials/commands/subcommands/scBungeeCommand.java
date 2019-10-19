package me.uniodex.unioessentials.commands.subcommands;

import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.commands.SubCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class scBungeeCommand implements SubCommand {

    private UnioEssentials plugin;
    private int minArgs = 2;
    private String name = "bcmd";
    private String permission = "ue.subcommand.bungeecommand";
    private String usage = "";

    public scBungeeCommand(UnioEssentials plugin) {
        this.plugin = plugin;
        usage = plugin.getMessage("commands.unioEssentials.bungeeCommand.usage");
    }

    public int getMinArgs() {
        return minArgs;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getUsage() {
        return usage;
    }

    public void run(CommandSender sender, Command command, String label, String[] args) {
        String bcmd = StringUtils.join(args, ' ', 1, args.length);
        plugin.getRconManager().sendBungeeCommand(bcmd);
        if (!plugin.getConfig().getBoolean("settings.silentBcmd")) {
            sender.sendMessage(plugin.getMessage("commands.unioEssentials.bungeeCommand.success"));
        }
    }
}
