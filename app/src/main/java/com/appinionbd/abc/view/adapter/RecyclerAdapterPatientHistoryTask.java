package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;

public class RecyclerAdapterPatientHistoryTask extends RecyclerView.Adapter<RecyclerAdapterPatientHistoryTask.MyPatientHistoryTaskViewHolder>{


    @NonNull
    @Override
    public MyPatientHistoryTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_history_task ,parent ,false);
        return new MyPatientHistoryTaskViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPatientHistoryTaskViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyPatientHistoryTaskViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPatientHistoryTaskName;
        ImageView imageViewPatientHistoryTaskType;

        public MyPatientHistoryTaskViewHolder(View itemView) {
            super(itemView);

            textViewPatientHistoryTaskName = itemView.findViewById(R.id.textView_patient_history_task_name);
            imageViewPatientHistoryTaskType = itemView.findViewById(R.id.imageView_patient_history_task_type);
        }
    }
}
