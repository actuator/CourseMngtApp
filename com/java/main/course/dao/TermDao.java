package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.Term;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;


/**
 * Class to provide Database Layer for the Term Table
 */

public class TermDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database database manager object
     */
    public TermDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add new Term into the Database.
     *
     * @param term that will be stored into database
     * @return true if term has been added to the database, false otherwise
     */
    public boolean addTerm(Term term) {

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TERM_COL_TITLE, term.getTitle());
        contentValues.put(Constants.TERM_COL_SDATE, Utility.dateToString(term.getStartDate()));
        contentValues.put(Constants.TERM_COL_EDATE, Utility.dateToString(term.getEndDate()));

        // insert the Term into Database
        long insertId = this.database.insert(Constants.TBL_TERM, null, contentValues);

        return insertId != -1;
    }

    /**
     * Function to update a given Term. The Term Id will not be changed as its a PK. Rest of the
     * attributes will be updated.
     *
     * @param term that will be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateTerm(Term term) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TERM_COL_TITLE, term.getTitle());
        contentValues.put(Constants.TERM_COL_SDATE, Utility.dateToString(term.getStartDate()));
        contentValues.put(Constants.TERM_COL_EDATE, Utility.dateToString(term.getEndDate()));

        // Call update method
        int updated = this.database.update(Constants.TBL_TERM, contentValues,
                Constants.TBL_TERMS_ID + "=?", new String[]{String.valueOf(term.getId())});

        // return result
        return updated >= 1;
    }

    /**
     * Function to delete a given Term. A term can only be deleted if it is not an FK (Foreign Key)
     * or linked to a Course. If there exist a Course linked with this term, it will not be deleted.
     * If it is not linked to any course, safely delete it.
     *
     * @param termId of the term going to be deleted.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteTerm(int termId) {

        int courseCount = getCourseCount(termId);

        if(courseCount == 0) {
            String predicates [] = new String[]{String.valueOf(termId)};
            return database.delete(Constants.TBL_TERM, Constants.TBL_TERMS_ID + "=?",
                    predicates) == 1;
        }

        return false;
    }

    /**
     * Function to count the number of courses this term id has been linked and used as a Foreign
     * Key. If there is no course exist linked to this Term Id, return 0.
     *
     * @param termId to see linked courses to this term
     * @return count of linked courses
     */
    private int getCourseCount(int termId) {

        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_COURSE, Constants.COURSE_COL_TERM_ID);
        String predicates [] = new String[]{String.valueOf(termId)};

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
     * Function to get the Collection of all the Terms from the Database Term Table.
     *
     * @return collection of terms
     */
    public ArrayList<Term> getTerms() {
        ArrayList<Term> terms = new ArrayList<Term>();

        // Read the Terms
        String sql = String.format("SELECT * FROM %s", Constants.TBL_TERM);

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int termId = cursor.getInt(0);
                String title = cursor.getString(1);
                Date startDate = Utility.stringToDate(cursor.getString(2));
                Date endDate = Utility.stringToDate((cursor.getString(3)));

                // term object
                Term term = new Term(termId, title, startDate, endDate);

                // store into list
                terms.add(term);
            }while(cursor.moveToNext());
        }

        return terms;
    }
}
