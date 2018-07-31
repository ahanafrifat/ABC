package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IChoosePatientOrMonitor;

public class ChoosePatientOrMonitorPresenter implements IChoosePatientOrMonitor.Presenter {
    IChoosePatientOrMonitor.View view;

    public ChoosePatientOrMonitorPresenter() {
    }

    public ChoosePatientOrMonitorPresenter(IChoosePatientOrMonitor.View view) {
        this.view = view;
    }
}
