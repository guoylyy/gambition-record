package com.gambition.recorder;

/**
 * Created by jingpeng on 2016/5/29.
 */
public class Utility {
    public static String covertToTimeString(long seconds) {
        long hour = seconds / 3600;
        long minute = (seconds % 3600) / 60;
        long second = ((seconds % 3600)) % 60;

        String hourStr, minuteStr, secondStr;
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = String.valueOf(hour);
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = String.valueOf(minute);
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = String.valueOf(second);
        }
        return hourStr + ":" + minuteStr + ":" + secondStr;
    }
}
