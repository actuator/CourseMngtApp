package com.java.main.course.coursemanagementapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Term;
import com.java.main.course.utility.Utility;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTerm extends Fragment {

    /** components and fields **/
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextTitle;
    private DatabaseManager databaseManager;
    private Button addButton;

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
        View rootView = inflater.inflate(R.layout.fragment_add_term, container, false);

        databaseManager = new DatabaseManager(getActivity());
        editTextTitle = rootView.findViewById(R.id.editTextAddTermTitle);

        // Set the Date picker for the Start Date
        editTextStartDate = rootView.findViewById(R.id.editTextAddTermStartDate);
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
        editTextEndDate = rootView.findViewById(R.id.editTextAddTermEndDate);
        editTextEndDate.setInputType(InputType.TYPE_NULL);
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDatePicker cdp = new CustomDatePicker().newInstance(R.layout.fragment_add_term);
                cdp.setCallBack(endDate);
                cdp.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        addButton = rootView.findViewById(R.id.buttonAddTerm);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTerm();
            }
        });

        return rootView;
    }

    private void addTerm() {
        String title = editTextTitle.getText().toString().trim();
        String sdString = editTextStartDate.getText().toString().trim();
        String edString = editTextEndDate.getText().toString();

        if (title.isEmpty()) {
            editTextTitle.setError("Provide title");
            editTextTitle.requestFocus();
            return;
        }

        try {
            if (sdString.isEmpty()) {
                editTextStartDate.setError("Select Start Date");
                editTextStartDate.requestFocus();
                return;
            }
            Date startDate = Utility.stringToDate(sdString);

            if (edString.isEmpty()) {
                editTextEndDate.setError("Select End Date");
                editTextEndDate.requestFocus();
                return;
            }
            Date endDate = Utility.stringToDate(edString);

            // Check end Date must be greater than start date
            if(startDate.compareTo(endDate) > 0) {
                editTextEndDate.setError("Invalid End Date");
                editTextEndDate.requestFocus();
                return;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }

        // Create Term
        Term term = new Term(0, title, Utility.stringToDate(sdString), Utility.stringToDate(edString));

        //adding the term into database
        if (databaseManager.getTermDao().addTerm(term)) {
            Toast.makeText(getActivity(), "New Term has been added", Toast.LENGTH_SHORT).show();


            // Move to List view
            Fragment fragment = new ListTerms();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, fragment);
            fragmentTransaction.commit();

        }
        else {
            Toast.makeText(getActivity(), "Could not add new Term", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.add_term_title);
    }
}
