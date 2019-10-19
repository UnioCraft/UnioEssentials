package me.uniodex.unioessentials.commands.subcommands;

import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class scReload implements SubCommand {

    private UnioEssentials plugin;
    private int minArgs = 1;
    private String name = "reload";
    private String permission = "ue.subcommand.reload";
    private String usage = "";

    public scReload(UnioEssentials plugin) {
        this.plugin = plugin;
        usage = plugin.getMessage("commands.unioEssentials.reload.usage");
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
        plugin.reload();
        sender.sendMessage(plugin.getMessage("commands.unioEssentials.reload.success"));
    }
}
