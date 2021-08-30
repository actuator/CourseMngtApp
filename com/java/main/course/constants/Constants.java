package com.java.main.course.constants;

public class Constants {

    // Table Names
    public static final String TBL_TERM = "tblTerm";
    public static final String TBL_COURSE = "tblCourse";
    public static final String TBL_ASSESSMENT = "tblAssessment";
    public static final String TBL_GOAL = "tblGoal";
    public static final String TBL_NOTES = "tblNotes";
    public static final String TBL_COURSE_ALERTS= "tblCourseAlerts";
    public static final String TBL_GOAL_ALERTS = "tblGoalAlerts";

    // Table ID Fields
    public static final String TBL_TERMS_ID = "TermId";
    public static final String TBL_COURSE_ID = "CourseId";
    public static final String TBL_ASSESSMENT_ID = "AssessmentId";
    public static final String TBL_GOAL_ID = "GoalId";
    public static final String TBL_NOTES_ID = "NotesId";
    public static final String TBL_CALERT_ID = "CourseAlertId";
    public static final String TBL_GALERT_ID = "GoalAlertId";

    // Table Fields Names
    // Terms
    public static final String TERM_COL_TITLE = "Title";
    public static final String TERM_COL_SDATE = "StartDate";
    public static final String TERM_COL_EDATE = "EndDate";

    // Course
    public static final String COURSE_COL_TITLE = "Title";
    public static final String COURSE_COL_SDATE = "StartDate";
    public static final String COURSE_COL_EDATE = "EndDate";
    public static final String COURSE_COL_STATUS = "Status";
    public static final String COURSE_COL_INS_NAME = "InstructorName";
    public static final String COURSE_COL_INS_PHONE = "InstructorPhone";
    public static final String COURSE_COL_INS_EMAIL = "InstructorEmail";
    public static final String COURSE_COL_TERM_ID = "TermId"; // FK

    // Assessment
    public static final String ASSESSMENT_COL_TITLE = "Title";
    public static final String ASSESSMENT_COL_DESC = "Description";
    public static final String ASSESSMENT_COL_COURSE_ID = "CourseId"; // FK
    public static final String ASSESSMENT_COL_SDATE = "StartDate";
    public static final String ASSESSMENT_COL_EDATE = "EndDate";
    public static final String ASSESSMENT_COL_TYPE = "Type";


    // GOAL
    public static final String GOAL_COL_TITLE = "Title";
    public static final String GOAL_COL_DESC = "Description";
    public static final String GOAL_COL_COURSE_ID = "CourseId"; // FK

    // NOTES
    public static final String NOTES_COL_TITLE = "Title";
    public static final String NOTES_COL_DESC = "Description";
    public static final String NOTES_COL_COURSE_ID = "CourseId"; // FK

    // Course Alert
    public static final String CALERT_COL_TITLE = "Title";
    public static final String CALERT_COL_DESC = "Description";
    public static final String CALERT_COL_SDATE = "StartDate";
    public static final String CALERT_COL_EDATE = "EndDate";
    public static final String CALERT_COL_COURSE_ID = "CourseId"; // FK

    // Goal Alerts
    public static final String GALERT_COL_TITLE = "Title";
    public static final String GALERT_COL_DESC = "Description";
    public static final String GALERT_COL_SDATE = "StartDate";
    public static final String GALERT_COL_EDATE = "EndDate";
    public static final String GALERT_COL_GOAL_ID = "GoalId";   // FK

    // Create Table Queries.

    // TERMS
    public static final String CREATE_TBL_TERM = "CREATE TABLE " + TBL_TERM + " (\n" + " " + TBL_TERMS_ID
            + " INTEGER NOT NULL CONSTRAINT TERM_PK PRIMARY KEY AUTOINCREMENT, \n" + " " + TERM_COL_TITLE
            + " TEXT NOT NULL,\n" + " " + TERM_COL_SDATE + " TEXT NOT NULL, \n" + " " + TERM_COL_EDATE
            + " TEXT NOT NULL);";

    // COURSE
    public static final String CREATE_TBL_COURSE = "CREATE TABLE " + TBL_COURSE + "(\n" + " " + TBL_COURSE_ID
            + " INTEGER NOT NULL CONSTRAINT COURSE_PK PRIMARY KEY AUTOINCREMENT, \n" + " " + COURSE_COL_TITLE
            + " TEXT NOT NULL, \n" + " " + COURSE_COL_SDATE + " TEXT NOT NULL, \n" + " " + COURSE_COL_EDATE
            + " TEXT NOT NULL, \n" + " " + COURSE_COL_STATUS + " TEXT NOT NULL, \n" + " " + COURSE_COL_INS_NAME
            + " TEXT NOT NULL, \n" + " " + COURSE_COL_INS_PHONE + " TEXT NOT NULL, \n" + " " + COURSE_COL_INS_EMAIL
            + " TEXT NOT NULL, \n" + " " + COURSE_COL_TERM_ID
            + " INTEGER NOT NULL, FOREIGN KEY ("+ COURSE_COL_TERM_ID +") REFERENCES " + TBL_TERM + "(" + TBL_TERMS_ID + "));\n";

