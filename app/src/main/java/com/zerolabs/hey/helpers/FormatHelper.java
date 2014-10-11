package com.zerolabs.hey.helpers;

import android.text.TextUtils;

import com.zerolabs.hey.model.User;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by jpedro on 11.10.14.
 */
public class FormatHelper {

    // Date Format
    private static String DATE_FORMAT = "MM/dd/yyyy";

    public static String formatDateToString(Date date){
        if(date == null) return null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static Date getDateFromString(String creationDateStr) {
        if(TextUtils.isEmpty(creationDateStr)) return null;
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

    public static Date getDateFromJSON(JSONObject dateJson) {

        if (dateJson == null) return null;

        Date result = new Date();

        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.set(Calendar.YEAR, dateJson.getInt(User.JSONRep.KEY_YEAR));
            cal.set(Calendar.MONTH, dateJson.getInt(User.JSONRep.KEY_MONTH));
            cal.set(Calendar.DAY_OF_MONTH, dateJson.getInt(User.JSONRep.KEY_DAY));
            result = cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;

    }
}
