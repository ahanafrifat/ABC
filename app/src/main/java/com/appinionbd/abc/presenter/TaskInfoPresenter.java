package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.ITaskInfo;
import com.appinionbd.abc.interfaces.taskInfoInterface.ITaskAndReminderInterface;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.taskHistory.ApiTaskHistory;

import java.util.List;

import io.realm.Realm;

public class TaskInfoPresenter implements ITaskInfo.Presenter{
    private ITaskInfo.View view;

    public TaskInfoPresenter() {
    }

    public TaskInfoPresenter(ITaskInfo.View view) {
        this.view = view;
    }

    @Override
    public void reminderList(String taskId) {
        String userId;
        String token;
        try(Realm realm = Realm.getDefaultInstance()){
            User user = realm.where(User.class).findFirst();
            userId = user.getUserId();
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }

        ApiTaskHistory.getApiTaskHistory().setApiTaskHistory(token, userId, new ITaskAndReminderInterface() {
            @Override
            public void patientTaskList(List<PatientWiseTaskList> patientWiseTaskLists) {
                for(PatientWiseTaskList patientWiseTaskList : patientWiseTaskLists){
                    if(patientWiseTaskList.getTaskId().equals(taskId)){

                        view.showReminderList(patientWiseTaskList.getReminderList() , patientWiseTaskList.getTaskName() ,patientWiseTaskList.getTaskCategory());
                        break;
                    }
                }
            }

            @Override
            public void emptyPatientTaskList(String message) {

            }

            @Override
            public void serverError(String message) {

            }

            @Override
            public void connectionError(String message) {

            }
        });
    }
}
