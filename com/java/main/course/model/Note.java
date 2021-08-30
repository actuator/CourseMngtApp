package com.java.main.course.model;

public class Note {

    /** Instance fields **/
    private int noteId;
    private String title;
    private String description;
    private int courseId;

    /**
     * Default Constructor to setup the Note object with default values.
     */
    public Note() {
    }

    /**
     * Parameter constructor to setup a Note object with given parameter values used
     * to initialize the data attributes of the object.
     *
     * @param noteId of this Note Object.
     * @param title of this Note Object.
     * @param description of this Note Object.
     * @param courseId of this Note Object.
     */
    public Note(int noteId, String title, String description, int courseId) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
    }

    /**
     * Function to get the NoteId of this Note Object.
     *
     * @return nodeId of this Note Object.
     */
    public int getNoteId() {
        return noteId;
    }

    /**
     * Function to set or update the nodeId of this Note Object.
     *
     * @param noteId of this Note Object.
     */
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    /**
     * Function to get the title of the Note object.
     *
     * @return title of the Note object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the Title of the Note object.
     *
     * @param title of the Note object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the description of the Note Object.
     *
     * @return description of the Note object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function to set the description of the Note object.
     *
     * @param description of the Note object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function to get the Course Id for which this Note has been created.
     *
     * @return courseId of the Note object.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Function set or update the Course Id of the Note object.
     *
     * @param courseId of the Note object.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    /**
     * Get the Note title as string
     *
     * @return string representation
     */
    public String toString() {
        return this.title;
    }
}
