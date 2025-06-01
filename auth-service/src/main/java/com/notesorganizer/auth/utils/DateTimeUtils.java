package com.notesorganizer.auth.utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateTimeUtils {

    private DateTimeUtils(){}

    public static Timestamp getCurrentSQLTimestamp() {
        return new Timestamp((new Date()).getTime());
    }

    public static Timestamp getSQLTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

}
