package com.appinionbd.abc.view.PatientInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.AdapterPatientHistoryTaskInterface.IAdapterPatientHistoryTask;
import com.appinionbd.abc.interfaces.presenterInterface.IPatientInfo;
import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;
import com.appinionbd.abc.presenter.PatientInfoPresenter;
import com.appinionbd.abc.view.adapter.RecyclerAdapterPatientHistoryTask;
import com.appinionbd.abc.view.taskInfo.TaskInfoActivity;

import java.util.List;

public class PatientInfoActivity extends AppCompatActivity implements IPatientInfo.View {

    RecyclerView recyclerViewPersonalInfoList;
    private String patientId;
    private String patientName;
    private String patientEmail;
    private String patientDob;
    private String patientHeight;
    private String patientWeight;
    private String patientGender;

    private TextView textViewPatientInfoName;
    private TextView textViewPatientInfoEmail;
    private TextView textViewPatientInfoDob;
    private TextView textViewPatientInfoHeight;
    private TextView textViewPatientInfoWeight;
    private TextView textViewPatientInfoGender;


    IPatientInfo.Presenter patientInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Intent intent = getIntent();
        patientId = intent.getStringExtra("patient_id");

        patientName = intent.getStringExtra("patient_name");
        patientEmail = intent.getStringExtra("patient_email");
        patientDob = intent.getStringExtra("patient_dob");
        patientHeight = intent.getStringExtra("patient_height");
        patientWeight = intent.getStringExtra("patient_weight");
        patientGender = intent.getStringExtra("patient_gender");

        patientInfoPresenter = new PatientInfoPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        textViewPatientInfoName = findViewById(R.id.textView_patient_info_name);
        textViewPatientInfoEmail = findViewById(R.id.textView_patient_info_email);
        textViewPatientInfoDob = findViewById(R.id.textView_patient_info_dob);
        textViewPatientInfoHeight = findViewById(R.id.textView_patient_info_height);
        textViewPatientInfoWeight = findViewById(R.id.textView_patient_info_weight);
        textViewPatientInfoGender = findViewById(R.id.textView_patient_info_gender);

        textViewPatientInfoName.setText(patientName);
        textViewPatientInfoEmail.setText(patientEmail);
        textViewPatientInfoDob.setText(patientDob);
        textViewPatientInfoHeight.setText(patientHeight);
        textViewPatientInfoWeight.setText(patientWeight + " kg");

        if(patientGender.equals("0")){
            textViewPatientInfoGender.setText("Male");
        }
        else if(patientGender.equals("1")){
            textViewPatientInfoGender.setText("Female");
        }


        recyclerViewPersonalInfoList = findViewById(R.id.recyclerView_patient_task);

        patientInfoPresenter.getInfo(patientId);
    }

    @Override
    public void showTaskList(List<PatientWiseTaskList> patientWiseTaskLists) {

        AppUtil.log("PatientInfoActivity" , "patientWiseTaskLists size : " + patientWiseTaskLists.size());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPersonalInfoList.setLayoutManager(layoutManager);
        recyclerViewPersonalInfoList.setHasFixedSize(true);
        RecyclerAdapterPatientHistoryTask recyclerAdapterPatientHistoryTask = new RecyclerAdapterPatientHistoryTask(patientWiseTaskLists, new IAdapterPatientHistoryTask() {
            @Override
            public void clickLinearLayout(PatientWiseTaskList patientWiseTaskList) {
                gotoTaskInfo(patientWiseTaskList.getTaskId());
            }
        });

        recyclerViewPersonalInfoList.setAdapter(recyclerAdapterPatientHistoryTask);
        recyclerAdapterPatientHistoryTask.notifyDataSetChanged();

    }

    private void gotoTaskInfo(String taskId) {
        Intent intent = new Intent(this , TaskInfoActivity.class);
        intent.putExtra("task_id" , taskId);
        startActivity(intent);
    }
}
