package com.appinionbd.abc.interfaces.deleteInterface;

public interface IDeleteInterface {
    void successful(String message);
    void error(String message);
    void connectionError(String message);
}
