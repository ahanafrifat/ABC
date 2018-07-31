package com.appinionbd.abc.interfaces.networkInterface;

public interface IUpdatePersonalInformation {
    void successful();
    void noNewInfo(String message);
    void unAuthorized(String message);
    void connectionProblem(String message);
}
