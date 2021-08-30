package com.java.main.course.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat ("MM/dd/yyyy");

    /**
     * Function to format a Date into MM/dd/yyyy format
     *
     * @param date
     * @return string representation of date
     */
    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Function to convert a String into Date object.
     *
     * @param text
     * @return date object
     */
    public static Date stringToDate(String text) {
        Date date = null;

        try {
            date = dateFormat.parse(text);
        } catch(ParseException pe) {
            pe.printStackTrace();
        }
        return date;
    }

    /**
     * Function to Compute number of days between two given dates.
     *
     * @param startDate start date
     * @param endDate end date
     * @return days
     */
    public static int daysBetweenTwoDates(Date startDate, Date endDate) {
        long ms = endDate.getTime() - startDate.getTime();
        double diff =  (ms * 1.0) / (24 * 60 * 60 * 1000);
        return (int) diff;
    }

    /**
     * Function to compute teh progress in terms of number of days passed.
     *
     * @param totalDays
     * @param consumedDays
     * @return progress
     */
    public static int getProgress(int totalDays, int consumedDays) {

        double daysLeft = consumedDays;

        // Percent left
        daysLeft = daysLeft / totalDays * 100;

        // percent consumed
        return (int) (daysLeft);

    }



}
