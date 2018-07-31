package com.appinionbd.abc.model.dataHolder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

public class AlarmModel {

    private String alarmId;

    private String state;

    private Intent intent;

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;


    public AlarmModel() {
    }

    public AlarmModel(String alarmId, AlarmManager alarmManager, Intent intent, PendingIntent pendingIntent, String state) {
        this.alarmId = alarmId;
        this.alarmManager = alarmManager;
        this.intent = intent;
        this.pendingIntent = pendingIntent;
        this.state = state;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public void setAlarmManager(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
