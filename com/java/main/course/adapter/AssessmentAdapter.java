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

import com.java.main.course.coursemanagementapp.EditAssessment;
import com.java.main.course.coursemanagementapp.R;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Assessment;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

public class AssessmentAdapter extends ArrayAdapter<Assessment> {

    private Context context;
    private int layoutRes;
    private ArrayList<Assessment> assessments;
    private DatabaseManager databaseManager;

    /**
     * Constructor to setup the Assessment Adapter to setup the object with given
     * context, layout, and list of assessments and database manager.
     *
     * @param context view context
     * @param layoutRes layout of list
     * @param assessments list of courses
     * @param databaseManager database manager
     */
    public AssessmentAdapter(Context context, int layoutRes, ArrayList<Assessment> assessments,
                         DatabaseManager databaseManager) {
        super(context, layoutRes, assessments);
        this.context = context;
        this.layoutRes = layoutRes;
        this.assessments = assessments;
        this.databaseManager = databaseManager;
    }

    /**
     * Function to produce a Row in the Assessments List View
     *
     * @param position of the list view
     * @param convertView view
     * @param parent container
     * @return view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes, null);

        // Setup alternative Color for each orow
        if (position % 2 == 1) {
            view.setBackgroundResource(R.color.rowColor);
        } else {
            view.setBackgroundResource(R.color.alternateRowColor);
        }

        // Get the Assessment object at given position
        final Assessment assessment = assessments.get(position);

        // set to the text view, its title
        TextView titleView = view.findViewById(R.id.textViewAssessmentListTitle);
        titleView.setText(assessment.getTitle());

        ProgressBar bar = view.findViewById(R.id.progressBarAssessment);

        int totalDays = Utility.daysBetweenTwoDates(assessment.getStartDate(), assessment.getEndDate());
        Date today = new Date();
        today.setTime(System.currentTimeMillis());
        int progress = 0;

        // if End date already passed
        if (assessment.getEndDate().compareTo(today) <= 0) {
            progress = 100;
        } else {
            // if not yet started
            if (assessment.getStartDate().compareTo(today) > 0) {
                progress = 0;
            } else {
                // compute days consumed
                int daysConsumed = Utility.daysBetweenTwoDates(assessment.getStartDate(), today);
                progress = Utility.getProgress(totalDays, daysConsumed);
            }
        }

        bar.setProgress(progress);

        ImageButton viewButton = view.findViewById(R.id.buttonListAssessmentView);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAssessment(assessment);
            }
        });

        return view;
    }

    /**
     * Function to load Assessment Details in the view with Edit and Delete buttons on it
     * to provide the user functions to edit/delete or just view the details of the Assessments.
     *
     * @param assessment
     */
    private void loadAssessment(Assessment assessment) {
        Bundle bundle = new Bundle();

        bundle.putInt("assessmentId", assessment.getAssessmentId());
        bundle.putInt("courseId", assessment.getCourseId());
        bundle.putString("title", assessment.getTitle());
        bundle.putString("startDate", Utility.dateToString(assessment.getStartDate()));
        bundle.putString("endDate", Utility.dateToString(assessment.getEndDate()));
        bundle.putString("description", assessment.getDescription());
        bundle.putString("type", assessment.getType());
        bundle.putBoolean("edit", true);

        Fragment fragment = new EditAssessment();
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
        assessments = databaseManager.getAssessmentDao().getAssessments();
        notifyDataSetChanged();
    }

}
