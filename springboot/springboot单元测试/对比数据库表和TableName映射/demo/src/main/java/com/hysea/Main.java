package com.hysea;

import com.hysea.scanner.DatabaseTableScanner;
import com.hysea.scanner.MybatisTablesScanner;
import com.hysea.scanner.TableOfDatabaseAndMybatisDiffScanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan({
        "com.hysea.*.mapper"
})
public class Main {

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

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}