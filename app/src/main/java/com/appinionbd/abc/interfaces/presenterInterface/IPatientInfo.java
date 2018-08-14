package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;

import java.util.List;

public interface IPatientInfo {
    interface View{
        void showTaskList(List<PatientWiseTaskList> patientWiseTaskLists);
    }

    interface Presenter{
        void getInfo(String patientId);
    }
}
