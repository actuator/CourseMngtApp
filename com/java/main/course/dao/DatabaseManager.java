package com.java.main.course.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.java.main.course.constants.Constants;


/**
 * Class to provide a Database Layer for the Sqlite Database that is a builtin database
 * The class will extend the SQLiteOpenHelper class to facilitate the Database Crude Operations
 * including DDL.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    // Database Parameters
    private static final String DB_NAME = "CourseDatabase";
    private static final int DATABASE_VERSION = 4;

    // database classes delegating table crude functionality.
    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private GoalDao goalDao;
    private NotesDao notesDao;
    private CourseAlertDao courseAlertDao;
    private GoalAlertDao goalAlertDao;

    /**
     * Constructor that receives a context object, used to create the database. This context
     * object will be passed to this class from the main activity.
     */
    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    /**
     * Function to create New database if not already created. If this function is called
     * we suppose to create all the tables here by executing the "Table Create" DDL statements.
     */
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Constants.CREATE_TBL_TERM);
        database.execSQL(Constants.CREATE_TBL_COURSE);
        database.execSQL(Constants.CREATE_TBL_ASSESSMENT);
        database.execSQL(Constants.CREATE_TBL_GOAL);
        database.execSQL(Constants.CREATE_TBL_NOTES);
        database.execSQL(Constants.CREATE_TBL_COURSE_ALERTS);
        database.execSQL(Constants.CREATE_TBL_GOAL_ALERTS);
    }

    @Override
    /**
     * Version Controlling, this function will upgrade the database if needed.
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // Drop all the tables and create again - Due to FKs drop tables in reverse
        // order of creation.
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_GOAL_ALERTS));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_COURSE_ALERTS));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_NOTES));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_GOAL));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_ASSESSMENT));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_COURSE));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s;", Constants.TBL_TERM));
        onCreate(database);
    }

    /**
     * Function to get the TermDao object to provide Database Layer for the Term Table.
     *
     * @return term dao instance
     */
    public TermDao getTermDao()  {

        if(this.termDao == null) {
            this.termDao = new TermDao(this.getWritableDatabase());
        }

        return this.termDao;
    }

    /**
     * Function to get the CourseDao object to provide Database Layer for the Course Table.
     *
     * @return Course dao instance
     */
    public CourseDao getCourseDao()  {

        if(this.courseDao == null) {
            this.courseDao = new CourseDao(this.getWritableDatabase());
        }

        return this.courseDao;
    }

    /**
     * Function to get the AssessmentDao object to provide Database Layer for the Assessment Table.
     *
     * @return Assessment dao instance
     */
    public AssessmentDao getAssessmentDao()  {

        if(this.assessmentDao == null) {
            this.assessmentDao = new AssessmentDao(this.getWritableDatabase());
        }

        return this.assessmentDao;
    }

    /**
     * Function to get the GoalDao object to provide Database Layer for the Goal Table.
     *
     * @return Goal dao instance
     */
    public GoalDao getGoalDao()  {

        if(this.goalDao == null) {
            this.goalDao = new GoalDao(this.getWritableDatabase());
        }

        return this.goalDao;
    }

    /**
     * Function to get the NotesDao object to provide Database Layer for the Notes Table.
     *
     * @return Notes dao instance
     */
    public NotesDao getNotesDao()  {

        if(this.termDao == null) {
            this.notesDao = new NotesDao(this.getWritableDatabase());
        }

        return this.notesDao;
    }

    /**
     * Function to get the CourseAlertDao object to provide Database Layer for the CourseAlert Table.
     *
     * @return CourseAlert dao instance
     */
    public CourseAlertDao getCourseAlertDao()  {

        if(this.courseAlertDao == null) {
            this.courseAlertDao = new CourseAlertDao(this.getWritableDatabase());
        }

        return this.courseAlertDao;
    }

    /**
     * Function to get the GoalAlertDao object to provide Database Layer for the GoalAlert Table.
     *
     * @return GoalAlert dao instance
     */
    public GoalAlertDao getGoalAlertDao()  {

        if(this.termDao == null) {
            this.goalAlertDao = new GoalAlertDao(this.getWritableDatabase());
        }

        return this.goalAlertDao;
    }
}
