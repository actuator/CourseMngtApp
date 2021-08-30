package com.java.main.course.coursemanagementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Note;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNote extends Fragment {

    private Button saveButton;
    private Button deleteButton;
    private Button smsButton;
    private Button emailButton;
    private EditText txtNoteId;
    private EditText txtCourseId;
    private EditText txtTitle;
    private EditText txtDescription;
    private DatabaseManager databaseManager;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        databaseManager = new DatabaseManager(getActivity());

        // Get Component references
        txtNoteId = view.findViewById(R.id.txtEditNoteId);
        txtCourseId = view.findViewById(R.id.txtEditCourseId);
        txtTitle = view.findViewById(R.id.txtEditNoteTitle);
        txtDescription = view.findViewById(R.id.txtEditNoteDescription);
        saveButton = view.findViewById(R.id.btnEditNote);
        deleteButton = view.findViewById(R.id.btnDeleteNote);
        smsButton = view.findViewById(R.id.btnSMSNote);
        emailButton = view.findViewById(R.id.btnEmailNote);

        // disable editing
        txtNoteId.setEnabled(false);
        txtCourseId.setEnabled(false);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to List view
                Fragment fragment = new SendSms();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to List view
                Fragment fragment = new SendEmail();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
        });

        // set selected course values
        this.bundle = getArguments();

        if (bundle != null) {
            this.txtNoteId.setText((String.valueOf(bundle.getInt("noteId"))));
            txtCourseId.setText(String.valueOf(bundle.getInt("courseId")));
            txtTitle.setText(bundle.getString("title"));
            txtDescription.setText(bundle.getString("description"));
        }

        return view;
    }

    /**
     * Save the Note to update its information.
     */
    private void saveNote() {

        String title = txtTitle.getText().toString().trim();
        String description = txtDescription.getText().toString().trim();

        if (validateInputs(title, description)) {
            Note note = new Note();
            note.setNoteId(bundle.getInt("noteId"));
            note.setCourseId(bundle.getInt("courseId"));
            note.setTitle(title);
            note.setDescription(description);

            // Save the Note
            if (databaseManager.getNotesDao().updateNote(note)) {
                Toast.makeText(getActivity(), "Note has been updated", Toast.LENGTH_SHORT).show();
                // Move to List view
                Fragment fragment = new ListNotes();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getActivity(), "Could not Save the Note", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "Could not Save the Note", Toast.LENGTH_SHORT).show();
            Log.d("Edit NOTE: ->   ", "Could not Save the Note");
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
            txtTitle.setError("Provide title");
            txtTitle.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            txtDescription.setError("Provide title");
            txtDescription.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Delete this Note from the System.
     */
    private void deleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Are you sure to delete this Note?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int noteId = bundle.getInt("noteId");

                if (databaseManager.getNotesDao().deleteNote(noteId)) {
                    Toast.makeText(getActivity(), "Note Has been deleted", Toast.LENGTH_SHORT).show();

                    // Move to List view
                    Fragment fragment = new ListNotes();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, fragment);
                    fragmentTransaction.commit();

                }
                else {
                    Toast.makeText(getActivity(), "Could not Delete the note", Toast.LENGTH_SHORT).show();
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

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.edit_notes_title);
    }
}
