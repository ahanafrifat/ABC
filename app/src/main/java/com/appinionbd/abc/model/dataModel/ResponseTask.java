package com.appinionbd.abc.model.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTask {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("task_category")
    @Expose
    private List<TaskCategory> taskCategory = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TaskCategory> getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(List<TaskCategory> taskCategory) {
        this.taskCategory = taskCategory;
    }

}
