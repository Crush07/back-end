package com.hysea;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Main {

    /**
     * 计算date1和date2之间天数差
     * @param date1 date1
     * @param date2 date2
     * @return 天数差
     */
    public static int calculateDaysDifference(Date date1, Date date2){
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long between = ChronoUnit.DAYS.between(localDate1, localDate2);
        return Math.abs((int) between);
    }

    public static void main(String[] args) {
        Date date1 = new Date();

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR,2024);
        instance.set(Calendar.MONTH,10);
        instance.set(Calendar.DAY_OF_MONTH,1);
        Date date2 = instance.getTime();

        System.out.println(calculateDaysDifference(date1, date2));

    }
}