package me.uniodex.unioessentials.objects;

import lombok.Getter;

public class DatabaseInfo {

    @Getter
    private String identifierName;
    @Getter
    private String dbName;
    @Getter
    private String tableName;
    @Getter
    private String usernameColumn;

    public DatabaseInfo(String identifierName, String dbName, String tableName, String usernameColumn) {
        this.identifierName = identifierName;
        this.dbName = dbName;
        this.tableName = tableName;
        this.usernameColumn = usernameColumn;
    }
}
