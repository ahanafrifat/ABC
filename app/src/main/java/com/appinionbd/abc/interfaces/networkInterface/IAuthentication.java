package com.appinionbd.abc.interfaces.networkInterface;

public interface IAuthentication {
    void successful();
    void wrongUserNameOrPassword(String message);
    void authError(String message);
    void authConnectionError(String message);
    void loginAuthError(String message);
    void loginAuthConnectionError(String message);
    void userInfoError(String message);
    void userInfoConnectionError(String message);
}
