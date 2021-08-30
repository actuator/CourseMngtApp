package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.GoalAlert;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

/**
 * Database Layer for the Goal Alerts Table
 */

public class GoalAlertDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database private database of this application
     */
    public GoalAlertDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add and store new GoalAlert Object into the database.
     *
     * @param goalAlert that is going to be stored into Database.
     * @return true if added otherwise false
     */
    public boolean addGoalAlert(GoalAlert goalAlert) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.GALERT_COL_TITLE, goalAlert.getTitle());
        contentValues.put(Constants.GALERT_COL_DESC, goalAlert.getDescription());
        contentValues.put(Constants.GALERT_COL_SDATE, Utility.dateToString(goalAlert.getStartDate()));
        contentValues.put(Constants.GALERT_COL_EDATE, Utility.dateToString(goalAlert.getEndDate()));
        contentValues.put(Constants.GALERT_COL_GOAL_ID, goalAlert.getGoalId());

        // insert the Term into Database
        long insertId = this.database.insert(Constants.TBL_TERM, null, contentValues);

        result = insertId != -1;

        return result;
    }

    /**
     * Function to update a given GoalAlert. The goalAlertId will not be changed as
     * its a PK. Rest of the attributes will be updated.
     *
     * @param goalAlert that is going to be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateGoalAlert(GoalAlert goalAlert) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.GALERT_COL_TITLE, goalAlert.getTitle());
        contentValues.put(Constants.GALERT_COL_DESC, goalAlert.getDescription());
        contentValues.put(Constants.GALERT_COL_SDATE, Utility.dateToString(goalAlert.getStartDate()));
        contentValues.put(Constants.GALERT_COL_EDATE, Utility.dateToString(goalAlert.getEndDate()));

        int updated = this.database.update(Constants.TBL_GOAL_ALERTS, contentValues,
                Constants.TBL_GALERT_ID + "=?", new String[]{String.valueOf(goalAlert.getGoalAlertId())});

        // return result
        result = updated >= 1;
        return result;
    }

    /**
     * Function to delete a given GoalAlert identified by the goalAlertId. As this
     * table is a Leaf table and not linked to any sub-table. Safely delete it.
     *
     * @param goalAlertId id of the CourseAlert going to be deleted
     * @return true if deleted, false otherwise.
     */
    public boolean deleteGoalAlert(int goalAlertId) {
        boolean result = false;

        String predicates [] = new String[]{String.valueOf(goalAlertId)};

        result = database.delete(Constants.TBL_GOAL_ALERTS, Constants.TBL_GALERT_ID + "=?",
                predicates) == 1;
        return result;
    }

    /**
     * Function to get the Collection of all the CourseAlert from the Database
     * CourseAlert Table.
     *
     * @return collection of CourseAlert
     */
    public ArrayList<GoalAlert> getGoalAlerts() {
        ArrayList<GoalAlert> list = new ArrayList<GoalAlert>();

        // Read the Terms
        String sql = String.format("SELECT * FROM %s", Constants.TBL_GOAL_ALERTS);

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int goalAlertId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                Date startDate = Utility.stringToDate(cursor.getString(3));
                Date endDate = Utility.stringToDate((cursor.getString(4)));
                int goalId = cursor.getInt(5);

                // GoalAlert object
                GoalAlert alert = new GoalAlert(goalAlertId, title, description, startDate, endDate, goalId);

                // store into list
                list.add(alert);

            }while(cursor.moveToNext());
        }


        return list;
    }
}
