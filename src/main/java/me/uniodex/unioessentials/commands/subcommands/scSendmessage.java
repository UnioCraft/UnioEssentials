package me.uniodex.unioessentials.commands.subcommands;

import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.commands.SubCommand;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class scSendmessage implements SubCommand {

    private UnioEssentials plugin;
    private int minArgs = 3;
    private String name = "sendmessage";
    private String permission = "ue.subcommand.sendmessage";
    private String usage = "";

    public scSendmessage(UnioEssentials plugin) {
        this.plugin = plugin;
        usage = plugin.getMessage("commands.unioEssentials.sendMessage.usage");
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
        Player player = Bukkit.getPlayerExact(args[1]);
        if (player == null) {
            sender.sendMessage(plugin.getMessage("commands.unioEssentials.sendMessage.playerNotFound"));
            return;
        }

        String msg = StringUtils.join(args, ' ', 2, args.length);
        player.sendMessage(UnioEssentials.bilgiPrefix + " " + ChatColor.translateAlternateColorCodes('&', msg));
        if (!plugin.getConfig().getBoolean("settings.silentSendMessage")) {
            sender.sendMessage(plugin.getMessage("commands.unioEssentials.sendMessage.success"));
        }
    }
}
