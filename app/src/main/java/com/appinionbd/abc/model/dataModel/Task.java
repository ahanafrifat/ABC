package com.appinionbd.abc.model.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("task_name")
    @Expose
    private String taskName;
    @SerializedName("tbl_user_user_id")
    @Expose
    private Integer tblUserUserId;
    @SerializedName("tbl_task_category_ser_id")
    @Expose
    private Integer tblTaskCategorySerId;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("num_of_days")
    @Expose
    private Integer numOfDays;
    @SerializedName("task_frequency")
    @Expose
    private Integer taskFrequency;
    @SerializedName("specific_days")
    @Expose
    private Object specificDays;
    @SerializedName("days_interval")
    @Expose
    private Object daysInterval;
    @SerializedName("reminder")
    @Expose
    private Integer reminder;
    @SerializedName("task_notes")
    @Expose
    private String taskNotes;
    @SerializedName("reminder_list")
    @Expose
    private List<String> reminderList;

    public Task() {
    }

    public Task(String taskName, Integer tblUserUserId, Integer tblTaskCategorySerId, String startDate, Integer numOfDays, Integer taskFrequency, Object specificDays, Object daysInterval, Integer reminder, String taskNotes, List<String> reminderList) {
        this.taskName = taskName;
        this.tblUserUserId = tblUserUserId;
        this.tblTaskCategorySerId = tblTaskCategorySerId;
        this.startDate = startDate;
        this.numOfDays = numOfDays;
        this.taskFrequency = taskFrequency;
        this.specificDays = specificDays;
        this.daysInterval = daysInterval;
        this.reminder = reminder;
        this.taskNotes = taskNotes;
        this.reminderList = reminderList;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTblUserUserId() {
        return tblUserUserId;
    }

    public void setTblUserUserId(Integer tblUserUserId) {
        this.tblUserUserId = tblUserUserId;
    }

    public Integer getTblTaskCategorySerId() {
        return tblTaskCategorySerId;
    }

    public void setTblTaskCategorySerId(Integer tblTaskCategorySerId) {
        this.tblTaskCategorySerId = tblTaskCategorySerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(Integer numOfDays) {
        this.numOfDays = numOfDays;
    }

    public Integer getTaskFrequency() {
        return taskFrequency;
    }

    public void setTaskFrequency(Integer taskFrequency) {
        this.taskFrequency = taskFrequency;
    }

    public Object getSpecificDays() {
        return specificDays;
    }

    public void setSpecificDays(Object specificDays) {
        this.specificDays = specificDays;
    }

    public Object getDaysInterval() {
        return daysInterval;
    }

    public void setDaysInterval(Object daysInterval) {
        this.daysInterval = daysInterval;
    }

    public Integer getReminder() {
        return reminder;
    }

    public void setReminder(Integer reminder) {
        this.reminder = reminder;
    }

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }

    public List<String> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<String> reminderList) {
        this.reminderList = reminderList;
    }
}
