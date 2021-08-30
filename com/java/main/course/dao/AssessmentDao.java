package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.Assessment;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

/**
 * Database Layer for the Assessment Table
 */

public class AssessmentDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database private database of this application.
     */
    public AssessmentDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add and store new Assessment Object into the database.
     *
     * @param assessment that is going to be stored into Database.
     * @return true if added otherwise false
     */
    public boolean addAssessment(Assessment assessment) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.ASSESSMENT_COL_TITLE, assessment.getTitle());
        contentValues.put(Constants.ASSESSMENT_COL_DESC, assessment.getDescription());
        contentValues.put(Constants.ASSESSMENT_COL_SDATE, Utility.dateToString(assessment.getStartDate()));
        contentValues.put(Constants.ASSESSMENT_COL_EDATE, Utility.dateToString(assessment.getEndDate()));
        contentValues.put(Constants.ASSESSMENT_COL_COURSE_ID, assessment.getCourseId());
        contentValues.put(Constants.ASSESSMENT_COL_TYPE, assessment.getType());

        // insert the Term into Database
        long insertId = this.database.insert(Constants.TBL_ASSESSMENT,
                null, contentValues);

        result = insertId != -1;

        return result;
    }

    /**
     * Function to update a given Assessment. The Assessment Id will not be changed as
     * its a PK. Rest of the attributes will be updated.
     *
     * @param assessment that is going to be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateAssessment(Assessment assessment) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.ASSESSMENT_COL_TITLE, assessment.getTitle());
        contentValues.put(Constants.ASSESSMENT_COL_DESC, assessment.getDescription());
        contentValues.put(Constants.ASSESSMENT_COL_SDATE, Utility.dateToString(assessment.getStartDate()));
        contentValues.put(Constants.ASSESSMENT_COL_EDATE, Utility.dateToString(assessment.getEndDate()));
        contentValues.put(Constants.ASSESSMENT_COL_COURSE_ID, assessment.getCourseId());
        contentValues.put(Constants.ASSESSMENT_COL_TYPE, assessment.getType());

        int updated = this.database.update(Constants.TBL_ASSESSMENT, contentValues,
                Constants.TBL_ASSESSMENT_ID + "=?",
                new String[]{String.valueOf(assessment.getAssessmentId())});

        // return result
        result = updated >= 1;

        return result;
    }

    /**
     * Function to count the number of Assessments associated for a given Course Id.
     *
     * If there is no note exist for a given Note, return 0.
     *
     * @param courseId course for which notes are going to be counted
     * @return count of Assessment
     */
    public int getAssessmentCount(int courseId) {

        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_ASSESSMENT, Constants.ASSESSMENT_COL_COURSE_ID);
        String predicates [] = new String[]{String.valueOf(courseId)};

        // Get the Cursor
        Cursor cursor = database.rawQuery(sql, predicates);

        int count = 0;

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
     * Function to delete a given Assessment. As a Assessment is a Leaf Table and not linked
     * to any other table through Foreign Key, it is safe to delete it.
     *
     * @param assessmentId id of the Note going to be deleted
     * @return true if deleted, false otherwise.
     */
    public boolean deleteAssessment(int assessmentId) {
        boolean result = false;
        String predicates [] = new String[]{String.valueOf(assessmentId)};
        result = database.delete(Constants.TBL_ASSESSMENT,
                Constants.TBL_ASSESSMENT_ID + "=?",
                predicates) == 1;
        return result;
    }

    /**
     * Function to get the Collection of all the Note from the Database Assessment Table.
     *
     * @return collection of Assessment
     */
    public ArrayList<Assessment> getAssessments() {
        ArrayList<Assessment> list = new ArrayList<Assessment>();

        // Read the courses
        String sql = String.format("SELECT * FROM %s", Constants.TBL_ASSESSMENT);

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int assessmentId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                Date startDate = Utility.stringToDate(cursor.getString(3));
                Date endDate = Utility.stringToDate((cursor.getString(4)));
                int courseId = cursor.getInt(5);
                String type = cursor.getString(6);

                // Assessment object
                Assessment assessment = new Assessment(assessmentId, title, description, courseId, startDate, endDate, type);

                // store into list
                list.add(assessment);
            }while(cursor.moveToNext());
        }

        return list;
    }
}
