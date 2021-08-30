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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Course;
import com.java.main.course.model.Term;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCourse extends Fragment {
    private DatePickerDialog datePickerDialog;
    private ArrayAdapter<String> statusArrayAdapter;
    private ArrayAdapter<Term> termArrayAdapter;
    private DatabaseManager databaseManager;
    private Spinner statusSpinner;
    private Spinner termSpinner;
    private ArrayList<Term> termList;

    private Button addButton;
    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextInstructorName;
    private EditText editTextInstructorPhone;
    private EditText editTextInstructorEmail;

    // Event handler for the Date Pick for Start Date
    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {

            editTextStartDate.setText(String.format("%d/%d/%d", month+1, day, year));
        }
    };


    // Event handler for the Date Pick for End Date
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            editTextEndDate.setText(String.format("%d/%d/%d", month+1, day, year));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        // database
        databaseManager = new DatabaseManager(getActivity());

        // Set adapter to the status spinner
        statusSpinner = view.findViewById(R.id.spinnerAddCourseStatus);
        String statusArray [] = getResources().getStringArray(R.array.course_status);
        statusArrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, statusArray);
        statusSpinner.setAdapter(statusArrayAdapter);

        // Set adapter to the Term Spinner
        termSpinner = view.findViewById(R.id.spinnerAddCourseTerm);
        termList = databaseManager.getTermDao().getTerms();
        termArrayAdapter = new ArrayAdapter<Term>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                termList);
        termSpinner.setAdapter(termArrayAdapter);

        // Set other components
        editTextTitle = view.findViewById(R.id.editTextAddCourseTitle);
        editTextStartDate = view.findViewById(R.id.editTextAddCourseStartDate);;
        editTextEndDate = view.findViewById(R.id.editTextAddCourseEndDate);;
        editTextInstructorName = view.findViewById(R.id.editTextAddCourseInstructorName);;
        editTextInstructorPhone = view.findViewById(R.id.editTextAddCourseInstructorPhone);;
        editTextInstructorEmail = view.findViewById(R.id.editTextAddCourseInstructorEmail);;

        // Set the Date picker for the Start Date
        editTextStartDate.setInputType(InputType.TYPE_NULL);
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_add_term);
                cdp.setCallBack(startDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        // Set the Date picker for the End Date
        editTextEndDate.setInputType(InputType.TYPE_NULL);
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_add_term);
                cdp.setCallBack(endDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        // add button and its event handling
        addButton = view.findViewById(R.id.buttonAddCourse);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });

        return view;
    }

    /**
     * Function to add the course into the Database and on success move the
     * screen to the list of courses. If there is an error or course is not added
     * stay on this fragment screen and notify the user.
     */
    public void addCourse() {

        String title = editTextTitle.getText().toString().trim();
        String sdString = editTextStartDate.getText().toString().trim();
        String edString = editTextEndDate.getText().toString();
        String status = statusSpinner.getSelectedItem().toString();
        String instructorName = editTextInstructorName.getText().toString().trim();
        String instructorPhone = editTextInstructorPhone.getText().toString().trim();
        String instructorEmail = editTextInstructorEmail.getText().toString().trim();
        Term term = (Term)termSpinner.getSelectedItem();
        int termId = term.getId();

        if(validateInputs(title, sdString, edString, instructorName, instructorPhone,
                instructorEmail)) {

            Course course = new Course();
            course.setTitle(title);
            course.setStartDate(Utility.stringToDate(sdString));
            course.setEndDate(Utility.stringToDate(edString));
            course.setStatus(status);
            course.setInstructorName(instructorName);
            course.setInstructorPhone(instructorPhone);
            course.setInstructorEmail(instructorEmail);
            course.setTermId(termId);

            if(validateTermCourseDates(course.getStartDate(), course.getEndDate(), term.getStartDate(),
                    term.getEndDate())) {
                //adding the Course into database
                if (databaseManager.getCourseDao().addCourse(course)) {
                    Toast.makeText(getActivity(), "New Course has been added", Toast.LENGTH_SHORT).show();

                    // Move to List view
                    Fragment fragment = new ListCourses();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, fragment);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getActivity(), "Could not add new Course", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * Function to validate that the Course Start and End Dates must be within the range of
     * Term Dates.
     *
     * @param termSDate
     * @param termEDate
     * @param courseSDate
     * @param courseEDate
     * @return true if valid, false otherwise
     */
    public boolean validateTermCourseDates(Date courseSDate,
                Date courseEDate, Date termSDate, Date termEDate) {
       if (courseSDate.compareTo(termSDate) >= 0 &&
                courseEDate.compareTo(termEDate) <= 0)
       {
           return true;
       }


       String message = "Course Dates doesn't fall within Term Dates"+
               "\nCourse Dates: Start= " +
               Utility.dateToString(courseSDate) +
           "\nEnd= " + Utility.dateToString(courseEDate);
        message += "Term Dates: Start= " + Utility.dateToString(termSDate) +
                "\nEnd= " + Utility.dateToString(termEDate);

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

        return false;
    }

    /**
     * Function to perform Validation of Inputs.
     *
     * @param title of course
     * @param sdString of course
     * @param edString of course
     * @param instructorName of course
     * @param instructorPhone of course
     * @param instructorEmail of course
     * @return true if valid, false otherwise
     */
    public boolean validateInputs(String title, String sdString, String edString,
                                  String instructorName, String instructorPhone,
                                  String instructorEmail) {

        // validation
        if (title.isEmpty()) {
            editTextTitle.setError("Provide title");
            editTextTitle.requestFocus();
            return false;
        }

        try {
            if (sdString.isEmpty()) {
                editTextStartDate.setError("Select Start Date");
                editTextStartDate.requestFocus();
                return false;
            }
            Date startDate = Utility.stringToDate(sdString);

            if (edString.isEmpty()) {
                editTextEndDate.setError("Select End Date");
                editTextEndDate.requestFocus();
                return false;
            }
            Date endDate = Utility.stringToDate(edString);

            // Check end Date must be greater than start date
            if(startDate.compareTo(endDate) > 0) {
                editTextEndDate.setError("Invalid End Date");
                editTextEndDate.requestFocus();
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }



        if (instructorName.isEmpty()) {
            editTextInstructorName.setError("Provide Instructor NaMe");
            editTextInstructorName.requestFocus();
            return false;
        }

        if (instructorPhone.isEmpty()) {
            editTextInstructorPhone.setError("Provide Instructor Phone");
            editTextInstructorPhone.requestFocus();
            return false;
        }

        if (instructorEmail.isEmpty()) {
            editTextInstructorEmail.setError("Provide Instructor Email");
            editTextInstructorEmail.requestFocus();
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
        getActivity().setTitle(R.string.add_course_title);
    }
}
