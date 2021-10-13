package com.crawler.scrapecom.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static String getDateTimeFormattedNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss");
        return ZonedDateTime.now().format(formatter);
    }
}
