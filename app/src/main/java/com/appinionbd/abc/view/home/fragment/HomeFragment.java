package com.appinionbd.abc.view.home.fragment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.presenterInterface.IHome;
import com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface.ITaskSelection;
import com.appinionbd.abc.model.dataModel.Patient;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.presenter.HomePresenter;
import com.appinionbd.abc.view.adapter.RecyclerAdapterHome;
import com.appinionbd.abc.view.alarm.AlarmReceiver;
import com.appinionbd.abc.view.choosePatientOrMonitor.ChoosePatientOrMonitorActivity;
import com.appinionbd.abc.view.createTask.CreateTaskActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IHome.View {

    private SwipeRefreshLayout swipeLayoutHome;
    private RecyclerView recyclerViewHome;
    private CardView cardViewHomeCalender;
    private CardView cardViewCreateTask;
    private TextView textViewDateHome;
    private LinearLayout linearLayoutHomeEmptyContent;

    private DatePickerDialog.OnDateSetListener onDateSetListenerHome;
    private Calendar calendar = Calendar.getInstance();

    private List<Patient> patients;
    private String time;

    private final String STATE = "extra";
    private final String STATE_YES = "yes";
    private final String STATE_NO = "no";

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Intent intentAlarm;

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
//        intentAlarm = new Intent(this.getActivity(), MyAlarm.class);
        showHomeList();
    }

    private void showHomeList() {

        intentAlarm = new Intent(this.getActivity(), AlarmReceiver.class);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        recyclerViewHome = getActivity().findViewById(R.id.recyclerView_home);
        linearLayoutHomeEmptyContent = getActivity().findViewById(R.id.linearLayout_home_empty_content);
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateMonthYear, Locale.ENGLISH);
        time = simpleDateFormat.format(calendar.getTime());
        textViewDateHome.setText(time);


        //2018-07-20 api date
        String dateForSendInApi = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormatForSendInApi = new SimpleDateFormat(dateForSendInApi, Locale.ENGLISH);
        String apiDate = simpleDateFormatForSendInApi.format(calendar.getTime());

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
                linearLayoutHomeEmptyContent.setVisibility(View.GONE);

                RecyclerAdapterHome recyclerAdapterHome = new RecyclerAdapterHome(taskCategories, new ITaskSelection() {
                    @Override
                    public void gotoTask(TaskCategory taskCategory) {
                        gotoTaskActivity();
                    }

                    @Override
                    public void setNotificationAndAlarm(String reminderTime, String id, String taskId) {
//                        homePresenter.checkReminder(time, layoutPosition, alarmModels, imageViewTime, buttonDone);
//                        homePresenter.checkReminder(id , taskId);
                    }

                    @Override
                    public void reminderDone(String id, String taskId) {
//                        homePresenter.taskDone( id );
                        notificationAndAlarmOff( id , taskId );
                    }

                    @Override
                    public void reminderSetOn(String id, String taskId, String time) {
                        notificationAndAlarmON(id , taskId , time);
                    }

                    @Override
                    public void reminderSetOff(String id, String taskId) {
                        notificationAndAlarmOff( id , taskId );
                    }

                    @Override
                    public void errorInUpdatingReminder(String message) {

                    }

                    @Override
                    public void connectionErrorInUpdatingReminder(String message) {

                    }
                });
                recyclerViewHome.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
                recyclerViewHome.setAdapter(recyclerAdapterHome);
                recyclerAdapterHome.notifyDataSetChanged();
            } else {
                recyclerViewHome.setVisibility(View.GONE);
                linearLayoutHomeEmptyContent.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            AppUtil.log("HomeFragment", e.getMessage());
        }
    }

    private void gotoTaskActivity() {

    }

    @Override
    public void taskListEmpty(String message) {
        Toasty.warning(getActivity() , message , Toast.LENGTH_LONG , true).show();
        recyclerViewHome.setVisibility(View.GONE);
        linearLayoutHomeEmptyContent.setVisibility(View.VISIBLE);
    }

    public void notificationAndAlarmOff(String id, String taskId) {

        String alarmId = id + "" + taskId;
        intentAlarm.putExtra("extra" , STATE_NO);
        intentAlarm.putExtra("id" , alarmId);
        this.getActivity().sendBroadcast(intentAlarm);
        alarmManager.cancel(pendingIntent);

    }

    public void notificationAndAlarmON(String id, String taskId, String time ) {

        String alarmId = id + "" + taskId;

//        Intent intent = new Intent(getActivity() , AlarmActivity.class);
//        intent.putExtra("ID" , id);
//        intent.putExtra("TIME" , tempTime);
//        startActivity(intent);

        Calendar cal = Calendar.getInstance();
//        String time = "12:07:00 08-08-2018";
//                "yyyy-MM-dd'T'HH:mm:ssZ"
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy", Locale.getDefault());

        try {
            cal.setTime(sdf.parse(time));
            AppUtil.log("Check", "time = " + cal.getTimeInMillis() + " ID : " + id);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intentAlarm.putExtra("extra" , STATE_YES);
        intentAlarm.putExtra("id" , alarmId);
        pendingIntent = PendingIntent.getBroadcast(this.getActivity() , Integer.parseInt(id), intentAlarm , PendingIntent.FLAG_UPDATE_CURRENT);

        long checkTime = cal.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP , checkTime , pendingIntent);

        AppUtil.log("Check", "time in alarm = " + checkTime + " ID : " + id + "Real time : " + time);


    }
}














