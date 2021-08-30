package com.java.main.course.model;


import java.util.Date;

/**
 * Class to represent a Course in the System.
 */
public class Course {

    // Course instance fields
    private int courseId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termId;

    /**
     * Default constructor for the Course to initialize the Course with default values.
     */
    public Course() {
        this.courseId = 0;
        this.title = "";
        this.startDate = null;
        this.endDate = null;
        this.status = "";
        this.instructorName = "";
        this.instructorPhone = "";
        this.instructorEmail = "";
        this.termId = 0;
    }

    /**
     * Parameter Constructor for the Course class, initialize the object instance with
     * given parameter values. All the data attributes will be initialized with parameter
     * values.
     *
     * @param courseId of the Course Object.
     * @param title of the Course Object.
     * @param startDate of the Course Object.
     * @param endDate of the Course Object.
     * @param status of the Course Object.
     * @param instructorName of the Course Object.
     * @param instructorPhone of the Course Object.
     * @param instructorEmail of the Course Object.
     * @param termId of the Course Object.
     */
    public Course(int courseId, String title, Date startDate, Date endDate, String status,
                  String instructorName, String instructorPhone, String instructorEmail,
                  int termId) {
        this.courseId = courseId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.termId = termId;
    }

    /**
     * Function to get the Course Id of the Course Object.
     *
     * @return course id of the Course Object.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Function to set or update the Course Id of the Course Object.
     *
     * @param courseId of the Course Object.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Function to get the Title of the Course Object.
     *
     * @return title of the Course Object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set or update the title of the Course Object.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to get the start date of the Course Object.
     *
     * @return startDate of the Course Object.
     */
    public Date getStartDate() {
        return startDate;
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
     * Function to get the status of the Course Object.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function to set or update the status of the course.
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Function to get the Instructor Name of the Course Object.
     *
     * @return instructor name of the Course Object.
     */
    public String getInstructorName() {
        return instructorName;
    }

    /**
     * Function to set or update the instructor name of the Course Object.
     *
     * @param instructorName  of the Course Object.
     */
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    /**
     * Function to get the instructor phone of the Course Object.
     *
     * @return instructor phone
     */
    public String getInstructorPhone() {
        return instructorPhone;
    }

    /**
     * Function to set or update the Instructor Phone of the Course Object.
     *
     * @param instructorPhone of the Course Object.
     */
    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    /**
     * Function to get the Instructor's email of the Course Object.
     *
     * @return instructor email
     */
    public String getInstructorEmail() {
        return instructorEmail;
    }

    /**
     * Function to set or update the Instructor Email of the Course Object.
     *
     * @param instructorEmail of the Course Object.
     */
    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    /**
     * Function to get the Term Id this course has been linked.
     *
     * @return term id
     */
    public int getTermId() {
        return termId;
    }

    /**
     * Function to get or set the Term Id of the Course Object.
     *
     * @param termId of the Course Object.
     */
    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    /**
     * return String representation of the Course as title
     *
     * @return title
     */
    public String toString() {
        return this.title;
    }
}
