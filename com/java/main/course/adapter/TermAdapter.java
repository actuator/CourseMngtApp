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

import com.java.main.course.coursemanagementapp.EditTerm;
import com.java.main.course.coursemanagementapp.R;
import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Term;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;

/**
 * Class to represent a Term Array Adapter
 */

public class TermAdapter extends ArrayAdapter<Term> {

    private Context context;
    private int layoutRes;
    private ArrayList<Term> terms;
    private DatabaseManager databaseManager;

    /**
     * Constructor to setup and perform house keeping to initialize TermAdapter
     *
     * @param context
     * @param layoutRes
     * @param terms
     * @param databaseManager
     */
    public TermAdapter(Context context, int layoutRes, ArrayList<Term> terms,
                       DatabaseManager databaseManager) {
        super(context, layoutRes, terms);

        this.context = context;
        this.layoutRes = layoutRes;
        this.terms = terms;
        this.databaseManager = databaseManager;
    }

    /**
     * Function to produce a Row the Term Listview.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes, null);

        if (position % 2 == 1) {
            view.setBackgroundResource(R.color.rowColor);
        } else {
            view.setBackgroundResource(R.color.alternateRowColor);
        }

        final Term term = terms.get(position);

        TextView titleView = view.findViewById(R.id.textViewTermListTitle);
        titleView.setText(term.getTitle());
        ImageButton viewButton = view.findViewById(R.id.buttonListTermView);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTerm(term);
            }
        });

        return view;
    }

    /**
     * Display the Term into Edit View
     *
     * @param term
     */
    public void loadTerm(Term term) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", term.getId());
        bundle.putString("title", term.getTitle());
        bundle.putString("startDate", Utility.dateToString(term.getStartDate()));
        bundle.putString("endDate", Utility.dateToString(term.getEndDate()));
        bundle.putBoolean("edit", true);

        Fragment fragment = new EditTerm();
        fragment.setArguments(bundle);
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Function to Load all the Employees form the database.
     */
    public void loadTerms() {
        terms = databaseManager.getTermDao().getTerms();
        notifyDataSetChanged();
    }
}
