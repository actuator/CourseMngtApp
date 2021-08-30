package com.java.main.course.coursemanagementapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.main.course.dao.DatabaseManager;
import com.java.main.course.model.Assessment;
import com.java.main.course.model.Course;
import com.java.main.course.utility.Utility;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications extends Fragment {

    private RadioButton rdbCourse;
    private RadioButton rdbAssessment;
    private RadioButton rdbStartDate;
    private RadioButton rdbEndDate;
    private EditText txtMessage;
    private Spinner spinnerCourse;
    private Spinner spinnerAssessment;
    private ArrayList<Course> courses;
    private ArrayList<Assessment> assessments;
    private ArrayAdapter<Course> courseArrayAdapter;
    private ArrayAdapter<Assessment> assessmentArrayAdapter;
    private Button btnCreate;
    private DatabaseManager databaseManager;
    private boolean isStartDate;
    private boolean isCourse;
    private EditText txtTitle;
    private EditText txtDate;
    private Course course;
    private Assessment assessment;
    private Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // database
        databaseManager = new DatabaseManager(getActivity());

        // Lists
        courses = databaseManager.getCourseDao().getCourses();
        assessments = databaseManager.getAssessmentDao().getAssessments();

        // components
        rdbCourse = view.findViewById(R.id.rdbCourse);
        rdbAssessment = view.findViewById(R.id.rdbAssessment);
        rdbStartDate = view.findViewById(R.id.rdbStartDate);
        rdbEndDate = view.findViewById(R.id.rdbEndDate);
        txtMessage = view.findViewById(R.id.txtMessage);
        spinnerAssessment = view.findViewById(R.id.spinnerAssessment);
        spinnerAssessment.setEnabled(false);
        spinnerCourse = view.findViewById(R.id.spinnerCourse);
        btnCreate = view.findViewById(R.id.btnCreateNotification);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDate = view.findViewById(R.id.txtDate);

        courseArrayAdapter = new ArrayAdapter<Course>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                courses);
        spinnerCourse.setAdapter(courseArrayAdapter);

        assessmentArrayAdapter = new ArrayAdapter<Assessment>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                assessments);
        spinnerAssessment.setAdapter(assessmentArrayAdapter);
        isStartDate = true;     // Initially it is set to start date.
        isCourse = true;        // Initially it is course.

        RadioGroup rgCategory = view.findViewById(R.id.rgCategory);
        rgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = view.findViewById(checkedId);
                if(rb.equals(rdbAssessment)) {
                    isCourse = false;
                    spinnerAssessment.setEnabled(true);
                    spinnerCourse.setEnabled(false);
                } else {
                    isCourse = true;
                    spinnerCourse.setEnabled(true);
                    spinnerAssessment.setEnabled(false);
                }
            }
        });

        RadioGroup rgDate = view.findViewById(R.id.rgDate);
        rgDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = view.findViewById(checkedId);
                if(rb.equals(rdbStartDate)) {
                    isStartDate = true;
                    setDate();
                } else {
                    isStartDate = false;
                    setDate();
                }
            }
        });

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                course = (Course) spinnerCourse.getSelectedItem();
                setDate();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        spinnerAssessment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                assessment = (Assessment) spinnerAssessment.getSelectedItem();
                setDate();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
            }
        });

        return view;
    }

    /**
     * Set the Date
     */
    private void setDate() {
        // Parse Data for the Course
        if (isCourse) {
            if(course != null) {
                if (isStartDate) {
                    date = course.getStartDate();
                } else {
                    date = course.getEndDate();
                }
            }
        } else {

            if(assessment != null) {
                // Parse Data for the Assessment
                if (isStartDate) {
                    date = assessment.getStartDate();
                } else {
                    date = assessment.getEndDate();
                }
            }
        }

        if(date != null) {
            txtDate.setText(Utility.dateToString(date));
        }
    }

    /**
     * Create a Notification that will be displayed or activated to due date i.e. Start or End Date
     */
    private void createNotification() {

        Course course = null;
        Assessment assessment = null;
        String title = this.txtTitle.getText().toString().trim();
        String message = this.txtMessage.getText().toString().trim();


        if(validateInputs(message, title)) {

            // Only Set for future dates.
            Date today = new Date();
            today.setTime(System.currentTimeMillis());
            System.out.println(today.toString());
            System.out.println(date.toString());
            if (today.compareTo(date) <= 0) {
                createNotification(getActivity(), title, message, date);
            } else {
                Toast.makeText(getActivity(), "Notification Cannot be created for Past Date "
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Function to create Notification for a given date in the future.
     *
     * @param context
     * @param title
     * @param message
     * @param date
     */
    public void createNotification(Context context, String title, String message, Date date){

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_notifications_paused_black_24dp);
        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, Publisher.class);
        notificationIntent.putExtra(Publisher.getIDKey(), (int)System.currentTimeMillis());
        notificationIntent.putExtra(Publisher.getKey(), notification);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Adjust future time
        long curTime = System.currentTimeMillis();
        long span = date.getTime() - curTime;   // Difference till event in future from current time
        //long eventTime = SystemClock.elapsedRealtime() + span;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        System.out.println("Current-Time: " + curTime);
        System.out.println("Alert Date: " + date.getTime());
        System.out.println("Time to Alert: " + span);
        System.out.println("Elapsed RealTime: " + AlarmManager.ELAPSED_REALTIME);
        System.out.println("Elapsed RealTime: " + AlarmManager.ELAPSED_REALTIME_WAKEUP);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, span , pendingIntent);
        Toast.makeText(getActivity(), "Notification has been set to show on " +
                        Utility.dateToString(date)
                , Toast.LENGTH_SHORT).show();

        txtMessage.setText("");
        rdbCourse.setChecked(true);
        rdbStartDate.setChecked(true);
    }


    /**
     * Function to validate Message
     *
     * @param message
     * @return true if provided, false otherwise
     */
    public boolean validateInputs(String message, String title) {
        if (message.isEmpty()) {
            txtMessage.setError("Provide Notification Message");
            txtMessage.requestFocus();
            return false;
        }

        if (title.isEmpty()) {
            txtTitle.setError("Provide Notification Title");
            txtTitle.requestFocus();
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
        getActivity().setTitle(R.string.notifications_title);
    }
}
