package com.java.main.course.coursemanagementapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Course;
import com.java.main.course.model.Note;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNote extends Fragment {

    private ArrayAdapter<Course> courseAdapter;
    private DatabaseManager databaseManager;
    private Spinner courseSpinner;
    private ArrayList<Course> courseList;
    private Button addButton;
    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        // database
        databaseManager = new DatabaseManager(getActivity());

        // Set adapter to the Course Spinner
        courseSpinner = view.findViewById(R.id.spinnerAddNoteCourse);
        courseList = databaseManager.getCourseDao().getCourses();
        courseAdapter = new ArrayAdapter<Course>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                    courseList);
        courseSpinner.setAdapter(courseAdapter);

        editTextTitle = view.findViewById(R.id.txtAddNoteTitle);
        editTextDescription = view.findViewById(R.id.txtAddNoteDescription);

        // add button and its event handling
        addButton = view.findViewById(R.id.btnAddNote);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        return view;
    }

    /**
     * Function to add new Note into the database for the selected Course
     */
    private void addNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int courseId = ((Course)courseSpinner.getSelectedItem()).getCourseId();

        if(validateInputs(title, description)) {
            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setCourseId(courseId);

            //adding the Course into database
            if (databaseManager.getNotesDao().addNote(note)) {
                Toast.makeText(getActivity(), "New Note has been added", Toast.LENGTH_SHORT).show();
                Log.d("Add NOTE: ->   ", "New Note has been added");
                // Move to List view
                Fragment fragment = new ListNotes();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
            else {
                Toast.makeText(getActivity(), "Could not add new Note", Toast.LENGTH_SHORT).show();
                Log.d("Add NOTE: ->   ", "New Note has not been added");
            }
        }
    }

    /**
     * Function to validate that the user has provided the Title and the Description for the Note.
     *
     * @param title
     * @param description
     * @return true if present, false otherwise
     */
    private boolean validateInputs(String title, String description) {

        if (title.isEmpty()) {
            editTextTitle.setError("Provide title");
            editTextTitle.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            editTextDescription.setError("Provide title");
            editTextDescription.requestFocus();
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
        getActivity().setTitle(R.string.add_notes_title);
    }
}
