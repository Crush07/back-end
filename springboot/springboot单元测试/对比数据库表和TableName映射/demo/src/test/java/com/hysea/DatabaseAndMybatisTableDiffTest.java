package com.hysea;

import com.hysea.scanner.TableOfDatabaseAndMybatisDiffScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.PathMatcher;

import java.util.function.Predicate;

@SpringBootTest
public class DatabaseAndMybatisTableDiffTest implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Autowired
    PathMatcher pathMatcher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    TableOfDatabaseAndMybatisDiffScanner tableOfDatabaseAndMybatisDiffScanner;

    @Test
    public void getDiff() throws ClassNotFoundException {
        System.out.println(pathMatcher);
        {
            boolean pattern = pathMatcher.isPattern("/dfd/ffff/{ddd}");
            System.out.println(pattern);
        }
        {
            boolean pattern = pathMatcher.isPattern("/dfd/ffff/ddd}");
            System.out.println(pattern);
        }
        {
            boolean pattern = pathMatcher.isPattern("/dfd/ffff/ddd");
            System.out.println(pattern);
        }

        Predicate<Integer> predicate = count-> count == 0;
        assert predicate.test(tableOfDatabaseAndMybatisDiffScanner.getDiff());
    }
}
