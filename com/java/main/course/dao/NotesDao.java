package com.java.main.course.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.main.course.constants.Constants;
import com.java.main.course.model.Note;

import java.util.ArrayList;


/**
 * Database Layer for the Notes Tables
 */

public class NotesDao {

    private SQLiteDatabase database;

    /**
     * Class to create the instance of this Database layer with the reference of the
     * Database storing this table.
     *
     * @param database private database of this application
     */
    public NotesDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Function to add and store new Note Object into the database.
     *
     * @param note that is going to be stored into Database.
     * @return true if added otherwise false
     */
    public boolean addNote(Note note) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NOTES_COL_TITLE, note.getTitle());
        contentValues.put(Constants.NOTES_COL_DESC, note.getDescription());
        contentValues.put(Constants.NOTES_COL_COURSE_ID, note.getCourseId());

        // insert the Term into Database
        long insertId = this.database.insert(Constants.TBL_NOTES, null, contentValues);

        result =  insertId != -1;

        return result;
    }

    /**
     * Function to update a given Note. The Note Id will not be changed as
     * its a PK. Rest of the attributes will be updated.
     *
     * @param note that is going to be updated in the database.
     * @return true if updated, false otherwise
     */
    public boolean updateNote(Note note) {
        boolean result = false;

        // Create ContentValue object
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NOTES_COL_TITLE, note.getTitle());
        contentValues.put(Constants.NOTES_COL_DESC, note.getDescription());
        contentValues.put(Constants.NOTES_COL_COURSE_ID, note.getCourseId());

        int updated = this.database.update(Constants.TBL_NOTES, contentValues,
                Constants.TBL_NOTES_ID + "=?", new String[]{String.valueOf(note.getNoteId())});
        result = updated >= 1;

        return result;
    }

    /**
     * Function to delete a given Note. As a Note is a Leaf Table and not linked
     * to any other table through Foreign Key, it is safe to delete it.
     *
     * @param noteId id of the Note going to be deleted
     * @return true if deleted, false otherwise.
     */
    public boolean deleteNote(int noteId) {
        boolean result = false;
        String predicates [] = new String[]{String.valueOf(noteId)};
        result = database.delete(Constants.TBL_NOTES, Constants.TBL_NOTES_ID + "=?",
                predicates) == 1;
        return result;
    }

    /**
     * Function to count the number of Notes associated for a given Course Id.
     *
     * If there is no note exist for a given Note, return 0.
     *
     * @param courseId course for which notes are going to be counted
     * @return count of notes
     */
    private int getNoteCount(int courseId) {

        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s=?",
                Constants.TBL_NOTES, Constants.NOTES_COL_COURSE_ID);
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
     * Function to get the Collection of all the Note from the Database Note Table.
     *
     * @return collection of Note
     */
    public ArrayList<Note> getNotes() {
        ArrayList<Note> list = new ArrayList<Note>();

        // Read the Terms
        String sql = String.format("SELECT * FROM %s", Constants.TBL_NOTES);

        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                // Parse Data fields
                int noteId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                int courseId = cursor.getInt(3);

                // Note object
                Note note = new Note(noteId, title, description, courseId);

                // store into list
                list.add(note);
            } while (cursor.moveToNext());

        }

        return list;

    }
}
