package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.model.dataModel.ReminderList;

import java.util.List;

public class RecyclerAdapterPatientHistoryReminder extends RecyclerView.Adapter<RecyclerAdapterPatientHistoryReminder.MyPatientHistoryReminderViewHolder>{

    private List<ReminderList> reminderLists;

    public RecyclerAdapterPatientHistoryReminder(List<ReminderList> reminderLists) {
        this.reminderLists = reminderLists;
    }

    @NonNull
    @Override
    public MyPatientHistoryReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_patient_history_reminder , parent , false);
        return new MyPatientHistoryReminderViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPatientHistoryReminderViewHolder holder, int position) {
        holder.textViewPatientHistoryReminderName.setText(reminderLists.get(position).getReminderId());
        holder.textViewPatientHistoryReminderTime.setText(reminderLists.get(position).getReminderTime() + " " + reminderLists.get(position).getReminderDate());

        holder.textViewPatientHistoryReminderStatus.setText(reminderLists.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return reminderLists.size();
    }

    class MyPatientHistoryReminderViewHolder extends RecyclerView.ViewHolder{


        TextView textViewPatientHistoryReminderName;
        TextView textViewPatientHistoryReminderTime;
        TextView textViewPatientHistoryReminderStatus;
        ImageView imageViewPatientHistoryReminderType;

        public MyPatientHistoryReminderViewHolder(View itemView) {
            super(itemView);

            textViewPatientHistoryReminderName = itemView.findViewById(R.id.textView_patient_history_reminder_name);
            textViewPatientHistoryReminderTime = itemView.findViewById(R.id.textView_patient_history_reminder_time);
            textViewPatientHistoryReminderStatus = itemView.findViewById(R.id.textView_patient_history_reminder_status);
            imageViewPatientHistoryReminderType = itemView.findViewById(R.id.imageView_patient_history_reminder_type);
        }
    }
}










