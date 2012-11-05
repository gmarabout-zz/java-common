package com.marabout.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *  An utility class for Date related operations.
 * 
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public final class DateUtils {

    private DateUtils() {
    }

    public static Calendar now() {
        return Calendar.getInstance();
    }

    public static Calendar today() {
        return org.apache.commons.lang.time.DateUtils.round( now(), Calendar.DATE );
    }

    public static Calendar yesterday() {
        Calendar today = today();
        int dayOfYear = today.get( Calendar.DAY_OF_YEAR );
        today.set( Calendar.DAY_OF_YEAR, dayOfYear - 1 );
        return today;
    }

    public static String format(Calendar calendar) {
        return SimpleDateFormat.getInstance().format( calendar.getTime() );
    }

    public static void main(String... args) {
        System.out.println( "Now: " + format( now() ) );
        System.out.println( "Today: " + format( today() ) );
        System.out.println( "Yesterday: " + format( yesterday() ) );
    }

}
