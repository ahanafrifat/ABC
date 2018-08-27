package com.appinionbd.abc.presenter;


import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.homeInterface.IHomeFragmentInterface;
import com.appinionbd.abc.interfaces.presenterInterface.IHome;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.PatientWithDate;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.networking.taskInformation.ApiTask;

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
    public void checkReminder(String id, ImageView imageViewTime, Button buttonDone, String taskId) {

        int checkAlarm = 0;
        String tempTime;

        try(Realm realm = Realm.getDefaultInstance()){

            TaskCategory taskCategory = realm.where(TaskCategory.class)
                    .equalTo("taskId" , taskId)
                    .and()
                    .equalTo("id" , id)
                    .findFirst();
//            if(taskCategory.getReminderStatus().equals(STRING_STATE_NO)){
//                checkAlarm = STATE_NO;
//            }
//            else if(taskCategory.getReminderStatus().equals(STRING_STATE_YES)){
//                checkAlarm = STATE_YES;
//            }
//            else if(taskCategory.getReminderStatus().equals(STRING_STATE_DONE)){
//                checkAlarm = STATE_DONE;
//            }
        }
//        if(checkAlarm == STATE_NO) {
//            AlarmModel tempAlarmModel = new AlarmModel();
//            try(Realm realm = Realm.getDefaultInstance()) {
//                AlarmModel alarmModel = realm.where(AlarmModel.class)
//                        .equalTo("alarmId" , id)
//                        .findFirst();
//
//                tempAlarmModel.setAlarmId(id);
//                tempAlarmModel.setState("no");
//                tempAlarmModel.setTime(alarmModel.getTime());
//                tempTime = alarmModel.getTime();
//
//                realm.executeTransaction(realm1 -> {
//                    realm1.insertOrUpdate(alarmModel);
//                });
//            }
//            view.notificationAndAlarmOff(id ,imageViewTime , buttonDone);
////            view.notificationAndAlarmON(id ,imageViewTime , buttonDone , tempTime);
//        }
//        else if(checkAlarm == STATE_YES) {
//            AlarmModel tempAlarmModel = new AlarmModel();
//            try(Realm realm = Realm.getDefaultInstance()) {
//                AlarmModel alarmModel = realm.where(AlarmModel.class)
//                        .equalTo("alarmId" , id)
//                        .findFirst();
//                tempAlarmModel.setAlarmId(id);
//                tempAlarmModel.setState("yes");
//                tempAlarmModel.setTime(alarmModel.getTime());
//
//                tempTime = alarmModel.getTime();
//
//
//                realm.executeTransaction(realm1 -> {
//                    realm1.insertOrUpdate(tempAlarmModel);
//                });
//            }
//            view.notificationAndAlarmON(id ,imageViewTime , buttonDone , tempTime);
////            view.notificationAndAlarmOff(id ,imageViewTime , buttonDone);
//        }

    }

//    @Override
//    public void taskDone(String id) {
//        try(Realm realm = Realm.getDefaultInstance()) {
//            AlarmModel alarmModel = realm.where(AlarmModel.class)
//                    .equalTo("alarmId" , id)
//                    .findFirst();
//            alarmModel.setState("no");
//            realm.executeTransaction(realm1 -> {
//                realm1.insertOrUpdate(alarmModel);
//            });
//        }
//        view.notificationAndAlarmOff(id);
//    }
}