    // ASSESSMENT
    public static final String CREATE_TBL_ASSESSMENT = "CREATE TABLE " + TBL_ASSESSMENT + " (\n" + " "
            + TBL_ASSESSMENT_ID + " INTEGER NOT NULL CONSTRAINT ASSESSMENT_PK PRIMARY KEY AUTOINCREMENT,\n" + " "
            + ASSESSMENT_COL_TITLE + " TEXT NOT NULL,\n" + " " + ASSESSMENT_COL_DESC + " TEXT NOT NULL,\n" + " "
            + ASSESSMENT_COL_SDATE + " TEXT NOT NULL, \n" + " " + ASSESSMENT_COL_EDATE + " TEXT NOT NULL, \n "
            + ASSESSMENT_COL_COURSE_ID + " INTEGER NOT NULL, \n"
            + ASSESSMENT_COL_TYPE +  " TEXT NOT NULL,\n"
            +"FOREIGN KEY (" + ASSESSMENT_COL_COURSE_ID
            + ") REFERENCES " + TBL_COURSE + "(" + TBL_COURSE_ID + ")\n);\n";

    // GOAL
    public static final String CREATE_TBL_GOAL = "CREATE TABLE " + TBL_GOAL + " (\n" + " " + TBL_GOAL_ID
            + " INTEGER NOT NULL CONSTRAINT GOAL_PK PRIMARY KEY AUTOINCREMENT,\n" + " " + GOAL_COL_TITLE
            + " TEXT NOT NULL,\n" + " " + GOAL_COL_DESC + " TEXT NOT NULL,\n" + " " + GOAL_COL_COURSE_ID
            + " INTEGER NOT NULL, FOREIGN KEY ("+ GOAL_COL_COURSE_ID +") REFERENCES " + TBL_COURSE + "("
            + TBL_COURSE_ID + "));\n";

    // NOTES
    public static final String CREATE_TBL_NOTES = "CREATE TABLE " + TBL_NOTES + " (\n" + " " + TBL_NOTES_ID
            + " INTEGER NOT NULL CONSTRAINT NOTES_PK PRIMARY KEY AUTOINCREMENT,\n" + " " + NOTES_COL_TITLE
            + " TEXT NOT NULL,\n" + " " + NOTES_COL_DESC + " TEXT NOT NULL,\n" + " " + NOTES_COL_COURSE_ID
            + " INTEGER NOT NULL,  FOREIGN KEY (" + NOTES_COL_COURSE_ID + ") REFERENCES " + TBL_COURSE + "("
            + TBL_COURSE_ID + "));\n";

    // COURSE ALERTS
    public static final String CREATE_TBL_COURSE_ALERTS = "CREATE TABLE " + TBL_COURSE_ALERTS + " (\n" + " "
            + TBL_CALERT_ID + " INTEGER NOT NULL CONSTRAINT COURSE_ALERTS_PK PRIMARY KEY AUTOINCREMENT,\n" + " "
            + CALERT_COL_TITLE + " TEXT NOT NULL,\n" + " " + CALERT_COL_DESC + " TEXT NOT NULL,\n" + " "
            + CALERT_COL_SDATE + " TEXT NOT NULL,\n" + " " + CALERT_COL_EDATE + " TEXT NOT NULL,\n" + " "
            + CALERT_COL_COURSE_ID + " INTEGER NOT NULL,  FOREIGN KEY (" +CALERT_COL_COURSE_ID+ ") REFERENCES "
            + TBL_COURSE + "(" + TBL_COURSE_ID + "));\n";

    // GOAL ALERTS
    public static final String CREATE_TBL_GOAL_ALERTS = "CREATE TABLE " + TBL_GOAL_ALERTS + " (\n" + " "
            + TBL_GALERT_ID + " INTEGER NOT NULL CONSTRAINT COURSE_ALERTS_PK PRIMARY KEY AUTOINCREMENT,\n" + " "
            + GALERT_COL_TITLE + " TEXT NOT NULL,\n" + " " + GALERT_COL_DESC + " TEXT NOT NULL,\n" + " "
            + GALERT_COL_SDATE + " TEXT NOT NULL,\n" + " " + GALERT_COL_EDATE + " TEXT NOT NULL,\n" + " "
            + GALERT_COL_GOAL_ID + " INTEGER NOT NULL, FOREIGN KEY (" + GALERT_COL_GOAL_ID +") REFERENCES "
            + TBL_GOAL + "(" + TBL_GOAL_ID + "));\n";

}
