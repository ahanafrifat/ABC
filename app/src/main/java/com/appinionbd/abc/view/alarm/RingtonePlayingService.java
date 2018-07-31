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

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.view.home.HomeActivity;

public class RingtonePlayingService extends Service {

    private boolean inRunning;
    private Context context;
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

        Intent intent1 = new Intent(this.getApplicationContext() , HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent1 , 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Alarm")
                .setContentText("click Me !")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("extra");
        AppUtil.log("RingtonePlayingService" , "State : "+ state);

        if(state != null){
            if(state.equals("yes")){
                startId = 1;
            }
            else
                startId = 0;
        }

        if(!this.inRunning && startId == 1){
            AppUtil.log("RingtonePlayingService" , "RingtonePlayingService is Running");
            mediaPlayer = MediaPlayer.create(this , Notification.DEFAULT_SOUND);
            mediaPlayer.start();
            notificationManager.notify(0 , notification);
            this.inRunning = true ;
            this.startId = 0;
        }
        else if(!this.inRunning){
            AppUtil.log("RingtonePlayingService" , "if there was not sound and you want end");
            this.inRunning = false ;
            this.startId =0;
        }
        else if(this.inRunning && startId == 1){
            AppUtil.log("RingtonePlayingService" , "if there is sound and you want start");
            this.inRunning = true ;
            this.startId =0;
        }
        else{
            AppUtil.log("RingtonePlayingService" , "if there is sound and you want to end");
            mediaPlayer.stop();
            mediaPlayer.reset();
            this.inRunning = false ;
            this.startId =0;
        }

        AppUtil.log("RingtonePlayingService" , "In the service");
        return START_NOT_STICKY;

//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        AppUtil.log("RingtonePlayingService" , "RingtonePlayingService is destroyed");
        super.onDestroy();
    }
}
