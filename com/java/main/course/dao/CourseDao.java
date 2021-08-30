package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.Course;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

/**
 * Database Layer for the Course Table
 */

public class CourseDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database private database for this application
     */
    public CourseDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add and store new Course Object into the database.
     *
     * @param course that is going to be stored into Database.
     * @return true if added otherwise false
     */
    public boolean addCourse(Course course) {
        boolean result = false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COURSE_COL_TITLE, course.getTitle());
        contentValues.put(Constants.COURSE_COL_SDATE, Utility.dateToString(course.getStartDate()));
        contentValues.put(Constants.COURSE_COL_EDATE, Utility.dateToString(course.getEndDate()));
        contentValues.put(Constants.COURSE_COL_STATUS, course.getStatus());
        contentValues.put(Constants.COURSE_COL_INS_NAME, course.getInstructorName());
        contentValues.put(Constants.COURSE_COL_INS_PHONE, course.getInstructorPhone());
        contentValues.put(Constants.COURSE_COL_INS_EMAIL, course.getInstructorEmail());
        contentValues.put(Constants.COURSE_COL_TERM_ID, course.getTermId());

        // insert the Term into Database
        long insertId = this.database.insert(Constants.TBL_COURSE,
                null, contentValues);

        result = insertId != -1;

        return result;
    }

    /**
     * Function to update a given Course. The Course Id will not be changed as
     * its a PK. Rest of the attributes will be updated.
     *
     * @param course that is going to be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateCourse(Course course) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COURSE_COL_TITLE, course.getTitle());
        contentValues.put(Constants.COURSE_COL_SDATE, Utility.dateToString(course.getStartDate()));
        contentValues.put(Constants.COURSE_COL_EDATE, Utility.dateToString(course.getEndDate()));
        contentValues.put(Constants.COURSE_COL_STATUS, course.getStatus());
        contentValues.put(Constants.COURSE_COL_INS_NAME, course.getInstructorName());
        contentValues.put(Constants.COURSE_COL_INS_PHONE, course.getInstructorPhone());
        contentValues.put(Constants.COURSE_COL_INS_EMAIL, course.getInstructorEmail());
        contentValues.put(Constants.COURSE_COL_TERM_ID, course.getTermId());

        System.out.println("ID: " + course.getCourseId());

        int updated = this.database.update(Constants.TBL_COURSE, contentValues,
                Constants.TBL_COURSE_ID + "=?", new String[]{String.valueOf(course.getCourseId())});

        // return result
        result = updated >= 1;
        return result;
    }

    /**
     * Function to delete a given Course. A course can only be deleted if it is not
     * an FK (Foreign Key) or linked to a Any Sub-Table. If there exist a Course
     * that has links in sub-tables, it will not be deleted. If it is not linked
     * to any course, safely delete it.
     *
     * @param courseId id of the course going to be deleted
     * @return true if deleted, false otherwise.
     */
    public boolean deleteCourse(int courseId) {
        boolean result = false;

        int assementCount = getAssessmentCount(courseId);

        // if no assessment linked
        if(assementCount == 0) {
            int goalCount = getGoalCount(courseId);

            // if not goal linked
            if(goalCount == 0) {
                int notesCount = getNotesCount(courseId);

                // if not notes linked
                if(notesCount == 0) {

                    int courseAlertCount = getCourseAlertCount(courseId);

                    // if not course alert linked, delete it
                    if(courseAlertCount == 0) {
                        String predicates [] = new String[]{String.valueOf(courseId)};
                        result = database.delete(Constants.TBL_COURSE,
                                Constants.TBL_COURSE_ID + "=?",
                                predicates) == 1;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Function to count the number of Assessments this course id has been linked
     * and used as a Foreign Key. If there is no Assessment exist linked to this course,
     * return 0 otherwise return the count of all the Assessments.
     *
     * @param courseId id of the course
     * @return count of linked assessments
     */
    private int getAssessmentCount(int courseId) {
        int count = 0;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_ASSESSMENT, Constants.ASSESSMENT_COL_COURSE_ID);
        String predicates [] = new String[]{String.valueOf(courseId)};

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
     * Function to count the number of Goals this course id has been linked
     * and used as a Foreign Key. If there is no Goal exist linked to this course,
     * return 0 otherwise return the count of all the Goals.
     *
     * @param courseId id of the course
     * @return count of linked goals
     */
    private int getGoalCount(int courseId) {
        int count = 0;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_GOAL, Constants.GOAL_COL_COURSE_ID);
        String predicates [] = new String[]{String.valueOf(courseId)};

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
     * Function to count the number of CourseAlert this course id has been linked
     * and used as a Foreign Key. If there is no CourseAlert exist linked to this course,
     * return 0 otherwise return the count of all the CourseAlert.
     *
     * @param courseId id of the course
     * @return count of linked CourseAlert
     */
    private int getCourseAlertCount(int courseId) {
        int count = 0;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_COURSE_ALERTS, Constants.CALERT_COL_COURSE_ID);
        String predicates [] = new String[]{String.valueOf(courseId)};

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
     * Function to count the number of Notes this course id has been linked
     * and used as a Foreign Key. If there is no Notes exist linked to this course,
     * return 0 otherwise return the count of all the Notes.
     *
     * @param courseId id of the course
     * @return count of linked CourseAlert
     */
    private int getNotesCount(int courseId) {
        int count = 0;

        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_NOTES, Constants.NOTES_COL_COURSE_ID);
        String predicates [] = new String[]{String.valueOf(courseId)};

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
     * Function to get the Collection of all the Courses from the Database Course Table.
     *
     * @return collection of course
     */
    public ArrayList<Course> getCourses() {
        ArrayList<Course> list = new ArrayList<Course>();

        // Read the Courses
        String sql = String.format("SELECT * FROM %s", Constants.TBL_COURSE);

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int courseId = cursor.getInt(0);
                String title = cursor.getString(1);
                Date startDate = Utility.stringToDate(cursor.getString(2));
                Date endDate = Utility.stringToDate((cursor.getString(3)));
                String status = cursor.getString(4);
                String instructorName = cursor.getString(5);
                String instructorPhone = cursor.getString(6);
                String instructorEmail = cursor.getString(7);
                int termId = cursor.getInt(8);

                // course
                Course course = new Course(courseId, title, startDate, endDate, status,
                        instructorName, instructorPhone, instructorEmail, termId);

                // store into list
                list.add(course);
            }while(cursor.moveToNext());
        }

        return list;
    }

}
