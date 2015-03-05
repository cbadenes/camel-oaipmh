package es.upm.oeg.camel;

import es.upm.oeg.camel.oaipmh.component.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeTest {


    @Test
    public void formatTest(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 00);
        calendar.setTimeZone(TimeZone.getTimeZone("Z"));

        System.out.println("TimeTest.formatTest: " + TimeUtils.current());

        Assert.assertEquals("2015-03-10T10:10:00Z", TimeUtils.toISO(calendar.getTimeInMillis(),"Zulu"));

    }

}
