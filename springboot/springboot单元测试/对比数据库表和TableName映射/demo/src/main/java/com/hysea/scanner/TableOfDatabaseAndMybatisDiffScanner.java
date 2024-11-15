package com.hysea.scanner;

import com.hysea.scanner.domain.DatabaseTable;
import com.hysea.scanner.domain.MybatisTable;
import com.hysea.scanner.domain.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class TableOfDatabaseAndMybatisDiffScanner {

    MybatisTablesScanner mybatisTablesScanner;

    DatabaseTableScanner databaseTableScanner;

    String packages;

    public TableOfDatabaseAndMybatisDiffScanner(MybatisTablesScanner mybatisTablesScanner, DatabaseTableScanner databaseTableScanner, String packages) {
        this.mybatisTablesScanner = mybatisTablesScanner;
        this.databaseTableScanner = databaseTableScanner;
        this.packages = packages;
    }

    public static Set<String> intersection(Set<String> set1, Set<String> set2) {
        return set1.stream().filter(set2::contains).collect(Collectors.toSet());
    }

    public int getDiff() throws ClassNotFoundException {

        int diffCount = 0;

        String[] packageArray = packages.split(",");

        Map<String, MybatisTable> mybatisTableMap = mybatisTablesScanner.scanTablesWithTableName(packageArray)
                .stream().collect(Collectors.toMap(Table::getTableName, Function.identity()));

        Map<String, DatabaseTable> databaseTableMap = databaseTableScanner.getAllTables()
                .stream()
                .collect(Collectors.toMap(Table::getTableName, Function.identity()));

        Set<String> mybatisTableNameSet = mybatisTableMap.keySet();

        Set<String> databaseTableNameSet = databaseTableMap.keySet();

        Set<String> tablesIntersection = TableOfDatabaseAndMybatisDiffScanner.intersection(mybatisTableNameSet, databaseTableNameSet);

        {
            boolean isFirst = true;
            for (String mybatisTableName : mybatisTableNameSet) {
                if (!tablesIntersection.contains(mybatisTableName)) {
                    if (isFirst) {

                        log.info("------");
                        log.info("表缺失：");
                        isFirst = false;
                    }
                    log.warn("数据库不存在\"{}\"表。",mybatisTableName);
                    diffCount++;
                }
            }
        }

        {
            boolean isFirst = true;
            for (String databaseTableName : databaseTableNameSet) {
                if (!tablesIntersection.contains(databaseTableName)) {
                    if (isFirst) {
                        log.info("------");
                        log.info("PO对象缺失：");
                        isFirst = false;
                    }
                    log.warn("未定义\"{}\"表的PO对象。",databaseTableName);
                    diffCount++;
                }
            }
        }

        for (String tableName : tablesIntersection) {
            MybatisTable mybatisTable = mybatisTableMap.get(tableName);
            DatabaseTable databaseTable = databaseTableMap.get(tableName);

            Set<String> mybatisTableFieldSet = mybatisTable.getFields().stream().map(Table.Field::getFieldName).collect(Collectors.toSet());
            Set<String> databaseTableFieldSet = databaseTable.getFields().stream().map(Table.Field::getFieldName).collect(Collectors.toSet());

            Set<String> fieldIntersection = TableOfDatabaseAndMybatisDiffScanner.intersection(mybatisTableFieldSet, databaseTableFieldSet);

            {
                boolean isFirst = true;
                for (String mybatisTableField : mybatisTableFieldSet) {
                    if (!fieldIntersection.contains(mybatisTableField)) {
                        if (isFirst) {
                            log.info("------");
                            log.info("\"{}\"表缺失字段：",tableName);
                            isFirst = false;
                        }
                        log.warn("\"{}\"表不存在`{}`字段。",tableName,mybatisTableField);
                        diffCount++;
                    }
                }
            }


            {
                boolean isFirst = true;
                for (String databaseTableField : databaseTableFieldSet) {
                    if (!fieldIntersection.contains(databaseTableField)) {
                        if (isFirst) {
                            log.info("\"{}\"PO对象缺失字段：",tableName);
                            isFirst = false;
                        }
                        log.warn("PO对象未定义\"{}\"表的`{}`字段。",tableName,databaseTableField);
                        diffCount++;
                    }
                }
            }
        }

        return diffCount;

    }
}
