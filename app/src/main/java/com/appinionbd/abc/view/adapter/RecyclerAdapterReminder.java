package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.reminderInterface.IReminder;

import java.util.List;

public class RecyclerAdapterReminder extends RecyclerView.Adapter<RecyclerAdapterReminder.ReminderViewHolder>  {
    private List<String> reminderList;
    private IReminder iReminder;

    public RecyclerAdapterReminder(List<String> reminderList, IReminder iReminder) {
        this.reminderList = reminderList;
        this.iReminder = iReminder;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reminder_add_or_delete , parent , false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        holder.textViewReminderTime.setText(reminderList.get(position).toString());
        holder.imageViewReminderDelete.setOnClickListener(v -> deleteReminder(position));
    }

    private void deleteReminder(int position) {
        iReminder.deleteReminder(position);
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewReminderTime;
        private ImageView imageViewReminderDelete;
        public ReminderViewHolder(View itemView) {
            super(itemView);

            textViewReminderTime = itemView.findViewById(R.id.textView_reminder_time);
            imageViewReminderDelete = itemView.findViewById(R.id.imageView_reminder_delete);

//            buttonReminderDelete.setOnClickListener(v -> iReminder.deleteReminder(getLayoutPosition()));
        }
    }
}
