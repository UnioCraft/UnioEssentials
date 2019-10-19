package me.uniodex.unioessentials;

import lombok.Getter;
import me.uniodex.unioessentials.commands.CommandUnioessentials;
import me.uniodex.unioessentials.commands.CommandVote;
import me.uniodex.unioessentials.listeners.SecurityListeners;
import me.uniodex.unioessentials.managers.ConfigManager;
import me.uniodex.unioessentials.managers.ConfigManager.Config;
import me.uniodex.unioessentials.managers.RconManager;
import me.uniodex.unioessentials.managers.SQLManager;
import me.uniodex.unioessentials.managers.SecurityManager;
import me.uniodex.unioessentials.utils.Utils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class UnioEssentials extends JavaPlugin {

    @Getter
    private ConfigManager configManager;
    @Getter
    private SQLManager sqlManager;
    @Getter
    private RconManager rconManager;
    @Getter
    private SecurityManager securityManager;

    @Getter
    private Object permission;

    public static String hataPrefix;
    public static String dikkatPrefix;
    public static String bilgiPrefix;
    public static String consolePrefix;

    public void onEnable() {
        configManager = new ConfigManager(this);
        initializePrefixes();

        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
            if (permissionProvider != null) {
                permission = permissionProvider.getProvider();
            }
        }

        // Managers
        sqlManager = new SQLManager(this);
        rconManager = new RconManager(this);
        securityManager = new SecurityManager(this);

        // Listeners
        new SecurityListeners(this);

        // Commands
        getCommand("unioessentials").setExecutor(new CommandUnioessentials(this));
        getCommand("vote").setExecutor(new CommandVote(this));
    }

    public void onDisable() {
        sqlManager.onDisable();
        rconManager.onDisable();
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

    public void reload() {
        reloadConfig();
    }
}
