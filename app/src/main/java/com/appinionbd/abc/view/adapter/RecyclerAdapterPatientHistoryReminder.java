package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;

public class RecyclerAdapterPatientHistoryReminder extends RecyclerView.Adapter<RecyclerAdapterPatientHistoryReminder.MyPatientHistoryReminderViewHolder>{

    @NonNull
    @Override
    public MyPatientHistoryReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_history_reminder , parent , false);
        return new MyPatientHistoryReminderViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPatientHistoryReminderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyPatientHistoryReminderViewHolder extends RecyclerView.ViewHolder{


        TextView textView_patient_history_reminder_name;
        TextView textViewPatientHistoryReminderTime;
        TextView textViewPatientHistoryReminderStatus;
        ImageView imageViewPatientHistoryReminderType;

        public MyPatientHistoryReminderViewHolder(View itemView) {
            super(itemView);

            textView_patient_history_reminder_name = itemView.findViewById(R.id.textView_patient_history_reminder_name);
            textViewPatientHistoryReminderTime = itemView.findViewById(R.id.textView_patient_history_reminder_time);
            textViewPatientHistoryReminderStatus = itemView.findViewById(R.id.textView_patient_history_reminder_status);
            imageViewPatientHistoryReminderType = itemView.findViewById(R.id.imageView_patient_history_reminder_type);
        }
    }
}
