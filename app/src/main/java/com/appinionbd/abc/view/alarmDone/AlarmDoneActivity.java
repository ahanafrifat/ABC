package com.appinionbd.abc.view.alarmDone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;

public class AlarmDoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_done);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        String state = intent.getExtras().getString("extra");
        String id = intent.getExtras().getString("id");
        String taskName = intent.getExtras().getString("taskName");
        String reminderTime = intent.getExtras().getString("reminderTime");
        String taskCategory = intent.getExtras().getString("taskCategory");

        TextView textViewAlarmDoneTime = findViewById(R.id.textView_alarm_done_time);
        ImageView imageViewAlarmDoneStatusIcon = findViewById(R.id.imageView_alarm_done_status_icon);
        Button buttonAlarmDone = findViewById(R.id.button_alarm_done);


    }
}
