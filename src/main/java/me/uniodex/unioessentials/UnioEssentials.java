package me.uniodex.unioessentials;

import lombok.Getter;
import me.uniodex.unioessentials.commands.CommandVote;
import me.uniodex.unioessentials.managers.ConfigManager;
import me.uniodex.unioessentials.managers.ConfigManager.Config;
import me.uniodex.unioessentials.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class UnioEssentials extends JavaPlugin {

    @Getter
    private ConfigManager configManager;

    public static String hataPrefix;
    public static String dikkatPrefix;
    public static String bilgiPrefix;
    public static String consolePrefix;

    public void onEnable() {
        configManager = new ConfigManager(this);
        initializePrefixes();

        getCommand("vote").setExecutor(new CommandVote(this));
    }

    public void onDisable() {

    }

    private void initializePrefixes() {
        bilgiPrefix = getMessage("prefix.bilgiPrefix");
        dikkatPrefix = getMessage("prefix.dikkatPrefix");
        hataPrefix = getMessage("prefix.hataPrefix");
        consolePrefix = getMessage("prefix.consolePrefix");
    }

    public String getMessage(String configSection) {
        if (configManager.getConfig(Config.LANG).getString(configSection) == null) return null;

        return Utils.colorizeMessage(configManager.getConfig(Config.LANG).getString(configSection));
    }

    public List<String> getMessages(String configSection) {
        if (configManager.getConfig(Config.LANG).getStringList(configSection) == null) return null;

        return Utils.colorizeMessages(configManager.getConfig(Config.LANG).getStringList(configSection));
    }
}
