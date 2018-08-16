package com.appinionbd.abc.view.adapter;

import android.app.AlarmManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface.ITaskSelection;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataModel.TaskCategory;

import java.util.List;

import io.realm.Realm;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.MyViewHolder> {

    List<TaskCategory> taskCategories;
    private ITaskSelection iTaskSelection;
    private final int STATE_NO = 0 ;
    private final int STATE_YES = 1 ;
    private final int STATE_DONE = 2 ;

    public RecyclerAdapterHome(List<TaskCategory> taskCategories, ITaskSelection iTaskSelection) {
        this.taskCategories = taskCategories;
        this.iTaskSelection = iTaskSelection;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home , parent , false);
        return new MyViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.textViewName.setText(taskCategories.get(position).getTaskName());
        holder.textViewTime.setText(taskCategories.get(position).getReminderTime());

        int state;

        state = checkAlarm(taskCategories.get(position).getId());
        if (state == STATE_YES)
            holder.imageViewTime.setImageResource(R.drawable.ic_notifications_active_24dp);
        else if(state == STATE_NO)
            holder.imageViewTime.setImageResource(R.drawable.ic_notifications_black_24dp);
        else if(state == STATE_DONE)
            holder.imageViewTime.setImageResource(R.drawable.ic_done_24dp);


        holder.imageViewTime.setOnClickListener(v -> iTaskSelection.setNotificationAndAlarm(
                taskCategories.get(position).getReminderTime()
                , taskCategories.get(position).getId()
                ,taskCategories.get(position).getTaskId()
                , holder.imageViewTime
                , holder.buttonDone));

    }

    @Override
    public int getItemCount() {
        return taskCategories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewAdd;
        TextView textViewName;
        TextView textViewTime;
        ImageView imageViewTime;
        Button buttonDone;
        LinearLayout linearLayoutPatient;
//        boolean state;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewAdd = itemView.findViewById(R.id.imageView_add);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewTime = itemView.findViewById(R.id.textView_time);
            imageViewTime = itemView.findViewById(R.id.imageView_alarm);
            buttonDone = itemView.findViewById(R.id.button_done);
            linearLayoutPatient = itemView.findViewById(R.id.linearLayout_patient);

            buttonDone.setOnClickListener(v -> iTaskSelection.reminderDone(taskCategories.get(getLayoutPosition()).getId() , imageViewTime , buttonDone));

            linearLayoutPatient.setOnClickListener(v -> iTaskSelection.gotoTask(taskCategories.get(getLayoutPosition())));
        }
    }

    private int checkAlarm(String id) {
        int state = 0 ;
        try(Realm realm = Realm.getDefaultInstance()){
            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , id)
                    .findFirst();
            if(alarmModel.getState().equals("0"))
                state = STATE_NO;
            else if( alarmModel.getState().equals("1") )
                state = STATE_YES;
            else if( alarmModel.getState().equals("3") )
                state = STATE_DONE;
        }
        return state;
    }
}
