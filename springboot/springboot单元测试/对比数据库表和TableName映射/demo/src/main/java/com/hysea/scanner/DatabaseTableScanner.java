package com.hysea.scanner;

import com.hysea.scanner.domain.DatabaseTable;
import com.hysea.scanner.domain.Table;
import com.hysea.scanner.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseTableScanner {

    @Autowired
    private TableMapper tableMapper;

    @Value("${hysea.database}")
    String database;

    public Set<DatabaseTable> getAllTables() {
        Set<DatabaseTable> tables = new HashSet<>();

        tables.addAll(tableMapper.selectTableNameList(database));
        return tables;
    }
}
