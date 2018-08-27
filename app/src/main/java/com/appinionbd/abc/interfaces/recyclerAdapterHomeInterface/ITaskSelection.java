package com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface;

import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.model.dataModel.TaskCategory;

public interface ITaskSelection {
    void gotoTask(TaskCategory taskCategory);
    void setNotificationAndAlarm(String reminderTime, String id, String taskId);
    void reminderDone(String id, String taskId);
    void reminderSetOn(String id, String taskId, String time);
    void reminderSetOff(String id, String taskId);
    void errorInUpdatingReminder(String message);
    void connectionErrorInUpdatingReminder(String message);
}
