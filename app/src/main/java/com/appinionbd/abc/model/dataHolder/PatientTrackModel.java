package com.appinionbd.abc.model.dataHolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientTrackModel {

    @SerializedName("patient_id")
    @Expose
    String patientId;

    public PatientTrackModel() {
    }

    public PatientTrackModel(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
