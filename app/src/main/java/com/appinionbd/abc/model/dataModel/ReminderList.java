package com.appinionbd.abc.model.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReminderList extends RealmObject {

    @PrimaryKey
    @SerializedName("reminder_id")
    @Expose
    private String reminderId;
    @SerializedName("reminder_date")
    @Expose
    private String reminderDate;
    @SerializedName("reminder_time")
    @Expose
    private String reminderTime;
    @SerializedName("complete_date")
    @Expose
    private String completeDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
