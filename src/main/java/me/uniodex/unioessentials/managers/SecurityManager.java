package me.uniodex.unioessentials.managers;

import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.enums.SQLAction;
import me.uniodex.unioessentials.objects.SQLProcess;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityManager {

    private UnioEssentials plugin;

    public SecurityManager(UnioEssentials plugin) {
        this.plugin = plugin;
    }

    private int getUserID(String username) {
        SQLProcess sqlProcess = new SQLProcess(plugin, SQLAction.GET, 0, username, "website", "xf_user", "user_id", "username", false);
        return sqlProcess.get();
    }

    public boolean is2FAEnabled(String username) {
        return plugin.getSqlManager().checkExist(String.valueOf(getUserID(username)), "website", "xf_user_tfa", "user_id");
    }

    public boolean checkIP(String username, String ip) {
        int userid = getUserID(username);

        String QUERY = "SELECT ip_id, INET_NTOA(CONV(HEX(ip), 16, 10)) AS ip FROM `website`.`xf_ip` WHERE user_id = " + userid + " ORDER BY ip_id DESC LIMIT 1";
        try (Connection connection = plugin.getSqlManager().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                if (res.getString("ip") == null) {
                    return false;
                }
                return res.getString("ip").equals(ip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAdmin(Player player) {
        return player.isOp()
                || player.hasPermission("*")
                || player.hasPermission("zpermissions.*")
                || player.hasPermission("luckperms.*")
                || (plugin.getPermission() != null && ((Permission) plugin.getPermission()).playerInGroup(player, "Kurucu"));
    }

    private boolean isMod(Player player) {
        return plugin.getPermission() != null
                && (((Permission) plugin.getPermission()).playerInGroup(player, "DenemeModerator")
                || ((Permission) plugin.getPermission()).playerInGroup(player, "Moderator")
                || ((Permission) plugin.getPermission()).playerInGroup(player, "SuperModerator"));
    }

    public boolean isStaff(Player player) {
        return isAdmin(player) || isMod(player);
    }

    public boolean isStaff(String userName) {
        if (userName.equalsIgnoreCase("UnioDex")) return true;
        if (userName.toLowerCase().contains("unioguvenlik")) return true;

        SQLProcess sqlProcess = new SQLProcess(plugin, SQLAction.GET, 0, userName, "website", "xf_user", "is_staff", "username", false);
        return sqlProcess.get() == 1;
    }
}
