package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.TaskCategory;

import java.util.List;

public interface IHome {

    interface View{

        void taskList(List<TaskCategory> taskCategories);

        void taskListEmpty(String message);
//
//        void notificationAndAlarmOff(String id, ImageView imageViewTime, Button buttonDone);
//
//        void notificationAndAlarmON(String id, ImageView imageViewTime, Button buttonDone, String tempTime);

    }

    interface Presenter{

        void sendDate(String time);

        void checkReminder(String reminderTime, String id, String taskId, String reminderStatus);

//        void taskDone(String id);
    }
}
