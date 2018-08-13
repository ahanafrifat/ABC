package com.appinionbd.abc.model.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class PatientWiseTaskList {

    @PrimaryKey
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @SerializedName("task_name")
    @Expose
    private String taskName;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("num_of_days")
    @Expose
    private String numOfDays;
    @SerializedName("complete_date")
    @Expose
    private String completeDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("task_notes")
    @Expose
    private String taskNotes;
    @SerializedName("task_category")
    @Expose
    private String taskCategory;
    @SerializedName("reminder_list")
    @Expose
    private RealmList<ReminderList> reminderList = null;

    public PatientWiseTaskList() {
    }

    public PatientWiseTaskList(String taskId, String taskName, String startDate, String endDate, String numOfDays, String completeDate, String status, String taskNotes, String taskCategory, RealmList<ReminderList> reminderList) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numOfDays = numOfDays;
        this.completeDate = completeDate;
        this.status = status;
        this.taskNotes = taskNotes;
        this.taskCategory = taskCategory;
        this.reminderList = reminderList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(String numOfDays) {
        this.numOfDays = numOfDays;
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

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public RealmList<ReminderList> getReminderList() {
        return reminderList;
    }

    public void setReminderList(RealmList<ReminderList> reminderList) {
        this.reminderList = reminderList;
    }
}
