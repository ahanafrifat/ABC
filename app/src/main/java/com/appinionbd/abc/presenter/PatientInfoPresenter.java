package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IPatientInfo;
import com.appinionbd.abc.interfaces.taskInfoInterface.ITaskAndReminderInterface;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.taskHistory.ApiTaskHistory;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class PatientInfoPresenter implements IPatientInfo.Presenter {

    private IPatientInfo.View view;

    public PatientInfoPresenter() {
    }

    public PatientInfoPresenter(IPatientInfo.View view) {
        this.view = view;
    }

    @Override
    public void getInfo(String patientId) {

//        String name;
//        String email;
//        String dob;
//        String height;
//        String weight;
//        String gender;
        String token;

        try(Realm realm = Realm.getDefaultInstance()){
//            User user = realm.where(User.class).findFirst();
//            name = user.getUserName();
//            dob = user.getDob();
//            height = user.getHeight();
//            weight = user.getWeight();
//            gender = user.getGender();

            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
//            RealmResults<PatientWiseTaskList> patientWiseTaskLists = re;
        }

        ApiTaskHistory.getApiTaskHistory().setApiTaskHistory(token, patientId, new ITaskAndReminderInterface() {
            @Override
            public void patientTaskList(List<PatientWiseTaskList> patientWiseTaskLists) {
                view.showTaskList(patientWiseTaskLists);
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












