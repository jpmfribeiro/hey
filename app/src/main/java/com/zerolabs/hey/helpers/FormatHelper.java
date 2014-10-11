package com.zerolabs.hey.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jpedro on 11.10.14.
 */
public class FormatHelper {

    // Date Format
    private static String DATE_FORMAT = "MM/dd/yyyy";

    public static String formatDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static Date getDateFromString(String creationDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date result;
        try {
            result = sdf.parse(creationDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

}
