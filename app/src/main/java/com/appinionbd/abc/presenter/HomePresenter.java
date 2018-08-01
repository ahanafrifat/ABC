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
    public void saveReminder(List<AlarmModel> alarmModels) {

        for(AlarmModel model : alarmModels){
            try(Realm realmInstance = Realm.getDefaultInstance()) {
                realmInstance.executeTransaction(realm -> {
                    realm.insertOrUpdate(model);
                });
            }
        }
    }

    @Override
    public void checkReminder(String id, ImageView imageViewTime, Button buttonDone) {

        boolean checkAlarm = false;

        try(Realm realm = Realm.getDefaultInstance()){

            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , id)
                    .findFirst();

            if(alarmModel.getState().equals("yes")) {
                checkAlarm = true;
            }
            else if(alarmModel.getState().equals("no")) {
                checkAlarm = false;
            }
        }
        if(checkAlarm) {
            AlarmModel tempAlarmModel = new AlarmModel();
            try(Realm realm = Realm.getDefaultInstance()) {
                AlarmModel alarmModel = realm.where(AlarmModel.class)
                        .equalTo("alarmId" , id)
                        .findFirst();

                tempAlarmModel.setAlarmId(id);
                tempAlarmModel.setState("no");
                tempAlarmModel.setTime(alarmModel.getTime());

                realm.executeTransaction(realm1 -> {
                    realm1.insertOrUpdate(alarmModel);
                });
            }
            view.notificationAndAlarmON(id ,imageViewTime , buttonDone);
        }
        else if(!checkAlarm) {
            AlarmModel tempAlarmModel = new AlarmModel();
            try(Realm realm = Realm.getDefaultInstance()) {
                AlarmModel alarmModel = realm.where(AlarmModel.class)
                        .equalTo("alarmId" , id)
                        .findFirst();
                tempAlarmModel.setAlarmId(id);
                tempAlarmModel.setState("yes");
                tempAlarmModel.setTime(alarmModel.getTime());
                realm.executeTransaction(realm1 -> {
                    realm1.insertOrUpdate(tempAlarmModel);
                });
            }
            view.notificationAndAlarmOff(id ,imageViewTime , buttonDone);
        }

    }

    @Override
    public void taskDone(String id, ImageView imageViewTime, Button buttonDone) {
        try(Realm realm = Realm.getDefaultInstance()) {
            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , id)
                    .findFirst();
            alarmModel.setState("no");
            realm.executeTransaction(realm1 -> {
                realm1.insertOrUpdate(alarmModel);
            });
        }
        view.notificationAndAlarmOff(id ,imageViewTime , buttonDone);
    }
}
