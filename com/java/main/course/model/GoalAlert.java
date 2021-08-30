package com.java.main.course.model;

import java.util.Date;

public class GoalAlert {

    /**
     *  Instance fields of the goal Alert.
     */
    private int goalAlertId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int goalId;

    /**
     * Default constructor for the GoalAlert class to initialize the object with
     * default values.
     */
    public GoalAlert() {
    }

    /**
     * Parameter constructor to setup and initialize the GoalAlert object with given
     * parameter values to initialize the data attributes of the object.
     *
     * @param goalAlertId of the GoalAlert Object.
     * @param title of the GoalAlert Object.
     * @param description of the GoalAlert Object.
     * @param startDate of the GoalAlert Object.
     * @param endDate of the GoalAlert Object.
     * @param goalId of the GoalAlert Object.
     */
    public GoalAlert(int goalAlertId, String title, String description, Date startDate,
                     Date endDate, int goalId) {
        this.goalAlertId = goalAlertId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalId = goalId;
    }

    /**
     * Function to get the Goal Alert Id of the GoalAlert Object.
     *
     * @return goalAlertId of the GoalAlert Object.
     */
    public int getGoalAlertId() {
        return goalAlertId;
    }

    /**
     * Function to set or update the goal alert id of the GoalAlert Object.
     *
     * @param goalAlertId of the GoalAlert Object.
     */
    public void setGoalAlertId(int goalAlertId) {
        this.goalAlertId = goalAlertId;
    }

    /**
     * Function to get the Title of this GoalAlert object.
     *
     * @return title of this GoalAlert object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the Title of this GoalAlert object.
     *
     * @param title of this GoalAlert object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the description of this GoalAlert object.
     *
     * @return description of this GoalAlert object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function to set or update the description of this GoalAlert object.
     *
     * @param description of this GoalAlert object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function to get the start date of this GoalAlert object.
     *
     * @return startDate of this GoalAlert object.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Function to set or update the start date of this GoalAlert object.
     *
     * @param startDate of this GoalAlert object.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Function to get the End Date of this GoalAlert object.
     *
     * @return endDate of this GoalAlert object.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Function to set or update the end date of this GoalAlert object.
     *
     * @param endDate of this GoalAlert object.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Function to get the Course Id for which this alert has been created.
     *
     * @return courseId of this GoalAlert object.
     */
    public int getGoalId() {
        return goalId;
    }

    /**
     * Function to set or update the course id for which this GoalAlert has been
     * created.
     *
     * @param goalId of this GoalAlert object.
     */
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }
}
