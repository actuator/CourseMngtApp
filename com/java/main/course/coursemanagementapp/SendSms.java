package com.java.main.course.coursemanagementapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
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
public class SendSms extends Fragment {

    private Bundle bundle;
    private EditText txtTitle;
    private EditText txtDescription;
    private Button btnSend;
    private EditText txtPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_sms, container, false);

        // components
        txtTitle = (EditText) view.findViewById(R.id.txtNoteSmsTitle);
        txtDescription = (EditText) view.findViewById(R.id.txtNoteSmsDescription);
        btnSend = (Button) view.findViewById(R.id.btnSendNoteSMS);
        txtPhone = (EditText) view.findViewById(R.id.txtSendSmsPhone);

        bundle = getArguments();
        txtTitle.setText(bundle.getString("title"));
        txtDescription.setText(bundle.getString("description"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        return view;
    }

    /**
     * Function to send Sms from the application
     *
     * Reference: https://google-developer-training.github.io/android-developer-phone-sms-course/Lesson%202/2_p_sending_sms_messages.html
     *
     * @param view
     */
    public void sendSms(View view) {

        // Set the destination phone number to the string in editText.
        String destinationAddress = txtPhone.getText().toString();

        // Get the text of the SMS message.
        String smsMessage = txtDescription.getText().toString();

        // Set the service center address if needed, otherwise null.
        String scAddress = null;

        // Set pending intents to broadcast
        // when message sent and when delivered, or set to null.
        PendingIntent sentIntent = null, deliveryIntent = null;

        // Use SmsManager.
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage
                (destinationAddress, scAddress, smsMessage,
                        sentIntent, deliveryIntent);
    }

    private void sendSms() {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", txtDescription.getText().toString());
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);
        } catch(Exception e) {
            Toast.makeText(getActivity(),
                    "SMS Sending failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
