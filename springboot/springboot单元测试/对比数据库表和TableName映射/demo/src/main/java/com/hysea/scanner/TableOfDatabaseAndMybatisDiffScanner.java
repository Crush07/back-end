package com.hysea.scanner;

import com.hysea.domain.DatabaseTable;
import com.hysea.domain.MybatisTable;
import com.hysea.domain.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class TableOfDatabaseAndMybatisDiffScanner {

    private static final Logger logger = Logger.getLogger(TableOfDatabaseAndMybatisDiffScanner.class.getName());

    @Autowired
    MybatisTablesScanner mybatisTablesScanner;

    @Autowired
    DatabaseTableScanner databaseTableScanner;

    @Value("hysea.po.packages")
    String packages;

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
                        logger.info("------");
                        logger.info("表缺失：");
                        isFirst = false;
                    }
                    logger.warning("数据库不存在\"" + mybatisTableName + "\"表。");
                    diffCount++;
                }
            }
        }

        {
            boolean isFirst = true;
            for (String databaseTableName : databaseTableNameSet) {
                if (!tablesIntersection.contains(databaseTableName)) {
                    if (isFirst) {
                        logger.info("------");
                        logger.info("PO对象缺失：");
                        isFirst = false;
                    }
                    logger.warning("未定义\"" + databaseTableName + "\"表的PO对象。");
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
                            logger.info("------");
                            logger.info("\"" + tableName + "\"表缺失字段：");
                            isFirst = false;
                        }
                        logger.warning("\"" + tableName + "\"表不存在`" + mybatisTableField + "`字段。");
                        diffCount++;
                    }
                }
            }


            {
                boolean isFirst = true;
                for (String databaseTableField : databaseTableFieldSet) {
                    if (!fieldIntersection.contains(databaseTableField)) {
                        if (isFirst) {
                            logger.info("\"" + tableName + "\"PO对象缺失字段：");
                            isFirst = false;
                        }
                        logger.warning("PO对象未定义\"" + tableName + "\"表的`" + databaseTableField + "`字段。");
                        diffCount++;
                    }
                }
            }
        }

        return diffCount;

    }
}
