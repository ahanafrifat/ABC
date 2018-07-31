package com.appinionbd.abc.interfaces.createInterface;

public interface ICreate {
    void successful(String message);
    void noNewInfo(String message);
    void unAuthorized(String message);
    void error(String message);
    void connectionProblem(String message);
}
