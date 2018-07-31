package com.appinionbd.abc.model.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Monitor {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("monitors_patient_list")
    @Expose
    private List<MonitorsPatientList> monitorsPatientList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MonitorsPatientList> getMonitorsPatientList() {
        return monitorsPatientList;
    }

    public void setMonitorsPatientList(List<MonitorsPatientList> monitorsPatientList) {
        this.monitorsPatientList = monitorsPatientList;
    }

}
