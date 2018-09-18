package com.appinionbd.abc.presenter;


import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.UpdateReminderInterface.IUpdateReminder;
import com.appinionbd.abc.interfaces.homeInterface.IHomeFragmentInterface;
import com.appinionbd.abc.interfaces.presenterInterface.IHome;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.PatientWithDate;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.model.uploadDataModel.UpdateReminderModel;
import com.appinionbd.abc.networking.taskInformation.ApiTask;
import com.appinionbd.abc.networking.updateReminderApi.ApiUpdateReminder;

import java.util.List;

import io.realm.Realm;

public class HomePresenter  implements IHome.Presenter{
    private IHome.View view;
    private static int STATE_NO = 0;
    private static int STATE_YES = 1;
    private static int STATE_DONE = 2;

    private String STRING_STATE_NO = "0";
    private String STRING_STATE_YES = "1";
    private String STRING_STATE_DONE = "2";

    public HomePresenter() {
    }

    public HomePresenter(IHome.View view) {
        this.view = view;
    }

    @Override
    public void sendDate(String time) {
        String token;
        PatientWithDate patientWithDate = new PatientWithDate();

        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();

            token = userInfo.getToken();

            patientWithDate.setPatientId(Integer.valueOf(userInfo.getUserId()));

            patientWithDate.setDate(time);

            AppUtil.log("checkPatientId" , "ID " + userInfo.getUserId());
        }

        ApiTask.getApiTask().setApiTask(token, patientWithDate, new IHomeFragmentInterface() {
            @Override
            public void successful(List<TaskCategory> taskCategories) {
                AppUtil.log("HomePresenter" , "size : " +taskCategories.size());
                view.taskList(taskCategories);
            }

            @Override
            public void noNewInfo(String message) {

            }

            @Override
            public void unAuthorized(String message) {

            }

            @Override
            public void error(String message) {

            }

            @Override
            public void noContent(String message) {
                view.taskListEmpty(message);
            }

            @Override
            public void connectionProblem(String message) {

            }
        });
    }

    @Override
    public void checkReminder(String reminderTime, String id, String taskId, String reminderStatus) {

        String alarmId = id.concat(taskId);

        AlarmModel alarmModelSave = new AlarmModel();

        try(Realm realm = Realm.getDefaultInstance()){

            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , alarmId)
                    .findFirst();

            alarmModelSave.setAlarmId(alarmModel.getAlarmId());
            alarmModelSave.setState(alarmModel.getState());
            alarmModelSave.setTime(alarmModel.getTime());

            realm.executeTransaction(realm1 -> {
                realm1.insertOrUpdate(alarmModelSave);
            });
        }

    }

    @Override
    public void reminderOnAndSaveInDatabase(TaskCategory taskCategory, int alarmOn) {

        String reminderId = taskCategory.getId();
        String alarmId = taskCategory.getId() +""+ taskCategory.getTaskId();

        updateStatus(alarmOn , reminderId , alarmId , taskCategory);
    }

    @Override
    public void reminderOffAndSaveInDatabase(TaskCategory taskCategory, int alarmOff) {

        String reminderId = taskCategory.getId();
        String alarmId = taskCategory.getId() +""+ taskCategory.getTaskId();

        updateStatus(alarmOff , reminderId , alarmId , taskCategory);
    }

    private void updateStatus(int state, String id, String alarmId, TaskCategory taskCategory) {

        int STATE_NO = 0 ;
        int STATE_YES = 1 ;
        int STATE_DONE = 2 ;

        String token;
        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();

        }

        UpdateReminderModel updateReminderModel = new UpdateReminderModel();
        updateReminderModel.setReminderId(Integer.valueOf(id));
        updateReminderModel.setStatus(String.valueOf(state));

        ApiUpdateReminder.getApiUpdateReminder().setApiUpdateReminder(token, updateReminderModel, new IUpdateReminder() {
            @Override
            public void successfullyUpdated(String message) {
                if(state == STATE_YES){
                    updateDatabase(alarmId , state);
                    view.alarmOn( message , taskCategory);
                }
                else if(state == STATE_NO){
                    updateDatabase(alarmId , state);
                    view.alarmOff( "Alarm Off" , taskCategory);
                }
                else{
                    view.alarmStatusChangeError("Something Went Wrong !" + message);
                }

            }

            @Override
            public void errorInUpdateReminder(String message) {
                view.alarmStatusChangeError(message);
            }

            @Override
            public void connectionErrorInUpdateReminder(String message) {
                view.alarmStatusChangeConnectionError(message);
            }
        });
    }

    private void updateDatabase(String alarmId, int state) {

        AlarmModel alarmModelSave = new AlarmModel();

        try(Realm realm = Realm.getDefaultInstance()){

            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , alarmId)
                    .findFirst();

            alarmModelSave.setAlarmId(alarmModel.getAlarmId());
            alarmModelSave.setState(String.valueOf(state));
            alarmModelSave.setTime(alarmModel.getTime());

            realm.executeTransaction(realm1 -> {
                realm1.insertOrUpdate(alarmModelSave);
            });
        }
    }
}
