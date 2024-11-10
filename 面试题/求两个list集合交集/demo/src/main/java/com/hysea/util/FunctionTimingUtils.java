package com.hysea.util;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class FunctionTimingUtils {

    private static final Logger logger = Logger.getLogger(FunctionTimingUtils.class.getName());

    public static long measureExecutionTime(Runnable function, String functionName, int n) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            function.run();
        }

        long endTime = System.currentTimeMillis();
        long res = endTime - startTime;
        logger.info("函数" + functionName + "花费" + res + "ms");
        return res;
    }

    public static long measureExecutionMemory(Runnable function, String functionName, int n) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long startMemory = runtime.totalMemory();
        for (int i = 0; i < n; i++) {
            function.run();
        }
        long endMemory = runtime.freeMemory();
        long res = startMemory - endMemory;
        logger.info("函数" + functionName + "花费" + res / 1024 + "KB");
        return res;
    }

    public static void measureExecutionMemoryAndTime(Runnable function, String functionName, int n) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long startTime = System.currentTimeMillis();

        long startMemory = runtime.totalMemory();
        for (int i = 0; i < n; i++) {
            function.run();
        }

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        long endMemory = runtime.freeMemory();
        long memory = startMemory - endMemory;
        logger.info("函数" + functionName + "\n花费" + memory / 1024 + "KB\n" + "花费" + time + "ms");
    }
}
