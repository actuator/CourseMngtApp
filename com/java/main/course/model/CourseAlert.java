package com.java.main.course.model;

import java.util.Date;

public class CourseAlert {

    /**
     *  Instance fields of the Course Alert.
     */
    private int courseAlertId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int courseId;

    /**
     * Default constructor to initialize the Course Alert object with default
     * values.
     */
    public CourseAlert() {
    }

    /**
     * Parameter constructor for the Course Alert object to initialize the instance
     * with given parameter values for each of the data attributes.
     *
     * @param courseAlertId of this instance
     * @param title of this instance
     * @param description of this instance
     * @param startDate of this instance
     * @param endDate of this instance
     * @param courseId of this instance
     */
    public CourseAlert(int courseAlertId, String title, String description, Date startDate, Date endDate, int courseId) {
        this.courseAlertId = courseAlertId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    /**
     * Function to get the Course Alert Id of this CourseAlert object.
     *
     * @return courseAlertId of this CourseAlert object.
     */
    public int getCourseAlertId() {
        return courseAlertId;
    }

    /**
     * Function to set or update the Course Alert Id of this CourseAlert object.
     *
     * @param courseAlertId of this CourseAlert object.
     */
    public void setCourseAlertId(int courseAlertId) {
        this.courseAlertId = courseAlertId;
    }

    /**
     * Function to get the Title of this CourseAlert object.
     *
     * @return title of this CourseAlert object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the Title of this CourseAlert object.
     *
     * @param title of this CourseAlert object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the description of this CourseAlert object.
     *
     * @return description of this CourseAlert object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function to set or update the description of this CourseAlert object.
     *
     * @param description of this CourseAlert object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function to get the start date of this CourseAlert object.
     *
     * @return startDate of this CourseAlert object.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Function to set or update the start date of this CourseAlert object.
     *
     * @param startDate of this CourseAlert object.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Function to get the End Date of this CourseAlert object.
     *
     * @return endDate of this CourseAlert object.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Function to set or update the end date of this CourseAlert object.
     *
     * @param endDate of this CourseAlert object.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Function to get the Course Id for which this alert has been created.
     *
     * @return courseId of this CourseAlert object.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Function to set or update the course id for which this CourseAlert has been
     * created.
     *
     * @param courseId of this CourseAlert object.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
