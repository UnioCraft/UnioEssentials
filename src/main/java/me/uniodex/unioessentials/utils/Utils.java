package me.uniodex.unioessentials.utils;

import me.uniodex.unioessentials.UnioEssentials;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String colorizeMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%hataPrefix%", UnioEssentials.hataPrefix).replaceAll("%bilgiPrefix%", UnioEssentials.bilgiPrefix).replaceAll("%dikkatPrefix%", UnioEssentials.dikkatPrefix).replaceAll("%prefix%", UnioEssentials.bilgiPrefix));
    }

    public static List<String> colorizeMessages(List<String> messages) {
        List<String> newList = new ArrayList<>();
        for (String msg : messages) {
            newList.add(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%hataPrefix%", UnioEssentials.hataPrefix).replaceAll("%bilgiPrefix%", UnioEssentials.bilgiPrefix).replaceAll("%dikkatPrefix%", UnioEssentials.dikkatPrefix).replaceAll("%prefix%", UnioEssentials.bilgiPrefix)));
        }
        return newList;
    }

    public static boolean matchCommands(String[] configCommand, String[] givenCommand) {
        if (givenCommand.length < configCommand.length) return false;
        if (!configCommand[0].equalsIgnoreCase(givenCommand[0])) return false;

        for (int i = 1; i < configCommand.length; i++) {
            if (configCommand[i].startsWith("<")) return true;
            if (!configCommand[i].equalsIgnoreCase(givenCommand[i])) return false;
        }
        return true;
    }

}
