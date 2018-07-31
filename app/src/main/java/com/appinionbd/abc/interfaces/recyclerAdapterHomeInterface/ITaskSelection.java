package com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface;

import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.model.dataModel.TaskCategory;

public interface ITaskSelection {
    void gotoTask(TaskCategory taskCategory);
    void setNotificationAndAlarm(String time, int layoutPosition, ImageView imageViewTime, Button buttonDone);
    void reminderDone(int layoutPosition, ImageView imageViewTime, Button buttonDone);
}
