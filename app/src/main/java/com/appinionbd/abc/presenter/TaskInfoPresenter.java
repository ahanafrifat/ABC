package com.appinionbd.abc.presenter;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.deleteTaskInterface.IDeleteTaskInterface;
import com.appinionbd.abc.interfaces.presenterInterface.ITaskInfo;
import com.appinionbd.abc.interfaces.taskInfoInterface.ITaskAndReminderInterface;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;
import com.appinionbd.abc.model.dataModel.ReminderList;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.DeleteTaskApi.ApiDeleteTask;
import com.appinionbd.abc.networking.taskHistory.ApiTaskHistory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TaskInfoPresenter implements ITaskInfo.Presenter{
    private ITaskInfo.View view;

    public TaskInfoPresenter() {
    }

    public TaskInfoPresenter(ITaskInfo.View view) {
        this.view = view;
    }

    @Override
    public void reminderList(String taskId) {
        String taskTitle;
        String category;

        List<ReminderList> reminderLists = new ArrayList<>();

        try(Realm realm = Realm.getDefaultInstance()){

            PatientWiseTaskList patientWiseTaskList = realm.where(PatientWiseTaskList.class)
                    .equalTo("taskId" , taskId)
                    .findFirst();
            taskTitle = patientWiseTaskList.getTaskName();
            category = patientWiseTaskList.getStatus();

            for(ReminderList reminderList : patientWiseTaskList.getReminderList()){
                ReminderList tempReminderList = new ReminderList();

                tempReminderList.setReminderId(reminderList.getReminderId());
                tempReminderList.setReminderDate(reminderList.getReminderDate());
                tempReminderList.setReminderTime(reminderList.getReminderTime());
                tempReminderList.setCompleteDate(reminderList.getCompleteDate());
                tempReminderList.setStatus(reminderList.getStatus());

                reminderLists.add(tempReminderList);
            }
        }


        view.showReminderList(reminderLists , taskTitle ,category);

    }

    @Override
    public void deleteTask(String taskId) {
        String token;
        AppUtil.log("TaskInfoPresenter","task id : " + taskId);
        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }
        ApiDeleteTask.getApiDeleteTask().setApiDeleteTask(token, taskId, new IDeleteTaskInterface() {
            @Override
            public void successful(String message) {
                view.confirmDeleteTask(message);
            }

            @Override
            public void error(String message) {
                view.deleteError(message);
            }

            @Override
            public void connectionError(String message) {
                view.connectionError(message);
            }
        });
//        view.confirmDeleteTask("ss");
    }
}
