package com.hysea;

import com.hysea.util.FunctionTimingUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static List<Integer> intersection(List<Integer> list1, List<Integer> list2){
        return new ArrayList<>(CollectionUtils.intersection(list1, list2));
    }

    // 我的答案
    public static List<Integer> intersection1(List<Integer> list1, List<Integer> list2){
        List<Integer> collect = list1.stream().filter(list2::contains).collect(Collectors.toList());
        return collect;
    }


    // 面试官建议优化后我的答案
    public static List<Integer> intersection2(List<Integer> list1, List<Integer> list2){
        HashSet<Integer> set1 = new HashSet<>(list1);
        HashSet<Integer> set2 = new HashSet<>(list2);
        List<Integer> collect = set1.stream().filter(set2::contains).collect(Collectors.toList());
        return collect;
    }

    // chatGPT进一步优化，这个方法直接修改 set1，不再需要 stream，能在结构和效率上都更简洁。
    public static List<Integer> intersection3(List<Integer> list1, List<Integer> list2) {
        HashSet<Integer> set1 = new HashSet<>(list1);
        set1.retainAll(list2);
        return new ArrayList<>(set1);
    }

    public static void main(String[] args) {

        FunctionTimingUtils.measureExecutionMemoryAndTime(() -> {
            Integer[] array1 = new Integer[]{1, 5, 8, 24, 4};
            Integer[] array2 = new Integer[]{5, 3, 2, 4};
            List<Integer> list1 = Arrays.stream(array1).collect(Collectors.toList());
            List<Integer> list2 = Arrays.stream(array2).collect(Collectors.toList());
            intersection(list1, list2);
        }, "intersection", 10000);

        FunctionTimingUtils.measureExecutionMemoryAndTime(() -> {
            Integer[] array1 = new Integer[]{1, 5, 8, 24, 4};
            Integer[] array2 = new Integer[]{5, 3, 2, 4};
            List<Integer> list1 = Arrays.stream(array1).collect(Collectors.toList());
            List<Integer> list2 = Arrays.stream(array2).collect(Collectors.toList());
            intersection1(list1, list2);
        }, "intersection1", 10000);

        FunctionTimingUtils.measureExecutionMemoryAndTime(() -> {
            Integer[] array1 = new Integer[]{1, 5, 8, 24, 4};
            Integer[] array2 = new Integer[]{5, 3, 2, 4};
            List<Integer> list1 = Arrays.stream(array1).collect(Collectors.toList());
            List<Integer> list2 = Arrays.stream(array2).collect(Collectors.toList());
            intersection2(list1, list2);
        }, "intersection2", 10000);

        FunctionTimingUtils.measureExecutionMemoryAndTime(() -> {
            Integer[] array1 = new Integer[]{1, 5, 8, 24, 4};
            Integer[] array2 = new Integer[]{5, 3, 2, 4};
            List<Integer> list1 = Arrays.stream(array1).collect(Collectors.toList());
            List<Integer> list2 = Arrays.stream(array2).collect(Collectors.toList());
            intersection3(list1, list2);
        }, "intersection3", 10000);


    }
}