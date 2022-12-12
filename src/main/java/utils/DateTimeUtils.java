package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils  extends LoggerUtils{

    public static void wait (int seconds){
        try{
            Thread.sleep(1000L*seconds);

        }catch (InterruptedException e){
            log.warn("Interupted exception in Thread.sleep()"+e.getMessage());
        }

    }

    public static Date getCurrentDateTime(){

        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getDateTime(long milliseconds){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return cal.getTime();
    }

    //https://docs.oracle.com/javase/10/docs/api/java/text/SimpleDateFormat.html
    //dd-MM-yyyy HH:mm:ss zzz
    public static String getFormattedDateTime(Date date, String pattern){

        DateFormat dateFormat =  new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getFormattedCurrentDateTime(String pattern){
        Date date= getCurrentDateTime();
        return getFormattedDateTime(date, pattern);

    }

    public static String getDateTimeStamp(){
        return getFormattedCurrentDateTime("yyMMddHHmmss");
    }

    public static boolean compareDateTimes(Date date1, Date date2, int threshold) {
        long diff = (date2.getTime() - date1.getTime()) / 1000;
        log.debug("Comparing dates (Date 1: " + date1 + ", Date 2: " + date2 + "). Difference: " + diff + " seconds. Threshold: " + threshold + ".");
        return Math.abs(diff) <= threshold;
    }
}
