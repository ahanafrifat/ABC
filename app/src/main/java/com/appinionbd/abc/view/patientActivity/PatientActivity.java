package com.appinionbd.abc.view.patientActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.appinionbd.abc.R;
import com.appinionbd.abc.model.dataModel.Patient;
import com.appinionbd.abc.view.adapter.RecyclerAdapterPatient;
import com.appinionbd.abc.view.patientCode.PatientCodeActivity;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPatient;
    private List<Patient> patients;
    private CardView cardViewAddPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerViewPatient = findViewById(R.id.recyclerView_patient);
        cardViewAddPatient = findViewById(R.id.cardView_add_patient);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPatient.setLayoutManager(layoutManager);
        recyclerViewPatient.setHasFixedSize(true);

        patients = new ArrayList<>();
        patients.add(new Patient(0 , "Sudipta" , "25" , ""));
        patients.add(new Patient(1 , "Ahasan" , "24" , ""));
        patients.add(new Patient(2 , "Rakib" , "24" , ""));
        patients.add(new Patient(3 , "Naim" , "24" , ""));

        RecyclerAdapterPatient recyclerAdapterPatient = new RecyclerAdapterPatient(patients);
        recyclerViewPatient.addItemDecoration(new DividerItemDecoration(this , LinearLayout.VERTICAL));
        recyclerViewPatient.setAdapter(recyclerAdapterPatient);
        recyclerAdapterPatient.notifyDataSetChanged();

        cardViewAddPatient.setOnClickListener(v -> gotoPatientCodeActivity());

    }

    private void gotoPatientCodeActivity() {
        Intent intent = new Intent(this , PatientCodeActivity.class);
        startActivity(intent);
    }
}
