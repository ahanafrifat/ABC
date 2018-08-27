package com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface;

import com.appinionbd.abc.model.dataModel.TaskCategory;

public interface ITaskSelection {
    void gotoTask(TaskCategory taskCategory);
    void setNotificationAndAlarm(String reminderTime, String id, String taskId, String reminderStatus);
    void reminderDone(String id, String taskId);
    void reminderSetOn(TaskCategory taskCategory);
    void reminderSetOff(TaskCategory taskCategory);
    void errorInUpdatingReminder(String message);
    void connectionErrorInUpdatingReminder(String message);
}
