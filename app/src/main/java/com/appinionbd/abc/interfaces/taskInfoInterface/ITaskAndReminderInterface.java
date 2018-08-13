package com.appinionbd.abc.interfaces.taskInfoInterface;

import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;
import com.appinionbd.abc.model.dataModel.ReminderList;

import java.util.List;

public interface ITaskAndReminderInterface {
    void patientTaskList(List<PatientWiseTaskList> patientWiseTaskLists);
    void emptyPatientTaskList(String message);
    void serverError(String message);
    void connectionError(String message);
}
