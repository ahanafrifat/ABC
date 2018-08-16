package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.Monitor;
import com.appinionbd.abc.model.dataModel.MonitorsPatientList;

public interface IMonitorHome {
    interface View{

        void showTrackList(Monitor monitor);

        void successfullyDeletedPatient(String message);

        void errorInDeletePatient(String message);

        void connectionErrorInDeletePatient(String message);
    }

    interface Presenter{
        void getMonitorList();

        void deletePatientParmanently(MonitorsPatientList monitorsPatientList);
    }
}
