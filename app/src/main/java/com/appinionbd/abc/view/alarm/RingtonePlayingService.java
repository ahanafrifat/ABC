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
import com.appinionbd.abc.presenter.ChoosePatientOrMonitorPresenter;
import com.appinionbd.abc.view.home.HomeActivity;

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

        String state = intent.getExtras().getString("extra");
        String id = intent.getExtras().getString("id");

        Intent intent1 = new Intent(this.getApplicationContext() , ChoosePatientOrMonitorPresenter.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , Integer.parseInt(id), intent1 , 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Alarm")
                .setContentText("click Me !")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_patient)
                .build();


        AppUtil.log("RingtonePlayingService" , "State : "+ state + " ID : " + id);

        if(state != null){
            if(state.equals("yes")){
                startId = 1;
            }
            else
                startId = 0;
        }

//        assert state != null;
//        switch (state) {
//            case "no":
//                startId = 0;
//                break;
//            case "yes":
//                startId = 1;
//                break;
//            default:
//                startId = 0;
//                break;
//        }

        if(!this.inRunning && startId == 1){
            AppUtil.log("RingtonePlayingService" , "RingtonePlayingService is Running");

            mediaPlayer = MediaPlayer.create(this , R.raw.richard_dawkins_1);
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
        this.inRunning = false;
    }
}
