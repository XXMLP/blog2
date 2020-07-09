package com.xxmlp.util;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateStartAndEnd {

    //获取当天结束时间
    public static Date getEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }

    //获取当天开始时间
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }

//    //每周开始时间
//    public static Date getBeginDayOfWeek(Date date) {
//        Calendar c = new GregorianCalendar();
//        c.setFirstDayOfWeek(Calendar.MONDAY);
//        c.setTime(date);
//        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
//        LocalDate toDay = toLocalData(c.getTime());
//        LocalDateTime localDateTime = toDay.atTime(0, 0, 0);
//        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
//        return Date.from(zonedDateTime.toInstant());
//    }
//
//    //每周的结束时间
//    public static Date getEndDayOfWeek(Date date) {
//        Calendar c = new GregorianCalendar();
//        c.setFirstDayOfWeek(Calendar.MONDAY);
//        c.setTime(date);
//        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
//        LocalDate toDay = toLocalData(c.getTime());
//        LocalDateTime localDateTime = toDay.atTime(23, 59, 59, 999);
//        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
//        return Date.from(zonedDateTime.toInstant());
//    }
//
//    //一个月的开始时间
//    public static Date getBeginTimeOfMonth(Date date) {
//        LocalDate ld = toLocalData(date);
//        YearMonth yearMonth = YearMonth.of(ld.getYear(), ld.getMonth());
//        LocalDate localDate = yearMonth.atDay(1);
//        LocalDateTime startOfDay = localDate.atStartOfDay();
//        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.systemDefault());
//        return Date.from(zonedDateTime.toInstant());
//    }
//
//    //一个月的结束时间
//    public static Date getEndTimeOfMonth(Date date) {
//        LocalDate ld = toLocalData(date);
//        YearMonth yearMonth = YearMonth.of(ld.getYear(), ld.getMonth());
//        LocalDate endOfMonth = yearMonth.atEndOfMonth();
//        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
//        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
//        return Date.from(zonedDateTime.toInstant());
//    }
//
//    //前几个月，字符串形式
//    public static String[] getBeforeMonth(int num) {
//        final String[] result = new String[num];
//        LocalDate localDate = LocalDate.now();
//        for (int i = 0; i < num; i++) {
//            result[i] = (localDate.getMonthValue()) + "月";
//            localDate = localDate.minusMonths(1);
//        }
//        return result;
//    }
//
//    //后几个月，字符串形式
//    public static String[] getAfterMonth(int num) {
//        final String[] result = new String[num];
//        LocalDate localDate = LocalDate.now();
//        for (int i = 0; i < num; i++) {
//            result[i] = (localDate.getMonthValue()) + "月";
//            localDate = localDate.plusMonths(1);
//        }
//        return result;
//    }
//    //前几个月，integer类型
//    public static Integer[] getBeMonth(int num) {
//        final Integer[] result = new Integer[num];
//        LocalDate localDate = LocalDate.now();
//        for (int i = 0; i < num; i++) {
//            result[i] = (localDate.getMonthValue());
//            localDate = localDate.minusMonths(1);
//        }
//        return result;
//    }
}
