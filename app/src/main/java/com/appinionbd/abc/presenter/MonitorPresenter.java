package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.deleteInterface.IDeleteInterface;
import com.appinionbd.abc.interfaces.presenterInterface.IMonitorHome;
import com.appinionbd.abc.interfaces.trackListInterface.ITrackList;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.Monitor;
import com.appinionbd.abc.model.dataModel.MonitorsPatientList;
import com.appinionbd.abc.networking.deletePatientApi.ApiDeletePatient;
import com.appinionbd.abc.networking.trackList.ApiTrackList;

import io.realm.Realm;

public class MonitorPresenter implements IMonitorHome.Presenter {
    private IMonitorHome.View view;

    public MonitorPresenter() {

    }

    public MonitorPresenter(IMonitorHome.View view) {
        this.view = view;
    }

    @Override
    public void getMonitorList() {

        String token;
        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }

        ApiTrackList.getApiTrackList().setApiTrackList(token, new ITrackList() {
            @Override
            public void successful(Monitor monitor) {
                view.showTrackList(monitor);
            }

            @Override
            public void error() {

            }

            @Override
            public void networkFailed() {

            }
        });
    }

    @Override
    public void deletePatientParmanently(MonitorsPatientList monitorsPatientList) {
        String token;
        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }
        ApiDeletePatient.getApiDeletePatient().setApiDeletePatient(token, monitorsPatientList.getMonitorPatientRelId(), new IDeleteInterface() {
            @Override
            public void successful(String message) {
                view.successfullyDeletedPatient(message);
            }

            @Override
            public void error(String message) {
                view.errorInDeletePatient(message);
            }

            @Override
            public void connectionError(String message) {
                view.connectionErrorInDeletePatient(message);
            }
        });
    }
}
