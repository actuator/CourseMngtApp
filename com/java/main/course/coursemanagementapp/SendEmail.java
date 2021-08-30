package com.java.main.course.coursemanagementapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendEmail extends Fragment {

    private Bundle bundle;
    private EditText txtTitle;
    private EditText txtDescription;
    private Button btnSend;
    private EditText txtEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);

        // components
        txtTitle = (EditText) view.findViewById(R.id.txtNoteEmailTitle);
        txtDescription = (EditText) view.findViewById(R.id.txtNoteEmailDescription);
        btnSend = (Button) view.findViewById(R.id.btnSendNoteEmail);
        txtEmail = (EditText) view.findViewById(R.id.txtSendEmail);

        bundle = getArguments();
        txtTitle.setText(bundle.getString("title"));
        txtDescription.setText(bundle.getString("description"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendEmail();
            }
        });

        return view;
    }

    private void sendEmail() {

        String email = txtEmail.getText().toString().trim();
        String title = txtTitle.getText().toString().trim();
        String description = txtDescription.getText().toString().trim();

        String mailto = String.format("mailto:%s?subject=%s&body=%s", email, Uri.encode(title), Uri.encode(description));
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Could not Send Email", Toast.LENGTH_SHORT).show();
        }
    }
}
