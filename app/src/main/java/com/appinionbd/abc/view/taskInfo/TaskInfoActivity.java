package com.appinionbd.abc.view.taskInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.ITaskInfo;
import com.appinionbd.abc.model.dataModel.ReminderList;
import com.appinionbd.abc.presenter.TaskInfoPresenter;
import com.appinionbd.abc.view.adapter.RecyclerAdapterPatientHistoryReminder;

import io.realm.RealmList;

public class TaskInfoActivity extends AppCompatActivity implements ITaskInfo.View {

    private String taskId;

    ImageView imageViewTaskType;
    TextView textViewTaskTitle;
    TextView textViewTaskTotalReminder;
    Button buttonTaskDelete;
    RecyclerView recyclerViewTaskReminder;

    ITaskInfo.Presenter taskInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
    }

    @Override
    protected void onStart() {
        super.onStart();

        taskInfoPresenter = new TaskInfoPresenter(this);

        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");

        imageViewTaskType = findViewById(R.id.imageView_task_type);
        textViewTaskTitle = findViewById(R.id.textView_task_title);
        textViewTaskTotalReminder = findViewById(R.id.textView_task_total_reminder);
        buttonTaskDelete = findViewById(R.id.button_task_delete);

        recyclerViewTaskReminder = findViewById(R.id.recyclerView_task_reminder);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTaskReminder.setLayoutManager(layoutManager);
        recyclerViewTaskReminder.setHasFixedSize(true);

        taskInfoPresenter.reminderList(taskId);

    }

    @Override
    public void showReminderList(RealmList<ReminderList> reminderList, String taskName, String taskCategory) {

        textViewTaskTitle.setText(taskName);
        textViewTaskTotalReminder.setText("Total reminder : " + reminderList.size());

        RecyclerAdapterPatientHistoryReminder recyclerAdapterPatientHistoryReminder = new RecyclerAdapterPatientHistoryReminder();

        recyclerViewTaskReminder.setAdapter(recyclerAdapterPatientHistoryReminder);
        recyclerAdapterPatientHistoryReminder.notifyDataSetChanged();
    }
}


















