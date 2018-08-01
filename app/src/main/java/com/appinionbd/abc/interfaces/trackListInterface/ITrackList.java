package com.appinionbd.abc.interfaces.trackListInterface;

import com.appinionbd.abc.model.dataModel.Monitor;

public interface ITrackList {
    void successful(Monitor monitor);
    void error();
    void networkFailed();
}
