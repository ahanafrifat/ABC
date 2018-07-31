package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.recyclerAdapterMonitorHomeInterface.IPatientSelection;
import com.appinionbd.abc.model.dataModel.Monitor;
import com.appinionbd.abc.model.dataModel.MonitorsPatientList;

import java.util.List;

public class RecyclerAdapterMonitor extends RecyclerView.Adapter<RecyclerAdapterMonitor.MyMonitorViewHolder>{

    List<MonitorsPatientList> monitorsPatientLists;
    private IPatientSelection iPatientSelection;

    public RecyclerAdapterMonitor(List<MonitorsPatientList> monitorsPatientLists, IPatientSelection iPatientSelection) {
        this.monitorsPatientLists = monitorsPatientLists;
        this.iPatientSelection = iPatientSelection;
    }

    @NonNull
    @Override
    public MyMonitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_monitor , parent ,false);
        return new MyMonitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMonitorViewHolder holder, int position) {
        holder.textMonitorPaitentName.setText(monitorsPatientLists.get(position).getUserName());
        holder.textMonitorPaitentEmail.setText(monitorsPatientLists.get(position).getUserEmail());
        holder.textMonitorPaitentDateOfBirth.setText(monitorsPatientLists.get(position).getDob());
//        holder.textMonitorPaitentDateOfBirth.setText(monitorsPatientLists.get(position).getDob());
    }

    @Override
    public int getItemCount() {
        return monitorsPatientLists.size();
    }

    class MyMonitorViewHolder extends RecyclerView.ViewHolder{

        TextView textMonitorPaitentName;
        TextView textMonitorPaitentEmail;
        TextView textMonitorPaitentDateOfBirth;
        LinearLayout linearLayoutMonitorPatient;

        public MyMonitorViewHolder(View itemView) {
            super(itemView);
            textMonitorPaitentName = itemView.findViewById(R.id.text_monitor_paitent_name);
            textMonitorPaitentEmail = itemView.findViewById(R.id.text_monitor_paitent_email);
            textMonitorPaitentDateOfBirth = itemView.findViewById(R.id.text_monitor_paitent_date_of_birth);
            linearLayoutMonitorPatient = itemView.findViewById(R.id.linearLayout_monitor_patient);

            linearLayoutMonitorPatient.setOnClickListener(v -> {
                iPatientSelection.selectedPatient();
            });
        }
    }
}
