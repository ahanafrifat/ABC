package com.appinionbd.abc.view.PatientInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.appinionbd.abc.R;

public class PatientInfoActivity extends AppCompatActivity {

    RecyclerView recyclerViewPersonalInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        recyclerViewPersonalInfoList = findViewById(R.id.recyclerView_personal_info_list);
    }
}
