package com.hysea.domain;

import java.util.List;

public class DatabaseTable extends Table {
    public DatabaseTable(String tableName, List<Field> fields) {
        super(tableName, fields);
    }

    public DatabaseTable() {
    }
}
