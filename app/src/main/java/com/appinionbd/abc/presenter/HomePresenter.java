package com.appinionbd.abc.presenter;


import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.homeInterface.IHomeFragmentInterface;
import com.appinionbd.abc.interfaces.presenterInterface.IHome;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataHolder.AlarmModelRealm;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.PatientWithDate;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.networking.taskInformation.ApiTask;

import java.util.List;

import io.realm.Realm;

public class HomePresenter  implements IHome.Presenter{
    private IHome.View view;

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
            public void connectionProblem(String message) {

            }
        });
    }

    @Override
    public void saveReminder(AlarmModel alarmModel) {

        AlarmModelRealm alarmModelRealm = new AlarmModelRealm();

        alarmModelRealm.setAlarmId(alarmModel.getAlarmId());
        alarmModelRealm.setState(alarmModel.getState());

        try(Realm realmInstance = Realm.getDefaultInstance()){
            realmInstance.executeTransaction(realm -> {
                realm.insertOrUpdate(alarmModelRealm);
            });
        }
    }

    @Override
    public void checkReminder(String time, int layoutPosition, List<AlarmModel> alarmModels, ImageView imageViewTime, Button buttonDone) {


        view.notificationAndAlarmON(time, layoutPosition, alarmModels, imageViewTime, buttonDone);
        String tempAlarmId = alarmModels.get(layoutPosition).getAlarmId();
        boolean checkAlarm = false;

        try(Realm realm = Realm.getDefaultInstance()){
            AlarmModelRealm alarmModelRealm = realm.where(AlarmModelRealm.class)
                    .equalTo("alarmId" , tempAlarmId)
                    .findFirst();
            if(alarmModelRealm.getState().equals("yes")) {
                checkAlarm = true;
            }
            else if(alarmModelRealm.getState().equals("no")) {
                checkAlarm = false;
            }
        }
        if(checkAlarm) {
//            try(Realm realm = Realm.getDefaultInstance()) {
//                AlarmModelRealm alarmModelRealm = realm.where(AlarmModelRealm.class)
//                        .equalTo("alarmId" , tempAlarmId)
//                        .findFirst();
//                alarmModelRealm.setState("no");
//                realm.executeTransaction(realm1 -> {
//                    realm1.insertOrUpdate(alarmModelRealm);
//                });
//            }
            view.notificationAndAlarmOff(time, layoutPosition, alarmModels, imageViewTime, buttonDone);
        }
        else if(!checkAlarm) {
//            try(Realm realm = Realm.getDefaultInstance()) {
//                AlarmModelRealm alarmModelRealm = realm.where(AlarmModelRealm.class)
//                        .equalTo("alarmId" , tempAlarmId)
//                        .findFirst();
//                alarmModelRealm.setState("yes");
//                realm.executeTransaction(realm1 -> {
//                    realm1.insertOrUpdate(alarmModelRealm);
//                });
//            }
            view.notificationAndAlarmON(time, layoutPosition, alarmModels, imageViewTime, buttonDone);
        }

    }

    @Override
    public void taskDone(String alarmId) {

    }
}
