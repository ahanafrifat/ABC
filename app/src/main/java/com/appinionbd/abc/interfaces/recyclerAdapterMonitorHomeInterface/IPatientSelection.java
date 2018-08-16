package com.appinionbd.abc.interfaces.recyclerAdapterMonitorHomeInterface;

import com.appinionbd.abc.model.dataModel.MonitorsPatientList;

public interface IPatientSelection {
    void selectedPatient(MonitorsPatientList monitorsPatientList);
    void deletePatient(MonitorsPatientList monitorsPatientList);
}
