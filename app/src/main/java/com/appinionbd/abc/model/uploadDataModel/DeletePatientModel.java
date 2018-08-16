package com.appinionbd.abc.model.uploadDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeletePatientModel {
    @SerializedName("patient_rel_id")
    @Expose
    private Integer patientRelId;

    public DeletePatientModel() {
    }

    public DeletePatientModel(Integer patientRelId) {
        this.patientRelId = patientRelId;
    }

    public Integer getPatientRelId() {
        return patientRelId;
    }

    public void setPatientRelId(Integer patientRelId) {
        this.patientRelId = patientRelId;
    }

}
