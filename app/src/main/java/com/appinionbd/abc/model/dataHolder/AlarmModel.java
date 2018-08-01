package com.appinionbd.abc.model.dataHolder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmModel extends RealmObject {

    @PrimaryKey
    private String alarmId;

    private String state;

    private String time;

    public AlarmModel() {
    }

    public AlarmModel(String alarmId, String state, String time) {
        this.alarmId = alarmId;
        this.state = state;
        this.time = time;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
