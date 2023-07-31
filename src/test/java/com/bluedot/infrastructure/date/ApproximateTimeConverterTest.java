package com.bluedot.infrastructure.date;

import cn.hutool.core.date.DateUtil;
import com.bluedot.BaseTest;
import com.bluedot.infrastructure.date.type.ApproximateTimeConverter;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jason
 * @since 2023/07/31 - 20:00
 */
public class ApproximateTimeConverterTest extends BaseTest {
    @Test
    public void test(){
        ApproximateTimeConverter converter = new ApproximateTimeConverter();

        Date yearsAgo = new Date(2019-1900, Calendar.JULY, 31, 20, 4, 0);
        Date longTimeAgo = new Date(2023-1900, Calendar.FEBRUARY, 31, 20, 4, 0);
        Date daysAgo = new Date(2023-1900, Calendar.JULY, 26, 20, 4, 0);
        Date theDayBeforeYesterday = new Date(2023-1900, Calendar.JULY, 29, 20, 4, 0);
        Date yesterday = new Date(2023-1900, Calendar.JULY, 30, 20, 4, 0);
        Date hoursAgo = new Date(2023-1900, Calendar.JULY, 31, 15, 4, 0);
        Date minutesAgo = new Date(2023-1900, Calendar.JULY, 31, 20, 0, 0);
        Date just = new Date(2023-1900, Calendar.JULY, 31, 20, 3, 30);

        Date now = new Date(2023-1900, Calendar.JULY, 31, 20, 4, 0);

        Date rightAway = new Date(2023-1900, Calendar.JULY, 31, 20, 4, 30);
        Date minutesLater = new Date(2023-1900, Calendar.JULY, 31, 21, 2, 0);
        Date hoursLater = new Date(2023-1900, Calendar.JULY, 31, 5, 4, 0);
        Date tomorrow = new Date(2023-1900, Calendar.AUGUST, 1, 20, 4, 0);
        Date theDayAfterTomorrow = new Date(2023-1900, Calendar.AUGUST, 2, 20, 4, 0);
        Date DaysLater = new Date(2023-1900, Calendar.AUGUST, 4, 20, 4, 0);
        Date longTimeLater = new Date(2023-1900, Calendar.OCTOBER, 31, 20, 4, 0);
        Date yearsLater = new Date(2077-1900, Calendar.JULY, 31, 20, 4, 0);

        log.info(converter.convert(yearsAgo, now));
        log.info(converter.convert(longTimeAgo, now));
        log.info(converter.convert(daysAgo, now));
        log.info(converter.convert(theDayBeforeYesterday, now));
        log.info(converter.convert(yesterday, now));
        log.info(converter.convert(hoursAgo, now));
        log.info(converter.convert(minutesAgo, now));
        log.info(converter.convert(just, now));

        log.info(converter.convert(rightAway, now));
        log.info(converter.convert(minutesLater, now));
        log.info(converter.convert(hoursLater, now));
        log.info(converter.convert(tomorrow, now));
        log.info(converter.convert(theDayAfterTomorrow, now));
        log.info(converter.convert(DaysLater, now));
        log.info(converter.convert(longTimeLater, now));
        log.info(converter.convert(yearsLater, now));
    }
}
