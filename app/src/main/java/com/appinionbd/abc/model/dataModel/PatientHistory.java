package com.appinionbd.abc.model.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientHistory {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("patient_wise_task_list")
    @Expose
    private List<PatientWiseTaskList> patientWiseTaskList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PatientWiseTaskList> getPatientWiseTaskList() {
        return patientWiseTaskList;
    }

    public void setPatientWiseTaskList(List<PatientWiseTaskList> patientWiseTaskList) {
        this.patientWiseTaskList = patientWiseTaskList;
    }

}
