package com.hysea.domain;

import java.util.List;

public class MybatisTable extends Table {
    public MybatisTable(String tableName, List<Field> fields) {
        super(tableName, fields);
    }

    public MybatisTable() {
    }
}
