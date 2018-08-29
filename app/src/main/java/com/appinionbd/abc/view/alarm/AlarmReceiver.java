package com.appinionbd.abc.view.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appinionbd.abc.appUtils.AppUtil;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {


        String state = intent.getExtras().getString("extra");
        String alarmId = intent.getExtras().getString("alarmId");
        String taskName = intent.getExtras().getString("taskName");
        String reminderTime = intent.getExtras().getString("reminderTime");
        String taskCategory = intent.getExtras().getString("taskCategory");
        String reminderId = intent.getExtras().getString("reminderId");


//        AppUtil.log("AlarmReceiver", "In the receiver with " + state + " ID : " + id);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);

        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("alarmId" , alarmId);
        serviceIntent.putExtra("taskName" , taskName);
        serviceIntent.putExtra("reminderTime" , reminderTime);
        serviceIntent.putExtra("taskCategory" , taskCategory);
        serviceIntent.putExtra("reminderId" , reminderId);

        context.startService(serviceIntent);
    }
}
