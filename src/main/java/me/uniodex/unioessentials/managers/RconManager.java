package me.uniodex.unioessentials.managers;

import me.uniodex.unioessentials.UnioEssentials;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RconManager {

    private UnioEssentials plugin;
    private Rcon rcon;
    private List<String> commandsInHold = new ArrayList<>();

    public RconManager(UnioEssentials plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::connectRcon);
        Bukkit.getScheduler().runTaskTimer(plugin, this::performHeldCommands, 100L, 100L);
    }

    public void performHeldCommands() {
        if (commandsInHold.size() <= 0) {
            return;
        }

        for (String command : commandsInHold) {
            sendBungeeCommand(command);
        }
    }

    public boolean connectRcon() {
        try {
            rcon = new Rcon(plugin.getConfig().getString("rcon.hostName"), plugin.getConfig().getInt("rcon.port"), plugin.getConfig().getString("rcon.password").getBytes());
        } catch (IOException | AuthenticationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Rcon getWorkingRcon() {
        if (rcon != null && !rcon.getSocket().isClosed()) {
            return rcon;
        } else {
            if (connectRcon()) {
                return rcon;
            } else {
                return null;
            }
        }
    }

    public void sendBungeeCommand(String command) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Rcon rcon = getWorkingRcon();
            if (rcon == null) {
                commandsInHold.add(command);
                return;
            }

            try {
                rcon.command(command);
            } catch (IOException e) {
                Bukkit.getLogger().severe("BungeeCord RCON komutu kullanılamadı. Komut beklemeye alınıyor.");
                commandsInHold.add(command);
                e.printStackTrace();
            }
        });
    }

    public void onDisable() {
        if (commandsInHold.size() > 0) {
            Bukkit.getLogger().severe("Bazı BungeeCord komutları tüm uğraşlara rağmen kullanılamadı. Komutlar:");
            for (String command : commandsInHold) {
                Bukkit.getLogger().severe("- " + command);
            }
        }

        try {
            rcon.disconnect();
        } catch (IOException ignored) {
        }
    }

}
