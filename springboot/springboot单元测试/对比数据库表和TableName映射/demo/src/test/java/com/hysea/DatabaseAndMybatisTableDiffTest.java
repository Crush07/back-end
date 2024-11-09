package com.hysea;

import com.hysea.domain.DatabaseTable;
import com.hysea.domain.MybatisTable;
import com.hysea.domain.Table;
import com.hysea.util.DatabaseTableScanner;
import com.hysea.util.MybatisTablesScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SpringBootTest
public class DatabaseAndMybatisTableDiffTest {

    private static final Logger logger = Logger.getLogger(DatabaseAndMybatisTableDiffTest.class.getName());

    @Autowired
    MybatisTablesScanner mybatisTablesScanner;

    @Autowired
    DatabaseTableScanner databaseTableScanner;
    public static Set<String> intersection(Set<String> set1, Set<String> set2) {
        return set1.stream().filter(set2::contains).collect(Collectors.toSet());
    }

    @Test
    public void getDiff() throws ClassNotFoundException {
        Map<String, MybatisTable> collect1 = mybatisTablesScanner.scanTablesWithTableName("com.hysea.*.domain")
                .stream().collect(Collectors.toMap(Table::getTableName, Function.identity()));

        Map<String, DatabaseTable> collect2 = databaseTableScanner.getAllTables()
                .stream()
                .collect(Collectors.toMap(Table::getTableName, Function.identity()));

        Set<Map.Entry<String, MybatisTable>> entries1 = collect1.entrySet();

        Set<Map.Entry<String, DatabaseTable>> entries2 = collect2.entrySet();

        Set<String> mybatisTableNameSet = entries1.stream().map(Map.Entry::getKey).collect(Collectors.toSet());
        Set<String> databaseTableNameSet = entries2.stream().map(Map.Entry::getKey).collect(Collectors.toSet());

        Set<String> intersection = DatabaseAndMybatisTableDiffTest.intersection(mybatisTableNameSet, databaseTableNameSet);


        logger.info("============判断表缺失===========");
        for (String mybatisTableName : mybatisTableNameSet) {
            if(!intersection.contains(mybatisTableName)){
                logger.info("数据库不存在\"" + mybatisTableName + "\"表");
            }
        }

        for (String databaseTableName : databaseTableNameSet) {
            if(!intersection.contains(databaseTableName)){
                logger.info("未定义\"" + databaseTableName + "\"表的PO对象");
            }
        }

        assert (mybatisTableNameSet.size() == intersection.size() &&
                databaseTableNameSet.size() == intersection.size());
    }
}
