package com.appinionbd.abc.view.taskInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.ITaskInfo;
import com.appinionbd.abc.model.dataModel.ReminderList;
import com.appinionbd.abc.presenter.TaskInfoPresenter;
import com.appinionbd.abc.view.adapter.RecyclerAdapterPatientHistoryReminder;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

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
        Realm.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        taskInfoPresenter = new TaskInfoPresenter(this);

        Intent intent = getIntent();
        taskId = intent.getStringExtra("task_id");

        imageViewTaskType = findViewById(R.id.imageView_task_type);
        textViewTaskTitle = findViewById(R.id.textView_task_title);
        textViewTaskTotalReminder = findViewById(R.id.textView_task_total_reminder);
        buttonTaskDelete = findViewById(R.id.button_task_delete);

        buttonTaskDelete.setOnClickListener(v -> taskInfoPresenter.deleteTask(taskId));

        recyclerViewTaskReminder = findViewById(R.id.recyclerView_task_reminder);

        taskInfoPresenter.reminderList(taskId);

    }

    @Override
    public void showReminderList(List<ReminderList> reminderLists, String taskTitle, String category) {

        textViewTaskTitle.setText(taskTitle);
//        textViewTaskTotalReminder.setText("Total reminder : " + reminderList.size());

        if(reminderLists.size() > 0){

            textViewTaskTotalReminder.setText("Total reminder : " + reminderLists.size());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewTaskReminder.setLayoutManager(layoutManager);
            recyclerViewTaskReminder.setHasFixedSize(true);

            RecyclerAdapterPatientHistoryReminder recyclerAdapterPatientHistoryReminder = new RecyclerAdapterPatientHistoryReminder(reminderLists);

            recyclerViewTaskReminder.setAdapter(recyclerAdapterPatientHistoryReminder);
            recyclerAdapterPatientHistoryReminder.notifyDataSetChanged();
        }
        else{
            textViewTaskTotalReminder.setText("Total reminder : 0");
        }

    }

    @Override
    public void confirmDeleteTask(String message) {
        Toasty.success(this , message , Toast.LENGTH_LONG , true).show();
        onBackPressed();
    }

    @Override
    public void deleteError(String message) {
        Toasty.error(this , message , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void connectionError(String message) {
        Toasty.error(this , message , Toast.LENGTH_LONG , true).show();
    }
}


















