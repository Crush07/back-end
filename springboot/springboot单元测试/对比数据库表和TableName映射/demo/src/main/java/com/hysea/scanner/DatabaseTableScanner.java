package com.hysea.scanner;

import com.hysea.scanner.domain.DatabaseTable;
import com.hysea.scanner.domain.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DatabaseTableScanner {

    private DataSource dataSource;

    public DatabaseTableScanner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Set<DatabaseTable> getAllTables() {
        Set<DatabaseTable> tables = new HashSet<>();

        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            DatabaseMetaData metaData = connection.getMetaData();

            String catalog = connection.getCatalog();
            // 获取所有表
            try (ResultSet tablesResultSet = metaData.getTables(catalog, null, "%", new String[]{"TABLE"})) {
                while (tablesResultSet.next()) {
                    String tableName = tablesResultSet.getString("TABLE_NAME");
                    DatabaseTable databaseTable = new DatabaseTable();
                    databaseTable.setTableName(tableName);

                    // 获取每个表的字段
                    try (ResultSet columnsResultSet = metaData.getColumns(catalog, null, tableName, "%")) {
                        while (columnsResultSet.next()) {
                            String columnName = columnsResultSet.getString("COLUMN_NAME");
                            Table.Field field = new Table.Field(columnName);
                            databaseTable.addField(field);
                        }
                    }

                    tables.add(databaseTable);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }
}
