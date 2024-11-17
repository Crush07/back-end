package com.hysea;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoaderUtilsDemo {

    public static void main(String[] args) throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("hysea.yml");
        properties.forEach((key,value) -> {
            System.out.println(key + "=" + value);
        });
    }

}
