package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IMonitorHome;
import com.appinionbd.abc.interfaces.trackListInterface.ITrackList;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.Monitor;
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
}
