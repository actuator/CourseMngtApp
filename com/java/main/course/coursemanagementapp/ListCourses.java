package com.java.main.course.coursemanagementapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.main.course.adapter.CourseAdapter;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Course;
import com.java.main.course.model.Term;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListCourses extends Fragment {

    private ArrayList<Term> terms;
    private DatabaseManager databaseManager;
    private Spinner spinnerTerms;
    private ArrayAdapter<Term> termArrayAdapter;

    private ArrayList<Course> courses;
    private ListView listViewCourses;
    private ListAdapter courseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_courses, container, false);

        // initialize database
        databaseManager = new DatabaseManager(getActivity());
        courses = new ArrayList<Course>();
        listViewCourses = view.findViewById(R.id.listViewCourses);


        // initialize Spinner
        spinnerTerms = view.findViewById(R.id.spinnerListCourseTerms);

        FloatingActionButton fab = view.findViewById(R.id.add_Course);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddCourse();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
        });

        spinnerTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                listCourses(terms.get(position).getId());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // load terms into spinner
        loadTerms();

        // load courses
        loadCourses();

        return view;
    }

    /**
     * Load the courses into the List View.
     *
     * @param termId term to fetch courses
     */
    private void listCourses(int termId) {
        courses = databaseManager.getCourseDao().getCourses();

        ArrayList<Course> filterList = new ArrayList<Course>();
        for(Course crs: courses) {
            if(crs.getTermId() == termId) {
                filterList.add(crs);
            }
        }

        courseAdapter = new CourseAdapter(getActivity(), R.layout.list_course_layout, filterList, databaseManager);
        listViewCourses.setAdapter(courseAdapter);
    }

    /**
     * Load the courses into the List View.
     */
    private void loadCourses() {
        courses = databaseManager.getCourseDao().getCourses();
        courseAdapter = new CourseAdapter(getActivity(), R.layout.list_course_layout, courses, databaseManager);
        listViewCourses.setAdapter(courseAdapter);
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.list_course_title);
    }

    /**
     * Function to load the terms into the spinner.
     */
    private void loadTerms() {
        this.terms = this.databaseManager.getTermDao().getTerms();
        this.termArrayAdapter = new ArrayAdapter<Term>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, terms);

        this.spinnerTerms.setAdapter(this.termArrayAdapter);
    }
}
