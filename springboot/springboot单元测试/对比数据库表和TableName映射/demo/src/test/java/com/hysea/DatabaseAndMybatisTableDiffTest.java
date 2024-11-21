package com.hysea;

import com.hysea.scanner.DatabaseTableScanner;
import com.hysea.scanner.MybatisTablesScanner;
import com.hysea.scanner.TableOfDatabaseAndMybatisDiffScanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
public class DatabaseAndMybatisTableDiffTest {

    @Autowired
    DataSource dataSource;

    @Value("${hysea.po.packages}")
    String packages;

    private DatabaseTableScanner databaseTableScanner;

    private MybatisTablesScanner mybatisTablesScanner;

    @Bean
    public DatabaseTableScanner databaseTableScanner(){
        databaseTableScanner = new DatabaseTableScanner(dataSource);
        return databaseTableScanner;
    }

    @Bean
    public MybatisTablesScanner mybatisTablesScanner(){
        mybatisTablesScanner = new MybatisTablesScanner();
        return mybatisTablesScanner;
    }

    @Bean
    public TableOfDatabaseAndMybatisDiffScanner tableOfDatabaseAndMybatisDiffScanner(){
        return new TableOfDatabaseAndMybatisDiffScanner(mybatisTablesScanner,databaseTableScanner,packages);
    }

    @Autowired
    TableOfDatabaseAndMybatisDiffScanner tableOfDatabaseAndMybatisDiffScanner;

    @Test
    public void getDiff() throws ClassNotFoundException {

        assert tableOfDatabaseAndMybatisDiffScanner.getDiff() == 0;

    }
}
