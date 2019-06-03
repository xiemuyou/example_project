package com.doushi.library.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class DateUtil {

    public static final String FULL_DATE_PATTERN_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String YEAR = "yyyy年";
    public static final String DYNAMICS = "yyyy年MM月dd日";
    public static final String MONTH = "MM月";
    public static final String FULL_TIME = "HH:mm";
    public static final String VIDEO_TIME = "MM-dd HH:mm";
    public static final String WITHIN_A_YEAR = "MM-dd HH:mm";
    public static final String PURCHASE_TIEM = "yyyy年MM月dd号";
    public static final String MONTH_AND_YEAR = "yyyy年MM月";
    public static final String MORE_THAN_A_YEAR = "yy-MM-dd HH:mm";
    public static final String RELATION_TIME = "yyyy.MM.dd";
    private static final long SECONDS = 1000;
    private static final long POINTS = 60 * SECONDS;
    private static final long WHEN = 60 * POINTS;
    public static final long DAY = 24 * WHEN;
    private static int serverDifference = 0;

    public static String getString(Date date) {
        return date == null ? null : getString(date, DEFAULT_DATE_PATTERN);
    }

    public static String getString(Date date, String pattern) {
        String str = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            str = sf.format(date);
        } catch (Exception e) {
            LogUtil.w(e);
        }
        return str;
    }


    public static Date getDate(String dateStr) {
        return getDate(dateStr, DEFAULT_DATE_PATTERN);
    }

    public static Date getDate(String dateStr, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            date = sf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getCurrentTime() {
        return getCurrentTime(DEFAULT_DATE_PATTERN);
    }

    public static String getCurrentYear() {
        return getCurrentTime(YEAR);
    }

    public static String getCurrentMonth() {
        return getCurrentTime(MONTH);
    }

    public static String getCurrentTime(String pattern) {
        return getString(new Date(), pattern);
    }

    public static String getTime(long time) {
        return getTime(time, DEFAULT_DATE_PATTERN);
    }

    public static String getTime(long time, String pattern) {
        return getString(new Date(time), pattern);
    }

    public static Date getServerDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() - serverDifference * 1000);
        return calendar.getTime();
    }

    public static Date getAfterDate(Date date, int dayCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + dayCount * 1000 * 60 * 60 * 24 * dayCount);
        return calendar.getTime();
    }

    /**
     * 获取两日期相差的秒数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiff(Date startTime, Date endTime) {
        long diff = startTime.getTime() - endTime.getTime();
        diff = diff / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(diff));
    }

    /**
     * 判断日期相差几个月
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static double getMounthDiff(String startTime, String endTime) {
        String[] beginDates = startTime.split("-");
        String[] endDates = endTime.split("-");

        double len = (Integer.valueOf(endDates[0]) - Integer.valueOf(beginDates[0])) * 12 + (Integer.valueOf(endDates[1]) - Integer.valueOf(beginDates[1]));
        int day = Integer.valueOf(endDates[2]) - Integer.valueOf(beginDates[2]);
        if (day > 0) {
            len += 0.5;
        } else if (day < 0) {
            len -= 0.5;
        }
        return len;
    }

    public static String stringForTime(int timeMs) {
        StringBuilder timeFormat = new StringBuilder();
        Formatter timeFormatter = new Formatter(timeFormat, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        timeFormat.setLength(0);
        if (hours > 0) {
            return timeFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return timeFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private static final long seconds = 1000;
    private static final long points = 60 * seconds;
    private static final long when = 60 * points;
    private static final long day = 24 * when;
    private static final long week = 7 * day;
    private static final long month = 30 * day;
    private static final long year = 365 * day;

    /**
     * 时间规则
     *
     * @param commentsTimes 距当前时间一小时内	xx分钟前
     *                      小于24小时	xx小时前
     *                      超过24小时	xx-xx xx:xx （如07-12 14:34）
     *                      超过一年	xx-xx-xx xx:xx（如17-07-12 14:34）
     * @return
     */
    public static String getStringTime(long commentsTimes) {
        //+ 跟服务器相差时长
        commentsTimes = (commentsTimes + serverDifference);
        String commentTime;
        long time = System.currentTimeMillis() - (commentsTimes * 1000);
        if (time < points) {
            commentTime = "刚刚";
        } else if (time < when) {
            commentTime = (time / points) + "分钟前";
        } else {
            if (time < day) {
                commentTime = (time / when) + "小时前";
            } else {
                if (time < year) {
                    commentTime = getTime(commentsTimes * 1000, WITHIN_A_YEAR);
                } else {
                    commentTime = getTime(commentsTimes * 1000, MORE_THAN_A_YEAR);
                }
            }
        }
        return commentTime;
    }

    /**
     * 时间规则
     *
     * @return
     */
    public static String getDateTimeString(long commentsTimes) {
        //+ 跟服务器相差时长
        commentsTimes = (commentsTimes + serverDifference);
        String commentTime;
        long time = commentsTimes * 1000;
        if (time < points) {
            commentTime = time / 1000 + "秒";
        } else if (time < when) {
            commentTime = (time / points) + "分钟";
        } else {
            commentTime = (time / when) + "小时";
        }
        return commentTime;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    public static String getDataFormat(long formatTime) {
        String currentData = getCurrentTime();
        String formatData = getTime(formatTime);
        if (formatData.equals(currentData)) {
            return "今天";
        }
        String yestDay = getYesTodayDate();
        if (formatData.equals(yestDay)) {
            return "昨天";
        }
        String dynamics = getTime(formatTime, DYNAMICS);
        dynamics = dynamics.substring(2, dynamics.length());
        return dynamics;
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYesTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * @param date 格式20170912
     * @return
     */
    public static String getDateFormat(String date) {
        if (TextUtils.isEmpty(date) || date.length() < 8) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(date.substring(0, 4)).append("-").append(date.substring(4, 6)).append("-").append(date.substring(6, 8));
        String currentData = getCurrentTime();
        String formatData = sb.toString();
        LogUtil.info("Date util:" + sb.toString());
        if (formatData.equals(currentData)) {
            return "今天";
        }
        String yestDay = getYesTodayDate();
        if (formatData.equals(yestDay)) {
            return "昨天";
        }
        StringBuffer yearSb = new StringBuffer();
        yearSb.append(date.substring(0, 4)).append("年");
        if (getCurrentYear().equals(yearSb.toString())) {
            yearSb.delete(0, yearSb.length());
        }
        yearSb.append(date.substring(4, 5).equals("0") ? date.substring(5, 6) :
                date.substring(4, 6)).append("月").append(date.substring(6, 7).equals("0") ?
                date.substring(7, 8) : date.substring(6, 8)).append("日");
        return yearSb.toString();
    }

    /**
     * @param dateStr1 开始日期
     * @param days     加上这些天
     * @return 变成多少号
     */
    public static String getTimeAfterDays(String dateStr1, int days, String Pattern) {
        // 加上的天数
        SimpleDateFormat dateFormat = new SimpleDateFormat(Pattern);
        Date date1 = null;
        try {
            date1 = dateFormat.parse(dateStr1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        // 开始的日期相对于1970年1月1日的毫秒数+天数的毫秒数
        long addMill = 0;
        if (date1 != null) {
            addMill = date1.getTime() + days * 24 * 3600 * 1000;
        }
        cal.setTimeInMillis(addMill);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 判断当前时间跟服务器时间是否相差3小时
     *
     * @param eTime 比较时间,规则(yyyy-MM-dd HH:mm:ss)
     * @return 在 <= 3小时返回true,否则false
     */
    public static boolean timeBeforeThreeWhen(String eTime) {
        if (eTime == null || eTime.isEmpty()) {
            return false;
        }
        long commentsTimes = getDate(eTime, FULL_DATE_PATTERN).getTime();
        return (AppIntroUtil.getServiceTime() - commentsTimes) <= (DAY * 3);
    }

    /**
     * 判断当前时间跟服务器时间是否相差3小时
     *
     * @param commentsTimes 比较时间,规则(yyyy-MM-dd HH:mm:ss)
     * @return 在 <= 3小时返回true,否则false
     */
    public static boolean timeBeforeThreeWhen(long commentsTimes) {
        return (AppIntroUtil.getServiceTime() - commentsTimes) <= (DAY * 3);
    }
}


