package com.java.main.course.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.java.main.course.coursemanagementapp.EditNote;
import com.java.main.course.coursemanagementapp.R;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Note;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int layoutRes;
    private ArrayList<Note> notes;
    private DatabaseManager databaseManager;


    /**
     * Constructor to setup the NoteAdapter with given parameters passed to the constructor.
     *
     * @param context view context
     * @param layoutRes layout of the notes list
     * @param notes array list of notes
     * @param databaseManager database manager reference
     */
    public NoteAdapter(Context context, int layoutRes, ArrayList<Note> notes,
                       DatabaseManager databaseManager) {
        super(context, layoutRes, notes);
        this.context = context;
        this.layoutRes = layoutRes;
        this.notes = notes;
        this.databaseManager = databaseManager;
    }

    /**
     * Function to generate a Row in the List with basic information and a button to view the Note
     * Details.
     *
     * @param position of the note in the list
     * @param convertView view
     * @param parent view
     * @return row view in the list
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes, null);

        // Select the background color
        if(position % 2 == 1) {
            view.setBackgroundResource(R.color.rowColor);
        } else {
            view.setBackgroundResource(R.color.alternateRowColor);
        }

        // Select the Note
        final Note note = notes.get(position);
        //Log.d("NOTE: ->   ", note.toString() + "\t" + note.getDescription());
        TextView titleView = view.findViewById(R.id.note_list_title);
        titleView.setText(note.getTitle());
        ImageButton viewButton = view.findViewById(R.id.btnViewNote);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNote(note);
            }
        });

        return view;
    }

    /**
     * Function to load Course Details in the view with Edit and Delete buttons on it
     * to provide the user functions to edit/delete or just view the details of the course.
     *
     * @param note
     */
    private void loadNote(Note note) {
        Bundle bundle = new Bundle();
        bundle.putInt("noteId", note.getNoteId());
        bundle.putString("title", note.getTitle());
        bundle.putString("description", note.getDescription());
        bundle.putInt("courseId", note.getCourseId());

        Fragment fragment = new EditNote();
        fragment.setArguments(bundle);
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Function to load all the Notes form the Database
     */
    public void loadNotes() {
        this.notes = databaseManager.getNotesDao().getNotes();
        notifyDataSetChanged();
    }

}
