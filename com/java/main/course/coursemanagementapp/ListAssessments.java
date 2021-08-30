package com.java.main.course.coursemanagementapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.main.course.adapter.AssessmentAdapter;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Assessment;
import com.java.main.course.model.Course;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAssessments extends Fragment {

    private ArrayList<Course> courses;
    private DatabaseManager databaseManager;
    private Spinner spinnerCourse;
    private ArrayAdapter<Course> courseArrayAdapter;
    private ArrayList<Assessment> assessments;
    private ListView listViewAssessments;
    private AssessmentAdapter assessmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_assessments, container, false);

        databaseManager = new DatabaseManager(getActivity());
        courses = new ArrayList<Course>();
        listViewAssessments = view.findViewById(R.id.listViewAssessments);
        spinnerCourse = view.findViewById((R.id.spinnerListAssessmentCourse));

        FloatingActionButton fab = view.findViewById(R.id.add_Assessment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddAssessment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
        });

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                listAssessments(courses.get(position).getCourseId());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // load terms into spinner
        loadAssessments();

        // load courses
        loadCourses();

        return view;
    }

    /**
     * Load the Assessments for the selected course into the List View.
     *
     * @param courseId term to fetch assessments
     */
    private void listAssessments(int courseId) {
        assessments = databaseManager.getAssessmentDao().getAssessments();

        ArrayList<Assessment> filterList = new ArrayList<Assessment>();
        for(Assessment a: assessments) {
            if(a.getCourseId() == courseId) {
                filterList.add(a);
            }
        }

        assessmentAdapter = new AssessmentAdapter(getActivity(), R.layout.list_assessment_layout,
                filterList, databaseManager);
        listViewAssessments.setAdapter(assessmentAdapter);
    }

    /**
     * Load the all the assessments into the List View.
     */
    private void loadAssessments() {
        assessments = databaseManager.getAssessmentDao().getAssessments();
        assessmentAdapter = new AssessmentAdapter(getActivity(), R.layout.list_assessment_layout,
                assessments, databaseManager);
        listViewAssessments.setAdapter(assessmentAdapter);
    }

    /**
     * Function to load the Courses into the spinner.
     */
    private void loadCourses() {
        this.courses = this.databaseManager.getCourseDao().getCourses();
        this.courseArrayAdapter = new ArrayAdapter<Course>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, courses);

        this.spinnerCourse.setAdapter(this.courseArrayAdapter);
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.list_assessment_title);
    }
}
