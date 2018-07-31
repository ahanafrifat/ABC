package com.appinionbd.abc.interfaces.networkInterface;

public interface IAuthentication {
    void successful();
    void authFailed();
    void wrongUserNameOrPassword();
    void connectionError();
}
