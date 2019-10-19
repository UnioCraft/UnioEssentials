package me.uniodex.unioessentials.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.uniodex.unioessentials.UnioEssentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {

    private UnioEssentials plugin;

    private HikariDataSource pointsDataSource;

    private String hostname;
    private String port;
    private String username;
    private String password;

    public SQLManager(UnioEssentials plugin) {
        this.plugin = plugin;
        init();
        setupPool();
    }

    private void init() {
        hostname = plugin.getConfig().getString("database.hostName");
        port = plugin.getConfig().getString("database.port");
        username = plugin.getConfig().getString("database.userName");
        password = plugin.getConfig().getString("database.password");
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname +
                        ":" +
                        port
        );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(15000);
        config.setLeakDetectionThreshold(10000);
        config.setPoolName("UnioEssentialsPool");
        pointsDataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return pointsDataSource.getConnection();
    }

    private void closePool() {
        if (pointsDataSource != null && !pointsDataSource.isClosed()) {
            pointsDataSource.close();
        }
    }

    public boolean check(String uniqueValue, String dbName, String tableName, String uniqueColumn, String booleanColumn) {
        String QUERY = "SELECT " + uniqueColumn + " FROM `" + dbName + "`.`" + tableName + "` WHERE " + uniqueColumn + " = '" + uniqueValue + "';";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getBoolean(booleanColumn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExist(String uniqueValue, String dbName, String tableName, String uniqueColumn) {
        String QUERY = "SELECT " + uniqueColumn + " FROM `" + dbName + "`.`" + tableName + "` WHERE " + uniqueColumn + " = '" + uniqueValue + "';";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getString(uniqueColumn) != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSQL(String QUERY) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int get(String userName, String dbName, String tableName, String columnName, String userNameColumn) {
        String QUERY = "SELECT " + columnName + " FROM `" + dbName + "`.`" + tableName + "` WHERE " + userNameColumn + " = '" + userName + "';";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getInt(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -999999;
    }

    public void onDisable() {
        closePool();
    }
}
