package es.upm.oeg.camel.component;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

public class TimeUtils {

    /**
     * Current time (UTC)
     * @return
     */
    public static String current(){
        String timezone = "Zulu";//UTC
        return toISO(DateTime.now(DateTimeZone.forID(timezone)).getMillis(), timezone);
    }


    public static String toISO(Long time, String timeZone){

        DateTimeZone timezone = DateTimeZone.forID(timeZone);
        return ISODateTimeFormat.dateTimeNoMillis().withZone(timezone).print(time);
    }


}
