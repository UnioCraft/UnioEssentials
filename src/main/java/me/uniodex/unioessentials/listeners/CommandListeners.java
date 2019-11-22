package me.uniodex.unioessentials.listeners;

import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListeners implements Listener {

    private UnioEssentials plugin;

    public CommandListeners(UnioEssentials plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().replaceFirst("/", "").split(" ");
        Player p = event.getPlayer();

        for (String cmd : plugin.getCommandManager().getCommands().keySet()) {
            if (!Utils.matchCommands(cmd.split(" "), command)) continue;
            String commandToUse = plugin.getCommandManager().getCommands().get(cmd);
            for (int i = 0; i < command.length; i++) {
                commandToUse = commandToUse.replaceAll("%arg-" + i + "%", command[i]);
                commandToUse = commandToUse.replaceAll("%args-" + i + "%", StringUtils.join(command, ' ', i, command.length));
            }
            p.performCommand(commandToUse);
            event.setCancelled(true);
            break;
        }
    }
}
