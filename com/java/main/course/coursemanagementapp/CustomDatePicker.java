package com.java.main.course.coursemanagementapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CustomDatePicker extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;
    private boolean isModal = false;
    private static int fragmentId;

    public static CustomDatePicker newInstance(int id)
    {
        CustomDatePicker frag = new CustomDatePicker();
        fragmentId = fragmentId;
        frag.isModal = true;
        return frag;
    }

    public CustomDatePicker(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), onDateSet, year, month, day);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(isModal) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        else {
            View rootView = inflater.inflate(fragmentId, container, false);
            return rootView;
        }
    }

    /**
     * Function to set the call back
     * @param onDate
     */
    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }
}
