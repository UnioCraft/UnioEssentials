package me.uniodex.unioessentials.listeners;

import me.uniodex.unioessentials.UnioEssentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SecurityListeners implements Listener {

    private UnioEssentials plugin;

    public SecurityListeners(UnioEssentials plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        String username = event.getName();
        String ip = event.getAddress().getHostAddress();

        if (!plugin.getSecurityManager().isStaff(username) && !plugin.getSecurityManager().is2FAEnabled(username))
            return;
        if (plugin.getSecurityManager().checkIP(username, ip)) return;

        event.disallow(Result.KICK_OTHER, plugin.getMessage("security.wrongIP"));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (plugin.getSecurityManager().isStaff(player)) {
            if (!plugin.getSecurityManager().checkIP(player.getName(), player.getAddress().getAddress().getHostAddress())) {
                event.setCancelled(true);

                if (plugin.getSecurityManager().isAdmin(player)) {
                    player.setOp(false);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perm player " + player.getName() + " purge");
                    player.kickPlayer(plugin.getMessage("security.kickAdmin"));
                } else {
                    player.kickPlayer(plugin.getMessage("security.kickStaff"));
                }
            }
        }
    }
}
