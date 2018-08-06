package com.appinionbd.abc.view.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent intentAlarm;

    private TextView textViewReminderActivityId;
    private TextView textViewReminderActivityTime;
    private ImageView imageViewReminderActivityStatus;

    private final String STATE = "extra";
    private final String STATE_YES = "yes";
    private final String STATE_NO = "no";

    private String id;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        id = intent.getStringExtra("ID");
        time = intent.getStringExtra("TIME");

        textViewReminderActivityId = findViewById(R.id.textView_reminder_activity_Id);
        textViewReminderActivityTime = findViewById(R.id.textView_reminder_activity_time);
        imageViewReminderActivityStatus = findViewById(R.id.imageView_reminder_activity_status);

        textViewReminderActivityId.setText(id);
        textViewReminderActivityTime.setText(time);

        intentAlarm = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        imageViewReminderActivityStatus.setOnClickListener(v -> {
            setAlarm();
        });
    }

    private void setAlarm() {
        Calendar cal = Calendar.getInstance();
        String time = "14:58:00 06-08-2018";
//                "yyyy-MM-dd'T'HH:mm:ssZ"
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy", Locale.getDefault());

        try {
            cal.setTime(sdf.parse(time));
            AppUtil.log("Check", "time = " + cal.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intentAlarm.putExtra("extra" , STATE_YES);
        pendingIntent = PendingIntent.getBroadcast(this , 0 , intentAlarm , PendingIntent.FLAG_UPDATE_CURRENT);

        long checkTime = cal.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP , checkTime , pendingIntent);

        imageViewReminderActivityStatus.setImageResource(R.drawable.ic_notifications_active_24dp);

    }
}
