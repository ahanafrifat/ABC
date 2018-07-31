package com.appinionbd.abc.view.home.fragment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.presenterInterface.IHome;
import com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface.ITaskSelection;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataModel.Patient;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.presenter.HomePresenter;
import com.appinionbd.abc.view.adapter.RecyclerAdapterHome;
import com.appinionbd.abc.view.alarm.MyAlarm;
import com.appinionbd.abc.view.choosePatientOrMonitor.ChoosePatientOrMonitorActivity;
import com.appinionbd.abc.view.createTask.CreateTaskActivity;
import com.appinionbd.abc.view.home.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IHome.View {

    private SwipeRefreshLayout swipeLayoutHome;
    private RecyclerView recyclerViewHome;
    private CardView cardViewHomeCalender;
    private CardView cardViewCreateTask;
    private TextView textViewDateHome;

    private DatePickerDialog.OnDateSetListener onDateSetListenerHome;
    private Calendar calendar = Calendar.getInstance();

    private List<Patient> patients;
    private String time;

    private List<AlarmModel> alarmModels;

    private final String STATE = "extra";
    private final String STATE_YES = "yes";
    private final String STATE_NO = "no";

    IHome.Presenter homePresenter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homePresenter = new HomePresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showHomeList();
    }

    private void showHomeList() {

        recyclerViewHome = getActivity().findViewById(R.id.recyclerView_home);
        cardViewCreateTask = getActivity().findViewById(R.id.cardView_create_task);
        cardViewHomeCalender = getActivity().findViewById(R.id.cardView_home_calender);
        textViewDateHome = getActivity().findViewById(R.id.textView_date_home);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setHasFixedSize(true);

        updateHomeDate();

        cardViewCreateTask.setOnClickListener(v -> gotoCreateTask());

        cardViewHomeCalender.setOnClickListener(v -> openCalenderHome());

        onDateSetListenerHome = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateHomeDate();
            }
        };
    }


    private void openCalenderHome() {
        new DatePickerDialog(getActivity()
                , onDateSetListenerHome
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateHomeDate() {
        String dateMonthYear = "dd ,MMMM yyyy";
        //2018-07-20 api date
        String dateForSort = "yyyy-MM-dd";
        String apiDate;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateMonthYear, Locale.ENGLISH);
        SimpleDateFormat simpleDateFormatSort = new SimpleDateFormat(dateForSort, Locale.ENGLISH);

        time = simpleDateFormat.format(calendar.getTime());
        apiDate = simpleDateFormatSort.format(calendar.getTime());
        textViewDateHome.setText(time);
//        homePresenter.sendDate(dateforSort);
        homePresenter.sendDate(apiDate);
    }

    private void gotoCreateTask() {
        Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
        startActivity(intent);
    }

    private void gotoChooseRole() {
        Intent intent = new Intent(getActivity(), ChoosePatientOrMonitorActivity.class);
        startActivity(intent);
    }

    @Override
    public void taskList(List<TaskCategory> taskCategories) {
        AppUtil.log("", "size : " + taskCategories.size());

        try {
            if (taskCategories.size() > 0) {

                recyclerViewHome.setVisibility(View.VISIBLE);

                alarmModels = new ArrayList<>();

                for (TaskCategory taskCategory : taskCategories) {

                    alarmModels.add(setDefaultAlarmManager(taskCategory.getId(), taskCategory.getReminderTime()));
                }

                RecyclerAdapterHome recyclerAdapterHome = new RecyclerAdapterHome(taskCategories, new ITaskSelection() {
                    @Override
                    public void gotoTask(TaskCategory taskCategory) {

                    }

                    @Override
                    public void setNotificationAndAlarm(String time, int layoutPosition, ImageView imageViewTime, Button buttonDone) {
                        homePresenter.checkReminder(time, layoutPosition, alarmModels, imageViewTime, buttonDone);
                    }

                    @Override
                    public void reminderDone(int layoutPosition, ImageView imageViewTime, Button buttonDone) {

                    }
                });
                recyclerViewHome.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
                recyclerViewHome.setAdapter(recyclerAdapterHome);
                recyclerAdapterHome.notifyDataSetChanged();
            } else {
                recyclerViewHome.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            AppUtil.log("HomeFragment", e.getMessage());
        }
    }

    private AlarmModel setDefaultAlarmManager(String id, String longTime) {

        String convertDate = "";
        long tempLongTime = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormatConvert = new SimpleDateFormat("HHmm", Locale.ENGLISH);

        try {
            Date date = simpleDateFormat.parse(longTime);
            convertDate = simpleDateFormatConvert.format(date);
            tempLongTime = Long.parseLong(convertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(getActivity(), MyAlarm.class);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        intent.putExtra(STATE, STATE_NO);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC, tempLongTime, pendingIntent);

        AppUtil.log("HomeFragment", "Alarm is set : " + time + " Long : " + longTime);

        AlarmModel alarmModel = new AlarmModel(
                id
                , alarmManager
                , intent
                , pendingIntent
                , STATE_NO);

        homePresenter.saveReminder(alarmModel);

        return alarmModel;
    }

    @Override
    public void notificationAndAlarmON(String time, int layoutPosition, List<AlarmModel> alarmModels, ImageView imageViewTime, Button buttonDone) {

//        Intent intent = alarmModels.get(layoutPosition).getIntent();
//        intent.putExtra(STATE, STATE_YES);
//        getActivity().sendBroadcast(intent);
//        AlarmManager alarmManager = alarmModels.get(layoutPosition).getAlarmManager();
//
//
//        Log.d("alarm","here");
//        alarmManager.set(AlarmManager.RTC_WAKEUP , (System.currentTimeMillis()+60000/10) , alarmModels.get(layoutPosition).getPendingIntent());


        Intent intent = new Intent(getActivity(), MyAlarm.class);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        intent.putExtra(STATE, STATE_NO);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC, (new Date().getTime()), pendingIntent);


        imageViewTime.setImageResource(R.drawable.ic_notifications_active_24dp);
        buttonDone.setVisibility(View.VISIBLE);
        buttonDone.setOnClickListener(v -> {
            homePresenter.taskDone(alarmModels.get(layoutPosition).getAlarmId());
        });

        AppUtil.log("HomeFragment", "Alarm is On : " + (new Date().getTime()));


    }

    @Override
    public void notificationAndAlarmOff(String time, int layoutPosition, List<AlarmModel> alarmModels, ImageView imageViewTime, Button buttonDone) {

        Intent intent = alarmModels.get(layoutPosition).getIntent();
        intent.putExtra(STATE, STATE_NO);
        getActivity().sendBroadcast(intent);
        AlarmManager alarmManager = alarmModels.get(layoutPosition).getAlarmManager();
        alarmManager.cancel(alarmModels.get(layoutPosition).getPendingIntent());

        imageViewTime.setImageResource(R.drawable.ic_notifications_black_24dp);
        buttonDone.setVisibility(View.GONE);

        AppUtil.log("HomeFragment", "Alarm is Cancel : " + time);
    }


}














