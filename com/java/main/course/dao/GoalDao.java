package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.Goal;

import java.util.ArrayList;

/**
 * Database Layer for the Goal Table
 */

public class GoalDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database private database of this application
     */
    public GoalDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add and store new Goal Object into the database.
     *
     * @param goal that is going to be stored into Database.
     * @return true if added otherwise false
     */
    public boolean addGoal(Goal goal) {
        boolean result = false;

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.GALERT_COL_TITLE, goal.getTitle());
        contentValues.put(Constants.GOAL_COL_DESC, goal.getDescription());
        contentValues.put(Constants.GOAL_COL_COURSE_ID, goal.getCourseId());

        // insert the Goal into Database
        long insertId = this.database.insert(Constants.TBL_GOAL, null, contentValues);

        result = insertId != -1;

        return result;
    }

    /**
     * Function to update a given Goal. The Goal Id will not be changed as
     * its a PK. Rest of the attributes will be updated.
     *
     * @param goal that is going to be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateGoal(Goal goal) {
        boolean result = false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.GALERT_COL_TITLE, goal.getTitle());
        contentValues.put(Constants.GOAL_COL_DESC, goal.getDescription());

        // Call update method

        int updated = this.database.update(Constants.TBL_GOAL, contentValues,
                Constants.TBL_GOAL_ID + "=?", new String[]{String.valueOf(goal.getGoalId())});

        // return result
        result = updated >= 1;

        return result;
    }

    /**
     * Function to delete a given Goal. A Goal can only be deleted if it is not
     * an FK (Foreign Key) or linked to a Any Sub-Table. If there exist a Goal
     * that has links in sub-tables, it will not be deleted. If it is not linked
     * to any course, safely delete it.
     *
     * @param goalId id of the Goal going to be deleted
     * @return true if deleted, false otherwise.
     */
    public boolean deleteGoal(int goalId) {
        boolean result = false;
        int courseCount = getGoalAlertCount(goalId);

        if(courseCount == 0) {
            String predicates [] = new String[]{String.valueOf(goalId)};
            result = database.delete(Constants.TBL_GOAL, Constants.TBL_GOAL_ID + "=?",
                    predicates) == 1;
        }

        return result;
    }

    /**
     * Function to count the number of GoalAlert this course id has been linked
     * and used as a Foreign Key. If there is no GoalAlert exist linked to this Goal,
     * return 0 otherwise return the count of all the GoalAlert.
     *
     * @param goalId id of the course
     * @return count of linked GoalAlert
     */
    private int getGoalAlertCount(int goalId) {
        int count = 0;

        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_GOAL_ALERTS, Constants.GALERT_COL_GOAL_ID);
        String predicates [] = new String[]{String.valueOf(goalId)};

        // Get the Cursor
        Cursor cursor = database.rawQuery(sql, predicates);

        if(cursor != null) {
            if(cursor.getColumnCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        return count;
    }

    /**
     * Function to get the Collection of all the Goals from the Database Goal Table.
     *
     * @return collection of Goals
     */
    public ArrayList<Goal> getGoals() {
        ArrayList<Goal> list = new ArrayList<Goal>();

        // Read the Terms
        String sql = String.format("SELECT * FROM %s", Constants.TBL_GOAL);

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int goalId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                int courseId = cursor.getInt(3);

                // term object
                Goal goal = new Goal(goalId, title, description, courseId);

                // store into list
                list.add(goal);

            }while(cursor.moveToNext());
        }

        return list;
    }
}
