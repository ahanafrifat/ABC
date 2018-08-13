package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.Monitor;

public interface IMonitorHome {
    interface View{

        void showTrackList(Monitor monitor);
    }

    interface Presenter{
        void getMonitorList();
    }
}
