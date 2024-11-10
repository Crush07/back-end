package com.hysea;

import com.hysea.scanner.TableOfDatabaseAndMybatisDiffScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseAndMybatisTableDiffTest {

    @Autowired
    TableOfDatabaseAndMybatisDiffScanner tableOfDatabaseAndMybatisDiffScanner;

    @Test
    public void getDiff() throws ClassNotFoundException {

        assert tableOfDatabaseAndMybatisDiffScanner.getDiff() == 0;
    }
}
