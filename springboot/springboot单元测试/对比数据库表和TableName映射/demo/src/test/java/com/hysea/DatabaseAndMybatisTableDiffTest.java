package com.hysea;

import com.hysea.scanner.TableOfDatabaseAndMybatisDiffScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootTest
public class DatabaseAndMybatisTableDiffTest implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    TableOfDatabaseAndMybatisDiffScanner tableOfDatabaseAndMybatisDiffScanner;

    @Test
    public void getDiff() throws ClassNotFoundException {
        assert tableOfDatabaseAndMybatisDiffScanner.getDiff() == 0;
    }
}
