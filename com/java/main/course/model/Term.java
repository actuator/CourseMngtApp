package com.java.main.course.model;

/**
 * Entity class to represent an Entity in the System.
 */

import java.util.Date;

public class Term {

    /** Instance fields **/
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;

    /**
     * Default constructor to instantiate a Term with default values.
     */
    public Term() {
    }

    /**
     * Parameter constructor to setup a Term with given data fields values passed
     * as parameter. All the attributes will be initialized.
     *
     * @param id of this Term Object.
     * @param title of this Term Object.
     * @param startDate of this Term Object.
     * @param endDate of this Term Object.
     */
    public Term(int id, String title, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Function to get the id of this Term Object.
     *
     * @return id of this Term Object.
     */
    public int getId() {
        return id;
    }

    /**
     * Function to set or update the Id of this Term Object.
     *
     * @param id of this Term Object.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function to get the title of this Term Object.
     *
     * @return title of this Term Object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the title of this Term Object.
     *
     * @param title of this Term Object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the Start Date of this Term Object.
     *
     * @return start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Function to set or update the Start Date of this Term Object.
     *
     * @param startDate of this Term Object.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Function to get the end date of this Term Object.
     *
     * @return end date of this Term Object.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Function to set or update the end date of this Term Object.
     *
     * @param endDate of this Term Object.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    /**
     * Function to produce the string representation of the Term.
     *
     * @return title as string representation.
     */
    public String toString() {
        return this.title;
    }
}
