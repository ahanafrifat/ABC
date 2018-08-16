package com.appinionbd.abc.model.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaskCategory extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("task_name")
    @Expose
    private String taskName;
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @SerializedName("tbl_task_category_ser_id")
    @Expose
    private String tblTaskCategorySerId;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("reminder")
    @Expose
    private String reminder;

    @SerializedName("reminder_status")
    @Expose
    private String reminderStatus;

    @SerializedName("task_notes")
    @Expose
    private String taskNotes;
    @SerializedName("task_status")
    @Expose
    private String taskStatus;
    @SerializedName("complet_date")
    @Expose
    private String completDate;
    @SerializedName("task_category")
    @Expose
    private String taskCategory;
    @SerializedName("reminder_time")
    @Expose
    private String reminderTime;


    public TaskCategory() {
    }

    public TaskCategory(String id, String taskName, String taskId, String tblTaskCategorySerId, String startDate, String reminder, String reminderStatus, String taskNotes, String taskStatus, String completDate, String taskCategory, String reminderTime) {
        this.id = id;
        this.taskName = taskName;
        this.taskId = taskId;
        this.tblTaskCategorySerId = tblTaskCategorySerId;
        this.startDate = startDate;
        this.reminder = reminder;
        this.reminderStatus = reminderStatus;
        this.taskNotes = taskNotes;
        this.taskStatus = taskStatus;
        this.completDate = completDate;
        this.taskCategory = taskCategory;
        this.reminderTime = reminderTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTblTaskCategorySerId() {
        return tblTaskCategorySerId;
    }

    public void setTblTaskCategorySerId(String tblTaskCategorySerId) {
        this.tblTaskCategorySerId = tblTaskCategorySerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getReminderStatus() {
        return reminderStatus;
    }

    public void setReminderStatus(String reminderStatus) {
        this.reminderStatus = reminderStatus;
    }

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCompletDate() {
        return completDate;
    }

    public void setCompletDate(String completDate) {
        this.completDate = completDate;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}
