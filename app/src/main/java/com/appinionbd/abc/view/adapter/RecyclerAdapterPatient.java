package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.model.dataModel.Patient;

import java.util.List;

public class RecyclerAdapterPatient extends RecyclerView.Adapter<RecyclerAdapterPatient.MyViewHolderForPatient>{

    List<Patient> patients;

    public RecyclerAdapterPatient(List<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public MyViewHolderForPatient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_information , parent , false);
        return new MyViewHolderForPatient(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderForPatient holder, int position) {

        AppUtil.log("adapter" , "Size : " + patients.size());

        holder.textViewPatientName.setText(patients.get(position).getName());
        holder.textViewPatientAge.setText(patients.get(position).getAge());
        holder.textViewRelationshipWithPatient.setText(patients.get(position).getRelationship());

    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class MyViewHolderForPatient extends RecyclerView.ViewHolder{

        TextView textViewPatientName;
        TextView textViewPatientAge;
        TextView textViewRelationshipWithPatient;

        public MyViewHolderForPatient(View itemView) {
            super(itemView);

            textViewPatientName = itemView.findViewById(R.id.textView_patient_name);
            textViewPatientAge = itemView.findViewById(R.id.textView_patient_age);
            textViewRelationshipWithPatient = itemView.findViewById(R.id.textView_relationship_with_patient);
        }
    }
}
