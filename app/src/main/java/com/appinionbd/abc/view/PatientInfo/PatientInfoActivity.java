package com.appinionbd.abc.view.PatientInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.IPatientInfo;
import com.appinionbd.abc.presenter.PatientInfoPresenter;

public class PatientInfoActivity extends AppCompatActivity implements IPatientInfo.View {

    RecyclerView recyclerViewPersonalInfoList;
    private String patientId;

    IPatientInfo.Presenter patientInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Intent intent = getIntent();
        patientId = intent.getStringExtra("patient_id");
        patientInfoPresenter = new PatientInfoPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerViewPersonalInfoList = findViewById(R.id.recyclerView_patient_task);

        patientInfoPresenter.getInfo(patientId);
    }
}
