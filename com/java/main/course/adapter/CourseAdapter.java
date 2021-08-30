package com.java.main.course.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.coursemanagementapp.EditCourse;
import com.java.main.course.coursemanagementapp.R;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Course;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

public class CourseAdapter extends ArrayAdapter<Course> {

    private Context context;
    private int layoutRes;
    private ArrayList<Course> courses;
    private DatabaseManager databaseManager;

    /**
     * Constructor to setup the Course Adapter with given parameter values for the database, context
     * layotu id, and course list.
     *
     * @param context view context
     * @param layoutRes layout of list
     * @param courses list of courses
     * @param databaseManager database manager
     */
    public CourseAdapter(Context context, int layoutRes, ArrayList<Course> courses,
                       DatabaseManager databaseManager) {
        super(context, layoutRes, courses);
        this.context = context;
        this.layoutRes = layoutRes;
        this.courses = courses;
        this.databaseManager = databaseManager;
    }

    /**
     * Function to produce a Row the Term List view.
     *
     * @param position of the list view
     * @param convertView view
     * @param parent container
     * @return view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes, null);

        if (position % 2 == 1) {
            view.setBackgroundResource(R.color.rowColor);
        } else {
            view.setBackgroundResource(R.color.alternateRowColor);
        }

        // Get the course object at given position
        final Course course = courses.get(position);

        // set to the text view, its title
        TextView titleView = view.findViewById(R.id.textViewCourseListTitle);
        titleView.setText(course.getTitle());

        ProgressBar bar = view.findViewById(R.id.progressBarCourse);

        int totalDays = Utility.daysBetweenTwoDates(course.getStartDate(), course.getEndDate());
        Date today = new Date();
        today.setTime(System.currentTimeMillis());
        int progress = 0;

        // If a course is in progress
        if(course.getStatus().equals("In Progress")) {
            // if End date already passed
            if (course.getEndDate().compareTo(today) <= 0) {
                progress = 100;
            } else {
                // if not yet started
                if (course.getStartDate().compareTo(today) > 0) {
                    progress = 0;
                } else {
                    // compute days consumed
                    int daysConsumed = Utility.daysBetweenTwoDates(course.getStartDate(), today);

                    progress = Utility.getProgress(totalDays, daysConsumed);
                }
            }
        } else if(course.getStatus().equals("Plan to Take")) {
            progress = 0;  // course is not taken yet
        } else {
            progress = 100; // Course is dropped or completed.
        }
        bar.setProgress(progress);


        ImageButton viewButton = view.findViewById(R.id.buttonListCourseView);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCourse(course);
            }
        });

        return view;
    }

    /**
     * Function to load Course Details in the view with Edit and Delete buttons on it
     * to provide the user functions to edit/delete or just view the details of the course.
     *
     * @param course
     */
    private void loadCourse(Course course) {
        Bundle bundle = new Bundle();
        bundle.putInt("courseId", course.getCourseId());
        bundle.putString("title", course.getTitle());
        bundle.putString("startDate", Utility.dateToString(course.getStartDate()));
        bundle.putString("endDate", Utility.dateToString(course.getEndDate()));
        bundle.putString("status", course.getStatus());
        bundle.putString("instructorName", course.getInstructorName());
        bundle.putString("instructorPhone", course.getInstructorPhone());
        bundle.putString("instructorEmail", course.getInstructorEmail());
        bundle.putInt("termId", course.getTermId());
        bundle.putBoolean("edit", true);

        Fragment fragment = new EditCourse();
        fragment.setArguments(bundle);
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Function to Load all the Employees form the database.
     */
    public void loadCourses() {
        courses = databaseManager.getCourseDao().getCourses();
        notifyDataSetChanged();
    }
}
