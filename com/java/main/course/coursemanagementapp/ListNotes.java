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
import com.java.main.course.adapter.NoteAdapter;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Course;
import com.java.main.course.model.Note;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListNotes extends Fragment {

    private ArrayList<Note> notes;
    private DatabaseManager databaseManager;
    private ListView listViewNotes;
    private NoteAdapter noteAdapter;
    private ArrayList<Course> courses;
    private ArrayAdapter<Course> courseArrayAdapter;
    private Spinner spinnerCourses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        notes = new ArrayList<Note>();
        databaseManager = new DatabaseManager(getActivity());
        listViewNotes = view.findViewById(R.id.listViewNotes);

        FloatingActionButton fab = view.findViewById(R.id.btnAddNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddNote();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
        });

        // initialize Spinner
        spinnerCourses = view.findViewById(R.id.spinnerListCourseNotes);
        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                listNotes(courses.get(position).getCourseId());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        loadCourses();
        loadNotes();
        return view;
    }

    /**
     * Function to display the Notes for selected Course.
     *
     * @param courseId
     */
    private void listNotes(int courseId) {
        notes = this.databaseManager.getNotesDao().getNotes();

        ArrayList<Note> filterList = new ArrayList<Note>();
        for(Note note: notes) {
            if(note.getCourseId() == courseId) {
                filterList.add(note);
            }
        }

        noteAdapter = new NoteAdapter(getActivity(), R.layout.list_notes_layout, filterList, databaseManager);
        listViewNotes.setAdapter(noteAdapter);
    }

    /**
     * Function to load Notes into the adapter
     */
    private void loadNotes() {
        notes = databaseManager.getNotesDao().getNotes();
        noteAdapter = new NoteAdapter(getActivity(), R.layout.list_notes_layout, notes, databaseManager);
        listViewNotes.setAdapter(noteAdapter);
    }

    /**
     * Function to load the Courses into the spinner.
     */
    private void loadCourses() {
        this.courses = this.databaseManager.getCourseDao().getCourses();
        this.courseArrayAdapter = new ArrayAdapter<Course>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, courses);

        this.spinnerCourses.setAdapter(this.courseArrayAdapter);
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.list_notes_title);
    }

}
