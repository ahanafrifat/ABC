package com.appinionbd.abc.view.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appinionbd.abc.appUtils.AppUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        String time;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH :mm: ss : SS z dd ,MMMM yyyy" , Locale.ENGLISH);

        time = simpleDateFormat.format(calendar.getTime());
        AppUtil.log("MyAlarm" , "Alarm just fired : " + time);

//        Intent serviceIntent = new Intent(context , Ring)
    }
}
