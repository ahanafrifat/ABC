package com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface;

import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.model.dataModel.TaskCategory;

public interface ITaskSelection {
    void gotoTask(TaskCategory taskCategory);
    void setNotificationAndAlarm(String reminderTime, String id, String taskId, ImageView imageViewTime, Button buttonDone);
    void reminderDone(String id, ImageView imageViewTime, Button buttonDone);
}
