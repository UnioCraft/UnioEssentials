package me.uniodex.unioessentials.objects;

import lombok.Getter;
import me.uniodex.unioessentials.UnioEssentials;
import me.uniodex.unioessentials.enums.SQLAction;
import me.uniodex.unioessentials.managers.SQLManager;

public class SQLProcess {

    private UnioEssentials plugin;
    private SQLManager sqlManager;

    private SQLAction action;
    private Integer amount;
    private String userName;
    private String dbName;
    private String tableName;
    private String columnName;
    private String userNameColumn;
    private boolean create;

    private int value = 0;
    @Getter
    private boolean successful;

    public SQLProcess(UnioEssentials plugin, SQLAction action, Integer amount, String userName, String dbName, String tableName, String columnName, String userNameColumn, boolean create) {
        this.plugin = plugin;
        this.sqlManager = plugin.getSqlManager();
        this.action = action;
        this.amount = amount;
        this.userName = userName;
        this.dbName = dbName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.userNameColumn = userNameColumn;
        this.create = create;

        init();
    }

    private void init() {
        if (create) {
            create();
        }

        prepareValue();

        switch (action) {
            case GET:
                if (value == -999999) {
                    successful = false;
                    value = 0;
                } else {
                    successful = true;
                }
                break;
            case SET:
                successful = set();
                if (successful) value = amount;
                break;
            case ADD:
                successful = add();
                if (successful) value += amount;
                break;
            case REMOVE:
                successful = remove();
                if (successful) value -= amount;
                break;
            default:
                break;
        }
    }

    private boolean exist() {
        return sqlManager.checkExist(userName, dbName, tableName, userNameColumn);
    }

    private boolean create() {
        if (!exist()) {
            return sqlManager.updateSQL("INSERT INTO `" + dbName + "`.`" + tableName +
                    "` (" + userNameColumn + ", " + columnName + ") " +
                    "VALUES ('" + userName + "','" + 0 + "');");
        } else {
            return false;
        }
    }

    private void prepareValue() {
        value = sqlManager.get(userName, dbName, tableName, columnName, userNameColumn);
    }

    public int get() {
        return value;
    }

    private boolean set() {
        if (exist()) {
            return sqlManager.updateSQL("UPDATE `" + dbName + "`.`" + tableName +
                    "` SET `" + columnName + "` = '" + amount +
                    "' WHERE `" + userNameColumn + "` = '" + userName + "';");
        }
        return false;
    }

    private boolean add() {
        if (exist()) {
            return sqlManager.updateSQL("UPDATE `" + dbName + "`.`" + tableName +
                    "` SET `" + columnName + "` = '" + (get() + amount) +
                    "' WHERE `" + userNameColumn + "` = '" + userName + "';");
        }
        return false;
    }

    private boolean remove() {
        if (exist()) {
            return sqlManager.updateSQL("UPDATE `" + dbName + "`.`" + tableName +
                    "` SET `" + columnName + "` = '" + (get() - amount) +
                    "' WHERE `" + userNameColumn + "` = '" + userName + "';");
        }
        return false;
    }
}
