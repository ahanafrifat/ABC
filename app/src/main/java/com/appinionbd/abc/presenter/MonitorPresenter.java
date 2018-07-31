package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IMonitorHome;

public class MonitorPresenter implements IMonitorHome.Presenter {
    private IMonitorHome.View view;

    public MonitorPresenter() {

    }

    public MonitorPresenter(IMonitorHome.View view) {
        this.view = view;
    }

    @Override
    public void getMonitorList() {

    }
}
