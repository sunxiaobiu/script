package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeMillisToDate {

    public static Date convert(long currentTimeMillis){

        Date date = new Date(currentTimeMillis);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Australia/Victoria"));
        String dateFormatted = formatter.format(date);

        try {
            return formatter.parse(dateFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }
}
