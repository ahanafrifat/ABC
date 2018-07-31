package com.appinionbd.abc.model.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskHistory {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("task_frequency_history")
    @Expose
    private List<TaskFrequencyHistory> taskFrequencyHistory = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TaskFrequencyHistory> getTaskFrequencyHistory() {
        return taskFrequencyHistory;
    }

    public void setTaskFrequencyHistory(List<TaskFrequencyHistory> taskFrequencyHistory) {
        this.taskFrequencyHistory = taskFrequencyHistory;
    }

}
