package com.appinionbd.abc.model.uploadDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateReminderModel {

    @SerializedName("reminder_id")
    @Expose
    private Integer reminderId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getReminderId() {
        return reminderId;
    }

    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
