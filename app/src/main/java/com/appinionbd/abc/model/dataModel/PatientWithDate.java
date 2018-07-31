package com.appinionbd.abc.model.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientWithDate {

    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
