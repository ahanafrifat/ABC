package com.appinionbd.abc.model.dataHolder;

public class ReminderModel {
    int reminderId;
    String reminderName;

    public ReminderModel() {
    }

    public ReminderModel(int reminderId, String reminderName) {
        this.reminderId = reminderId;
        this.reminderName = reminderName;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }
}
