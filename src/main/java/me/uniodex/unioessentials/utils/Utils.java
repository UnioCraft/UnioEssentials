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

}
