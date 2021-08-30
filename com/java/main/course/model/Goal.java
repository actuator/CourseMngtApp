package com.java.main.course.model;

public class Goal {
    
    /** Instance Fields */
    private int goalId;
    private String title;
    private String description;
    private int courseId;

    /**
     * Default constructor to setup the Goal Object with default values.
     */
    public Goal() {
    }

    /**
     * Parameter constructor to setup and initialize the Goal object with given
     * parameter values passed to the constructor as parameter.
     *
     * @param goalId of this Goal Object.
     * @param title of this Goal Object.
     * @param description of this Goal Object.
     * @param courseId of this Goal Object.
     */
    public Goal(int goalId, String title, String description, int courseId) {
        this.goalId = goalId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
    }

    /**
     * Function to get the Goal Id of this Goal Object.
     *
     * @return goalId of this Goal Object.
     */
    public int getGoalId() {
        return goalId;
    }

    /**
     * Function to set or update the goalId of this Goal Object.
     *
     * @param goalId of this Goal Object.
     */
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    /**
     * Function to get the title of the Goal object.
     *
     * @return title of the Goal object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the Title of the Goal object.
     *
     * @param title of the Goal object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the description of the Goal Object.
     *
     * @return description of the Goal object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Function to set the description of the Goal object.
     *
     * @param description of the Goal object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function to get the Course Id for which this Goal has been created.
     *
     * @return courseId of the Goal object.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Function set or update the Course Id of the Goal object.
     *
     * @param courseId of the Goal object.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
