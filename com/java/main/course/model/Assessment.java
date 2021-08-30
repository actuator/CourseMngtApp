package com.java.main.course.model;

import java.util.Date;

public class Assessment {

    /** Instance Fields **/
    private int assessmentId;
    private String title;
    private String description;
    private int courseId;
    private Date startDate;
    private Date endDate;
    private String type;

    /**
     * Default constructor of the Assessment object to initialize the instance with
     * default values.
     */
    public Assessment() {
        this.assessmentId = 0;
        this.title = "";
        this.description = "";
        this.courseId = 0;
        this.startDate = null;
        this.endDate = null;
        this.type = "";
    }

    /**
     * Parameter constructor to setup the Assessment instance with given parameter
     * values to initialize all the data attributes of the object.
     *
     * @param assessmentId of the Assessment object
     * @param title of the Assessment object
     * @param description of the Assessment object
     * @param courseId of the Assessment object
     * @param startDate of Assessment
     * @param endDate of Assessment
     * @param type of Assessment
     */
    public Assessment(int assessmentId, String title, String description, int courseId,
                      Date startDate, Date endDate, String type) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    /**
     * Function to get the Type of the Assessment.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Function to set the Type fo the Assessment.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Function to get the Start Date of this Assessment
     *
     * @return start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Function to set or update the Start Date of the Course Object.
     *
     * @param startDate of the Course Object.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Function to get the end date of the Course Object.
     *
     * @return end date of the Course Object.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Function to set or update the End date of the Course Object.
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Function to get the Assessment Id of the Assessment object
     *
     * @return assessment id of the Assessment object
     */
    public int getAssessmentId() {
        return assessmentId;
    }

    /**
     * Function to set or update the assessment id of the Assessment object.
     *
     * @param assessmentId of the Assessment object.
     */
    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**
     * Function to get the title of the Assessment object.
     *
     * @return title of the Assessment object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the Title of the Assessment object.
     *
     * @param title of the Assessment object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the description of the Assessment Object.
     *
     * @return description of the Assessment object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function to set the description of the Assessment object.
     *
     * @param description of the Assessment object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function to get the Course Id for which this assessment has been created.
     *
     * @return courseId of the Assessment object.
      */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Function set or update the Course Id of the Assessment object.
     *
     * @param courseId of the Assessment object.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    /**
     * Get the String representation of the Assessment
     *
     * @return String representation
     */
    public String toString() {
        return this.title;
    }
}
