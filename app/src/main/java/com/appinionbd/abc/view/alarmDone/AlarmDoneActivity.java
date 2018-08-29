package com.appinionbd.abc.view.alarmDone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.IAlarmDone;
import com.appinionbd.abc.presenter.AlarmDone;
import com.appinionbd.abc.view.alarm.AlarmReceiver;

import es.dmoral.toasty.Toasty;

public class AlarmDoneActivity extends AppCompatActivity implements IAlarmDone.View {

    private final String STATE_NO = "no";

    IAlarmDone.Presenter alarmDonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_done);
    }

    @Override
    protected void onStart() {
        super.onStart();

        alarmDonePresenter = new AlarmDone(this);
        Intent intent = getIntent();

        String state = intent.getExtras().getString("extra");
        String alarmId = intent.getExtras().getString("alarmId");
        String taskName = intent.getExtras().getString("taskName");
        String reminderTime = intent.getExtras().getString("reminderTime");
        String taskCategory = intent.getExtras().getString("taskCategory");
        String reminderId = intent.getExtras().getString("reminderId");

        TextView textViewAlarmDoneName = findViewById(R.id.textView_alarm_done_name);
        TextView textViewAlarmDoneTime = findViewById(R.id.textView_alarm_done_time);
        ImageView imageViewAlarmDoneStatusIcon = findViewById(R.id.imageView_alarm_done_status_icon);
        Button buttonAlarmDone = findViewById(R.id.button_alarm_done);

        textViewAlarmDoneName.setText(taskName);
        textViewAlarmDoneTime.setText(reminderTime);

        if(taskCategory.equals("Pill Reminder")){
            imageViewAlarmDoneStatusIcon.setImageResource(R.drawable.ic_drug);
        }
        else if(taskCategory.equals("Walking")){
            imageViewAlarmDoneStatusIcon.setImageResource(R.drawable.ic_directions_walk_24dp);
        }
        else if(taskCategory.equals("Exercise")){
            imageViewAlarmDoneStatusIcon.setImageResource(R.drawable.ic_directions_run_24dp);
        }

        buttonAlarmDone.setOnClickListener(v -> alarmDonePresenter.alarmDone(reminderId , alarmId));

    }

    @Override
    public void successfulAlarmDone(String alarmId, String message) {
        Toasty.success(this, message , Toast.LENGTH_LONG , true).show();
        alarmOff(alarmId);
    }

    @Override
    public void error(String message) {
        Toasty.error(this, message , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void errorInAlarmDone(String message) {
        Toasty.error(this, message , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void connectionErrorInAlarmdone(String message) {
        Toasty.error(this, message , Toast.LENGTH_LONG , true).show();
    }

    public void alarmOff( String alarmId) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        intentAlarm.putExtra("extra" , STATE_NO);
        intentAlarm.putExtra("alarmId" , alarmId);
        intentAlarm.putExtra("taskName" , "temp");
        intentAlarm.putExtra("reminderTime" , "temp");
        intentAlarm.putExtra("taskCategory" , "temp");

//        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this ,
                Integer.parseInt(alarmId),
                intentAlarm ,
                PendingIntent.FLAG_UPDATE_CURRENT );

        this.sendBroadcast(intentAlarm);

        alarmManager.cancel(pendingIntent);

    }
}
