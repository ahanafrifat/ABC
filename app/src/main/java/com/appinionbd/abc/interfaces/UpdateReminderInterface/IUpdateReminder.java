package com.appinionbd.abc.interfaces.UpdateReminderInterface;

public interface IUpdateReminder {
    void successfullyUpdated(String message);
    void errorInUpdateReminder(String message);
    void connectionErrorInUpdateReminder(String message);
}
