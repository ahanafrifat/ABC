package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.UpdateReminderInterface.IUpdateReminder;
import com.appinionbd.abc.interfaces.presenterInterface.IAlarmDone;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.uploadDataModel.UpdateReminderModel;
import com.appinionbd.abc.networking.updateReminderApi.ApiUpdateReminder;

import io.realm.Realm;

public class AlarmDone implements IAlarmDone.Presenter {
    IAlarmDone.View view;

    public AlarmDone() {
    }

    public AlarmDone(IAlarmDone.View view) {
        this.view = view;
    }

    @Override
    public void alarmDone(String reminderId , String alarmId) {

        updateStatus(2 , reminderId , alarmId );

    }

    private void updateStatus(int state, String id, String alarmId) {

        int STATE_NO = 0 ;
        int STATE_YES = 1 ;
        int STATE_DONE = 2 ;

        String token;
        //time = 2018-08-08 12:20:00 ; this is the format
//        String time = startDate + " " + reminderTime;

        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();

            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , alarmId)
                    .findFirst();

        }

        UpdateReminderModel updateReminderModel = new UpdateReminderModel();
        updateReminderModel.setReminderId(Integer.valueOf(id));
        updateReminderModel.setStatus(String.valueOf(state));

        ApiUpdateReminder.getApiUpdateReminder().setApiUpdateReminder(token, updateReminderModel, new IUpdateReminder() {
            @Override
            public void successfullyUpdated(String message) {
                if(state == STATE_DONE){
                    updateDatabase(alarmId , state);
                    view.successfulAlarmDone( alarmId, message);
                }
                else{
                    view.error("Something Went Wrong !");
                }

            }

            @Override
            public void errorInUpdateReminder(String message) {
                view.errorInAlarmDone(message);
            }

            @Override
            public void connectionErrorInUpdateReminder(String message) {
                view.connectionErrorInAlarmdone(message);
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
