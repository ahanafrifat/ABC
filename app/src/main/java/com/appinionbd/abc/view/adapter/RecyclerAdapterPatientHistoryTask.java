package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.AdapterPatientHistoryTaskInterface.IAdapterPatientHistoryTask;
import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;

import java.util.List;

public class RecyclerAdapterPatientHistoryTask extends RecyclerView.Adapter<RecyclerAdapterPatientHistoryTask.MyPatientHistoryTaskViewHolder>{

    private List<PatientWiseTaskList> patientWiseTaskLists;
    private IAdapterPatientHistoryTask IAdapterPatientHistoryTask;

    public RecyclerAdapterPatientHistoryTask(List<PatientWiseTaskList> patientWiseTaskLists, com.appinionbd.abc.interfaces.AdapterPatientHistoryTaskInterface.IAdapterPatientHistoryTask IAdapterPatientHistoryTask) {
        this.patientWiseTaskLists = patientWiseTaskLists;
        this.IAdapterPatientHistoryTask = IAdapterPatientHistoryTask;
    }

    @NonNull
    @Override
    public MyPatientHistoryTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_history_task ,parent ,false);
        return new MyPatientHistoryTaskViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPatientHistoryTaskViewHolder holder, int position) {
        holder.textViewPatientHistoryTaskName.setText(patientWiseTaskLists.get(position).getTaskId());

        switch (patientWiseTaskLists.get(position).getStatus()) {
            case "1":
                holder.imageViewPatientHistoryTaskType.setImageResource(R.drawable.ic_drug);
                break;
            case "2":
                holder.imageViewPatientHistoryTaskType.setImageResource(R.drawable.ic_feeding);
                break;
            case "3":
                holder.imageViewPatientHistoryTaskType.setImageResource(R.drawable.ic_directions_run_24dp);
                break;
        }

//        holder.imageViewPatientHistoryTaskType
    }

    @Override
    public int getItemCount() {
        return patientWiseTaskLists.size();
    }

    class MyPatientHistoryTaskViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPatientHistoryTaskName;
        ImageView imageViewPatientHistoryTaskType;
        LinearLayout linearLayoutPatientHistoryTask;

        public MyPatientHistoryTaskViewHolder(View itemView) {
            super(itemView);

            textViewPatientHistoryTaskName = itemView.findViewById(R.id.textView_patient_history_task_name);
            imageViewPatientHistoryTaskType = itemView.findViewById(R.id.imageView_patient_history_task_type);
            linearLayoutPatientHistoryTask = itemView.findViewById(R.id.linearLayout_patient_history_task);

            linearLayoutPatientHistoryTask.setOnClickListener(v -> IAdapterPatientHistoryTask.clickLinearLayout(patientWiseTaskLists.get(getLayoutPosition())));
        }
    }
}
