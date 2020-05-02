package me.uniodex.unioessentials.managers;

import lombok.Getter;
import me.uniodex.unioessentials.UnioEssentials;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private UnioEssentials plugin;

    @Getter
    private Map<String, String> commands = new HashMap<>();

    public CommandManager(UnioEssentials plugin) {
        this.plugin = plugin;
        loadCommandInstances();
    }

    public void loadCommandInstances() {
        commands.clear();
        for (String command : plugin.getConfig().getConfigurationSection("commandInstances").getKeys(false)) {
            String instance = plugin.getConfig().getConfigurationSection("commandInstances").getString(command);
            commands.put(command, instance);
        }
    }

    public void addCommandInstance(String command, String instance) {
        commands.put(command, instance);
    }

    public void addCommandInstances(Map<String, String> commandList) {
        for (String command : commandList.keySet()) {
            String instance = commandList.get(command);
            commands.put(command, instance);
        }
    }

    public void removeCommandInstance(String command) {
        commands.remove(command);
    }
}
