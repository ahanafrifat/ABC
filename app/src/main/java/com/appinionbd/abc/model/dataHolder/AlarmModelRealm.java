package com.appinionbd.abc.model.dataHolder;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmModelRealm extends RealmObject {
    @PrimaryKey
    private String alarmId;

    private String state;

    public AlarmModelRealm() {
    }

    public AlarmModelRealm(String alarmId, String state) {
        this.alarmId = alarmId;
        this.state = state;
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
}
