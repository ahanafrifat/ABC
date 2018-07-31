package com.appinionbd.abc.model.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskFrequencyHistory {

    @SerializedName("task_action_date")
    @Expose
    private String taskActionDate;
    @SerializedName("task_done_status")
    @Expose
    private Integer taskDoneStatus;

    public String getTaskActionDate() {
        return taskActionDate;
    }

    public void setTaskActionDate(String taskActionDate) {
        this.taskActionDate = taskActionDate;
    }

    public Integer getTaskDoneStatus() {
        return taskDoneStatus;
    }

    public void setTaskDoneStatus(Integer taskDoneStatus) {
        this.taskDoneStatus = taskDoneStatus;
    }

}
