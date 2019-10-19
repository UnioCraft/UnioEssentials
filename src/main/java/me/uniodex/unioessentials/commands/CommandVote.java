package me.uniodex.unioessentials.commands;

import me.uniodex.unioessentials.UnioEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandVote implements CommandExecutor {

    private UnioEssentials plugin;

    public CommandVote(UnioEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(plugin.getMessage("commands.vote.voteMessage"));
        return true;
    }
}
