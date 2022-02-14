package com.budymann.sysdsgn.twottar.twottarwriter.domain.helper;

import java.util.Calendar;

public class CalendarHelper {
    public static Calendar unixToCalendar(long unixTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime);
        return calendar;
    }
}
