package com.appinionbd.abc.view.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.view.alarmDone.AlarmDoneActivity;
import com.appinionbd.abc.view.choosePatientOrMonitor.ChoosePatientOrMonitorActivity;

public class RingtonePlayingService extends Service {

    private boolean inRunning;
    MediaPlayer mediaPlayer;
    private int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AppUtil.log("RingtonePlayingService" , "IBinder service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext() , AlarmDoneActivity.class);

        String state = intent.getExtras().getString("extra");
        String alarmIdString = intent.getExtras().getString("alarmId");
        String taskName = intent.getExtras().getString("taskName");
        String reminderTime = intent.getExtras().getString("reminderTime");
        String taskCategory = intent.getExtras().getString("taskCategory");
        String reminderId = intent.getExtras().getString("reminderId");

        int alarmId = Integer.parseInt(alarmIdString);

        intent1.putExtra("extra", state);
        intent1.putExtra("alarmId" , alarmIdString);
        intent1.putExtra("taskName" , taskName);
        intent1.putExtra("reminderTime" , reminderTime);
        intent1.putExtra("taskCategory" , taskCategory);
        intent1.putExtra("reminderId" , reminderId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this , alarmId, intent1 , 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(taskName)
                .setContentText("TIme : " + reminderTime)
                .setSmallIcon(R.drawable.ic_patient)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
//                .setContentTitle("Alarm")
//                .setContentText("click Me !")
//                .setTicker("Alert New Message")
//                .setSmallIcon(R.drawable.ic_patient);


//        String state = intent.getExtras().getString("extra");

//        AppUtil.log("RingtonePlayingService" , "State : "+ state + " ID : " + id);

//        if(state != null){
//            if(state.equals("yes")){
//                startId = 1;
//            }
//            else
//                startId = 0;
//        }

        assert state != null;
        switch (state) {
            case "no":
                startId = alarmId;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = alarmId;
                break;
        }

        if(!this.inRunning && startId == 1){
            AppUtil.log("RingtonePlayingService" , "RingtonePlayingService is Running");

            mediaPlayer = MediaPlayer.create(this , R.raw.richard_dawkins_1);
            mediaPlayer.start();

            notificationManager.notify(1 , notification);
//            notificationManager.notify(0 , notification);

            this.inRunning = true ;
            this.startId = alarmId;
        }
        else if(!this.inRunning && startId == alarmId){
            AppUtil.log("RingtonePlayingService" , "if there was not sound and you want end");
            this.inRunning = false ;
            this.startId =alarmId;
        }
        else if(this.inRunning && startId == 1){
            AppUtil.log("RingtonePlayingService" , "if there is sound and you want start");
            this.inRunning = true ;
            this.startId =alarmId;
        }
        else{
            AppUtil.log("RingtonePlayingService" , "if there is sound and you want to end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.inRunning = false ;
            this.startId =alarmId;
        }

        AppUtil.log("RingtonePlayingService" , "In the service");
        return START_NOT_STICKY;

//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        AppUtil.log("RingtonePlayingService" , "RingtonePlayingService is destroyed");
        super.onDestroy();
        this.inRunning = false;
    }
}
