package com.appinionbd.abc.interfaces.deleteTaskInterface;

public interface IDeleteTaskInterface {
    void successful(String message);
    void error(String message);
    void connectionError(String message);
}
