package com.java.main.course.coursemanagementapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
public class EditCourse extends Fragment {

    private DatePickerDialog datePickerDialog;
    private ArrayAdapter<String> statusArrayAdapter;
    private ArrayAdapter<Term> termArrayAdapter;
    private DatabaseManager databaseManager;
    private Spinner statusSpinner;
    private Spinner termSpinner;
    private ArrayList<Term> termList;
    private String statusArray [];
    private Button editButton;
    private Button deleteButton;
    private EditText editTextCourseId;
    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextInstructorName;
    private EditText editTextInstructorPhone;
    private EditText editTextInstructorEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_course, container, false);
        databaseManager = new DatabaseManager(getActivity());

        // database
        databaseManager = new DatabaseManager(getActivity());

        // Set adapter to the status spinner
        statusSpinner = view.findViewById(R.id.spinnerEditCourseStatus);
        statusArray = getResources().getStringArray(R.array.course_status);
        statusArrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, statusArray);
        statusSpinner.setAdapter(statusArrayAdapter);

        // Set adapter to the Term Spinner
        termSpinner = view.findViewById(R.id.spinnerEditCourseTerm);
        termList = databaseManager.getTermDao().getTerms();
        termArrayAdapter = new ArrayAdapter<Term>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                termList);
        termSpinner.setAdapter(termArrayAdapter);

        // Set other components
        editTextCourseId = view.findViewById(R.id.editTextEditCourseId);
        editTextTitle = view.findViewById(R.id.editTextEditCourseTitle);
        editTextStartDate = view.findViewById(R.id.editTextEditCourseStartDate);;
        editTextEndDate = view.findViewById(R.id.editTextEditCourseEndDate);;
        editTextInstructorName = view.findViewById(R.id.editTextEditCourseInstructorName);;
        editTextInstructorPhone = view.findViewById(R.id.editTextEditCourseInstructorPhone);;
        editTextInstructorEmail = view.findViewById(R.id.editTextEditCourseInstructorEmail);;

        // Set the Date picker for the Start Date
        editTextStartDate.setInputType(InputType.TYPE_NULL);
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_edit_course);
                cdp.setCallBack(startDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        // Set the Date picker for the End Date
        editTextEndDate.setInputType(InputType.TYPE_NULL);
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_edit_course);
                cdp.setCallBack(endDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        editButton = view.findViewById(R.id.buttonCourseEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCourse();
            }
        });

        deleteButton = view.findViewById(R.id.buttonCourseDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });



        // set selected course values
        Bundle bundle = getArguments();

        if(bundle != null && bundle.getBoolean("edit")) {
            editTextCourseId.setText(String.valueOf(bundle.getInt("courseId")));
            editTextTitle.setText(bundle.getString("title"));
            editTextStartDate.setText(bundle.getString("startDate"));
            editTextEndDate.setText((bundle.getString("endDate")));

            editTextInstructorName.setText(bundle.getString("instructorName"));
            editTextInstructorPhone.setText(bundle.getString("instructorPhone"));
            editTextInstructorEmail.setText(bundle.getString("instructorEmail"));

            String status = bundle.getString("status");
            statusSpinner.setSelection(getStatusIndex(status));
            System.out.println("Position: " + getStatusIndex(status) + " Status: " + status);
            int termId = bundle.getInt("termId");
            termSpinner.setSelection(getTermIndex(termId));
        }

        return view;
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
     * Function to edit the course that is loaded into this UI. Parse all the values and create
     * course object. call the adapter to update the Course object.
     */
    public void editCourse() {

        int courseId = Integer.parseInt(editTextCourseId.getText().toString().trim());
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
            course.setCourseId(courseId);
            course.setTitle(title);
            course.setStartDate(Utility.stringToDate(sdString));
            course.setEndDate(Utility.stringToDate(edString));
            course.setStatus(status);
            course.setInstructorName(instructorName);
            course.setInstructorPhone(instructorPhone);
            course.setInstructorEmail(instructorEmail);
            course.setTermId(termId);

            if(validateTermCourseDates(course.getStartDate(), course.getEndDate(),
                    term.getStartDate(), term.getEndDate())) {
                // Edit the Course
                if (databaseManager.getCourseDao().updateCourse(course)) {
                    Toast.makeText(getActivity(), "Course has been updated", Toast.LENGTH_LONG).show();

                    // Move to List view
                    Fragment fragment = new ListCourses();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, fragment);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getActivity(), "Could not update the Course", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * Function to delete a Course. A course can only be deleted if there is no linked sub table such
     * as Note, Assessment, Goal, Course Alert etc. If the Course is a Leaf Record i.e not used as
     * Foreign Key in any sub table, it will be deleted.
     */
    public void deleteCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Are you sure to delete this course?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int courseId = Integer.parseInt(editTextCourseId.getText().toString().trim());

                if (databaseManager.getCourseDao().deleteCourse(courseId)) {
                    Toast.makeText(getActivity(), "Course Has been deleted", Toast.LENGTH_SHORT).show();

                    // Move to List view
                    Fragment fragment = new ListCourses();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, fragment);
                    fragmentTransaction.commit();

                }
                else {
                    Toast.makeText(getActivity(), "Could not Delete the Course", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    /**
     * Function to get the Status Index from the status list.
     *
     * @param status
     * @return index of status
     */
    private int getStatusIndex(String status) {
        int index = 0;

        for(int i=0; i<statusArray.length; i++) {
            if(statusArray[i].equals(status)) {
                return i;
            }
        }

        return index;
    }

    /**
     * Function to get the Term index for a given term id
     *
     * @param termId
     * @return term index
     */
    private int getTermIndex(int termId) {

        for(int i=0; i<termList.size(); i++) {
            if(termList.get(i).getId() == termId) {
                return i;
            }
        }

        return 0;
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.edit_course_title);
    }

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
}
