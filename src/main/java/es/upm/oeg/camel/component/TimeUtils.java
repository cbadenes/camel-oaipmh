package es.upm.oeg.camel.component;


import org.joda.time.format.ISODateTimeFormat;

public class TimeUtils {

    public static String current(){
        return ISODateTimeFormat.dateTimeNoMillis().print(System.currentTimeMillis());
    }

}
