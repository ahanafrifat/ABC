package com.appinionbd.abc.view.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appinionbd.abc.appUtils.AppUtil;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        String id = intent.getExtras().getString("id");
        AppUtil.log("AlarmReceiver", "In the receiver with " + state + " ID : " + id);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("id" , id);

        context.startService(serviceIntent);
    }
}
