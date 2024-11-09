package com.hysea.util;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class FunctionTimingUtils {

    private static final Logger logger = Logger.getLogger(FunctionTimingUtils.class.getName());
//    public static void main(String[] args) {
//        // 示例：传入一个简单的打印函数和循环次数
//        long time = measureExecutionTime(i -> System.out.println("Iteration " + i), 100);
//        System.out.println("Total execution time: " + time + " ms");
//    }
//
//    public static long measureExecutionTime(Consumer<Integer> function, int n) {
//        long startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < n; i++) {
//            function.accept(i);
//        }
//
//        long endTime = System.currentTimeMillis();
//        return endTime - startTime;
//    }

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
}
