package com.java.main.course.coursemanagementapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Assessment;
import com.java.main.course.model.Course;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAssessment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private ArrayAdapter<Course> courseArrayAdapter;
    private DatabaseManager databaseManager;
    private Spinner courseSpinner;
    private ArrayList<Course> courses;

    private Button addButton;
    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtStartDate;
    private EditText txtEndDate;
    private RadioButton rdbExam;
    private RadioButton rdbProject;

    // Event handler for the Date Pick for Start Date
    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {

            txtStartDate.setText(String.format("%d/%d/%d", month+1, day, year));
        }
    };


    // Event handler for the Date Pick for End Date
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            txtEndDate.setText(String.format("%d/%d/%d", month+1, day, year));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_assessment, container, false);

        // database
        databaseManager = new DatabaseManager(getActivity());

        // Components
        courseSpinner = view.findViewById(R.id.spinnerAddAssessmentCourse);
        courses = databaseManager.getCourseDao().getCourses();
        courseArrayAdapter = new ArrayAdapter<Course>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                courses);
        courseSpinner.setAdapter(courseArrayAdapter);

        txtStartDate = view.findViewById(R.id.txtAddAssessmentStartDate);
        txtEndDate = view.findViewById(R.id.txtAddAssessmentEndDate);
        txtTitle = view.findViewById(R.id.txtAddAssessmentTitle);
        txtDescription = view.findViewById(R.id.txtAddAssessmentDescription);
        rdbExam = view.findViewById(R.id.rdbExam);
        rdbProject = view.findViewById(R.id.rdbProject);

        // Set the Date picker for the Start Date
        txtStartDate.setInputType(InputType.TYPE_NULL);
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_add_assessment);
                cdp.setCallBack(startDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        // Set the Date picker for the End Date
        txtEndDate.setInputType(InputType.TYPE_NULL);
        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_add_assessment);
                cdp.setCallBack(endDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        // add button and its event handling
        addButton = view.findViewById(R.id.btnAddAssessment);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAssessment();
            }
        });

        return view;
    }

    /**
     * Function to validate that the Assessment Start and End Dates must be within the range of
     * Course Dates.
     *
     * @param asDate
     * @param aeDate
     * @param csDate
     * @param ceDate
     * @return true if valid, false otherwise
     */
    public boolean validateAssessmentCourseDates(Date asDate,
                                           Date aeDate, Date csDate, Date ceDate) {
        if (asDate.compareTo(csDate) >= 0 &&
                aeDate.compareTo(ceDate) <= 0)
        {
            return true;
        }


        String message = "Assessment Dates doesn't fall within Course Dates"+
                "\nAssessment Dates: Start= " +
                Utility.dateToString(asDate) +
                "\nEnd= " + Utility.dateToString(aeDate);
        message += "Course Dates: Start= " + Utility.dateToString(csDate) +
                "\nEnd= " + Utility.dateToString(ceDate);

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

        return false;
    }

    /**
     * Function to create and Save new Assessment for a selected course.
     */
    private void addAssessment() {

        // Parse Data.
        String title = txtTitle.getText().toString().trim();
        String description = txtDescription.getText().toString().trim();
        String sdString = txtStartDate.getText().toString().trim();
        String edString = txtEndDate.getText().toString();
        Course course = (Course) courseSpinner.getSelectedItem();
        int courseId = course.getCourseId();
        String type = "Examination";

        if(rdbProject.isChecked()) {
            type = "Project";
        }

        // Check to see if the current Assessments must be Less than 6 for the selected Course.
        int count = this.databaseManager.getAssessmentDao().getAssessmentCount(courseId);

        if(count > 6)
        {
            Toast.makeText(getActivity(), "You already added 6 Assessments. That is MAX", Toast.LENGTH_SHORT).show();
            return;
        }

        if(validateInputs(title, description, sdString, edString)) {

            // Create Assessment
            Assessment assessment = new Assessment();
            assessment.setTitle(title);
            assessment.setDescription(description);
            assessment.setStartDate(Utility.stringToDate(sdString));
            assessment.setEndDate(Utility.stringToDate(edString));
            assessment.setCourseId(courseId);
            assessment.setType(type);

            if(validateAssessmentCourseDates(assessment.getStartDate(), assessment.getEndDate(),
                    course.getStartDate(), course.getEndDate())) {

                //adding the Course into database
                if (databaseManager.getAssessmentDao().addAssessment(assessment)) {
                    Toast.makeText(getActivity(), "New assessment has been added", Toast.LENGTH_SHORT).show();

                    // Move to List view
                    Fragment fragment = new ListAssessments();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, fragment);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getActivity(), "Could not add new Assessment", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(getActivity(), "Inputs are not valid - provide all data", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function to validate inputs needed to create an Assessment.
     *
     * @param title
     * @param description
     * @param sdString
     * @param edString
     * @return
     */
    public boolean validateInputs(String title, String description, String sdString, String edString) {

        // validation
        if (title.isEmpty()) {
            txtTitle.setError("Provide title");
            txtTitle.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            txtDescription.setError("Provide Description");
            txtDescription.requestFocus();
            return false;
        }

        try {
            if (sdString.isEmpty()) {
                txtStartDate.setError("Select Start Date");
                txtStartDate.requestFocus();
                return false;
            }
            Date startDate = Utility.stringToDate(sdString);

            if (edString.isEmpty()) {
                txtEndDate.setError("Select End Date");
                txtEndDate.requestFocus();
                return false;
            }
            Date endDate = Utility.stringToDate(edString);

            // Check end Date must be greater than start date
            if(startDate.compareTo(endDate) > 0) {
                txtEndDate.setError("Invalid End Date");
                txtEndDate.requestFocus();
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.add_assessment_title);
    }
}
