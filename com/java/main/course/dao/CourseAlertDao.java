package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.CourseAlert;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

/**
 * Database Layer for the Course Alerts Table
 */

public class CourseAlertDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database  private database for this application
     */
    public CourseAlertDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add and store new CourseAlert Object into the database.
     *
     * @param courseAlert that is going to be stored into Database.
     * @return true if added otherwise false
     */
    public boolean addCourseAlert(CourseAlert courseAlert) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.CALERT_COL_TITLE, courseAlert.getTitle());
        contentValues.put(Constants.CALERT_COL_DESC, courseAlert.getDescription());
        contentValues.put(Constants.CALERT_COL_SDATE, Utility.dateToString(courseAlert.getStartDate()));
        contentValues.put(Constants.CALERT_COL_EDATE, Utility.dateToString(courseAlert.getEndDate()));
        contentValues.put(Constants.CALERT_COL_COURSE_ID, courseAlert.getCourseId());

        // insert the Term into Database
        long insertId = this.database.insert(Constants.TBL_COURSE_ALERTS, null, contentValues);

        result = insertId != -1;

        return result;
    }

    public static final String CALERT_COL_TITLE = "Title";
    public static final String CALERT_COL_DESC = "Description";
    public static final String CALERT_COL_SDATE = "StartDate";
    public static final String CALERT_COL_EDATE = "EndDate";
    public static final String CALERT_COL_COURSE_ID = "CourseId"; // FK

    /**
     * Function to update a given CourseAlert. The courseAlertId will not be changed as
     * its a PK. Rest of the attributes will be updated.
     *
     * @param courseAlert that is going to be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateCourseAlert(CourseAlert courseAlert) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.CALERT_COL_TITLE, courseAlert.getTitle());
        contentValues.put(Constants.CALERT_COL_DESC, courseAlert.getDescription());
        contentValues.put(Constants.CALERT_COL_SDATE, Utility.dateToString(courseAlert.getStartDate()));
        contentValues.put(Constants.CALERT_COL_EDATE, Utility.dateToString(courseAlert.getEndDate()));
        contentValues.put(Constants.CALERT_COL_COURSE_ID, courseAlert.getCourseId());

        // insert the Term into Database
        long insertId = this.database.update(Constants.TBL_COURSE_ALERTS, contentValues,
                Constants.TBL_CALERT_ID + "=?", new String[]{String.valueOf(courseAlert.getCourseAlertId())});

        result = insertId != -1;

        return result;
    }

    /**
     * Function to delete a given CourseAlert identified by the courseAlertId. As this
     * table is a Leaf table and not linked to any sub-table. Safely delete it.
     *
     * @param courseAlertId id of the CourseAlert going to be deleted
     * @return true if deleted, false otherwise.
     */
    public boolean deleteCourseAlert(int courseAlertId) {
        boolean result = false;
        String predicates [] = new String[]{String.valueOf(courseAlertId)};
        result = database.delete(Constants.TBL_COURSE_ALERTS, Constants.TBL_CALERT_ID + "=?",
                predicates) == 1;
        return result;
    }

    /**
     * Function to get the Collection of all the CourseAlert from the Database
     * CourseAlert Table.
     *
     * @return collection of CourseAlert
     */
    public ArrayList<CourseAlert> getCourseAlerts() {
        ArrayList<CourseAlert> list = new ArrayList<CourseAlert>();

        // Read the Course Alerts
        String sql = String.format("SELECT * FROM %s", Constants.TBL_COURSE_ALERTS);

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int courseAlertId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                Date startDate = Utility.stringToDate(cursor.getString(3));
                Date endDate = Utility.stringToDate((cursor.getString(4)));
                int courseId = cursor.getInt(5);

                // Course alert object
                CourseAlert courseAlert = new CourseAlert(courseAlertId, title, description,
                        startDate, endDate, courseId);

                // store into list
                list.add(courseAlert);

            }while(cursor.moveToNext());
        }
        return list;
    }
}
