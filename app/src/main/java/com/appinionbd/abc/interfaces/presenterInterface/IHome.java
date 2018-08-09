package com.appinionbd.abc.interfaces.presenterInterface;

import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataModel.TaskCategory;

import java.util.List;

public interface IHome {

    interface View{

        void taskList(List<TaskCategory> taskCategories);

        void notificationAndAlarmOff(String id, ImageView imageViewTime, Button buttonDone);

        void notificationAndAlarmON(String id, ImageView imageViewTime, Button buttonDone, String tempTime);

    }

    interface Presenter{

        void sendDate(String time);

        void checkReminder(String id, ImageView imageViewTime, Button buttonDone);

        void taskDone(String id, ImageView imageViewTime, Button buttonDone);
    }
}
