package com.appinionbd.abc.view.choosePatientOrMonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.IChoosePatientOrMonitor;
import com.appinionbd.abc.presenter.ChoosePatientOrMonitorPresenter;
import com.appinionbd.abc.view.home.HomeActivity;
import com.appinionbd.abc.view.patientActivity.PatientActivity;

import io.realm.Realm;

public class ChoosePatientOrMonitorActivity extends AppCompatActivity implements IChoosePatientOrMonitor.View {

    CardView cardViewPatient;
    CardView cardViewMonitor;
    private IChoosePatientOrMonitor.Presenter choosePatientOrMonitorPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_patient_or_monitor);
        Realm.init(this);
        choosePatientOrMonitorPresenter = new ChoosePatientOrMonitorPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cardViewPatient = findViewById(R.id.cardView_patient);
        cardViewMonitor = findViewById(R.id.cardView_monitor);

        cardViewPatient.setOnClickListener(v -> gotoHomeActivity());
        cardViewMonitor.setOnClickListener(v -> gotoMonitorActivity());
    }

    private void gotoHomeActivity(){
        Intent intent = new Intent(this , HomeActivity.class);
        intent.putExtra("patientOrMonitor", "patient");
        startActivity(intent);
    }

    private void gotoMonitorActivity() {
        Intent intent = new Intent(this , HomeActivity.class);
        intent.putExtra("patientOrMonitor", "monitor");
        startActivity(intent);
    }
}
