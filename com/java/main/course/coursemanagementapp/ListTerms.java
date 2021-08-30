package com.java.main.course.coursemanagementapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.main.course.adapter.TermAdapter;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Term;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTerms extends Fragment {

    private ArrayList<Term> terms;
    private DatabaseManager database;
    private ListView listViewTerms;
    private TermAdapter termAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_terms, container, false);

        terms = new ArrayList<Term>();
        database = new DatabaseManager(getActivity());
        listViewTerms = view.findViewById(R.id.listViewTerms);

        FloatingActionButton fab = view.findViewById(R.id.add_term);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddTerm();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.commit();
            }
        });

        loadTerms();

        return view;
    }

    private void loadTerms() {
        terms = database.getTermDao().getTerms();
        termAdapter = new TermAdapter(getActivity(), R.layout.list_term_layout, terms, database);
        listViewTerms.setAdapter(termAdapter);
    }

    @Override
    /**
     * Function to update the Window Title according to the loaded fragment into the
     * activity.
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.list_term_title);
    }
}
