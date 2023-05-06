package com.yh.robot.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateUtils {

    public static final String DATE_FORMATTER_STR = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String SLASH_DATE_FORMATTER_STR = "yyyy/MM/dd";
    public static final String SLASH_MONTH_FORMATTER_STR = "yyyy/MM";


    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static List<String> getLastNDays(int days){
        List<String> dateList = new ArrayList<>();
        for(int i=0; i<days; i++){
            LocalDate date = LocalDate.now().minusDays(i);
            String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateList.add(dateString);
        }
        return dateList;
    }

    public static Date getNDaysAgo(int days){
        LocalDate localDate = LocalDate.now().minusDays(days);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param date 年-月-日
     * @return Date格式的年月日
     */
    public static Date stringToDateTime(String date) {
        DateTime dateTime = DateUtil.parse(date, DATE_FORMATTER_STR);
        return dateTime;
    }

    /**
     * 获取两天之间的日期
     *
     * @param
     * @param
     * @return 时间集合
     */
    public static List<Date> getBetweenDates(String startTime, String endTime) {
        Date startDate = DateUtils.stringToDateTime(startTime);
        Date endDate = DateUtils.stringToDateTime(endTime);
        return getBetweenDates(startDate, endDate);
    }

    /**
     * 日期集合转换
     *
     * @param
     * @param
     * @return 日期字符串集合
     */
    public static List<String> changeStringList(List<Date> dates, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return dates.stream().map(sdf::format).collect(Collectors.toList());
    }

    /**
     * 获取两天之间的日期
     *
     * @param
     * @param
     * @return 时间集合
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        start = DateUtil.beginOfDay(start);
        end = DateUtil.beginOfDay(end);
        end = addDay(end, 1);
        List<Date> days = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            days.add(tempStart.getTime());
            tempStart.add(Calendar.DATE, 1);
        }
        return days;
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
}
