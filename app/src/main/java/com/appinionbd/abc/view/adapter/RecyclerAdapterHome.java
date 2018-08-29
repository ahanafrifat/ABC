package com.appinionbd.abc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.UpdateReminderInterface.IUpdateReminder;
import com.appinionbd.abc.interfaces.recyclerAdapterHomeInterface.ITaskSelection;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.model.uploadDataModel.UpdateReminderModel;
import com.appinionbd.abc.networking.updateReminderApi.ApiUpdateReminder;

import java.util.List;

import io.realm.Realm;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.MyViewHolder> {

    List<TaskCategory> taskCategories;
    private ITaskSelection iTaskSelection;
    private final int STATE_NO = 0 ;
    private final int STATE_YES = 1 ;
    private final int STATE_DONE = 2 ;

    private final String STRING_STATE_NO = "0" ;
    private final String STRING_STATE_YES = "1" ;
    private final String STRING_STATE_DONE = "2" ;

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

        if(taskCategories.get(position).getTaskCategory().equals("Pill Reminder")) {
            holder.imageViewAdd.setImageResource(R.drawable.ic_drug);
        }
        else if(taskCategories.get(position).getTaskCategory().equals("Walking")) {
            holder.imageViewAdd.setImageResource(R.drawable.ic_directions_walk_24dp);
        }
        else if(taskCategories.get(position).getTaskCategory().equals("Exercise")) {
            holder.imageViewAdd.setImageResource(R.drawable.ic_directions_run_24dp);
        }

        holder.textViewName.setText(taskCategories.get(position).getTaskName());
        holder.textViewTime.setText(taskCategories.get(position).getReminderTime());

        if(taskCategories.get(position).getReminderStatus().equals(STRING_STATE_NO)){
            holder.textViewAlarmDone.setVisibility(View.GONE);
            holder.switchAlarm.setVisibility(View.VISIBLE);
            holder.switchAlarm.setChecked(false);
        }
        else if(taskCategories.get(position).getReminderStatus().equals(STRING_STATE_YES)){
            holder.textViewAlarmDone.setVisibility(View.GONE);
            holder.switchAlarm.setVisibility(View.VISIBLE);
            holder.switchAlarm.setChecked(true);
        }
        else if(taskCategories.get(position).getReminderStatus().equals(STRING_STATE_DONE)){
            holder.textViewAlarmDone.setVisibility(View.VISIBLE);
            holder.switchAlarm.setVisibility(View.GONE);
        }

        }

    @Override
    public int getItemCount() {
        return taskCategories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewAdd;
        TextView textViewName;
        TextView textViewTime;
//        ImageView imageViewTime;
//        Button buttonDone;
        Switch switchAlarm;
        TextView textViewAlarmDone;
        LinearLayout linearLayoutPatient;
//        boolean state;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewAdd = itemView.findViewById(R.id.imageView_add);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewTime = itemView.findViewById(R.id.textView_time);
//            imageViewTime = itemView.findViewById(R.id.imageView_alarm);
//            buttonDone = itemView.findViewById(R.id.button_done);
            switchAlarm = itemView.findViewById(R.id.switch_alarm);
            textViewAlarmDone = itemView.findViewById(R.id.textView_alarm_done);
            linearLayoutPatient = itemView.findViewById(R.id.linearLayout_patient);

            switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        iTaskSelection.reminderSetOn( taskCategories.get(getLayoutPosition()));
                    }
                    else if(!isChecked){
                        iTaskSelection.reminderSetOff( taskCategories.get(getLayoutPosition()));
                    }
                }
            });

//            imageViewTime.setOnClickListener(v -> iTaskSelection.setNotificationAndAlarm(
//                    taskCategories.get(getLayoutPosition()).getReminderTime() ,
//                    taskCategories.get(getLayoutPosition()).getId(),
//                    taskCategories.get(getLayoutPosition()).getTaskId(),
//                    taskCategories.get(getLayoutPosition()).getReminderStatus()
//                    ));

//            buttonDone.setOnClickListener(v -> iTaskSelection.reminderDone(taskCategories.get(getLayoutPosition()).getId() , imageViewTime , buttonDone));

            linearLayoutPatient.setOnClickListener(v -> iTaskSelection.gotoTask(taskCategories.get(getLayoutPosition())));
        }
    }

    private int checkAlarm(String id, String taskId) {
        String alarmId = id + "" + taskId;
        int state = 0 ;
        try(Realm realm = Realm.getDefaultInstance()){

            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , alarmId)
                    .findFirst();

            if(alarmModel.getState().equals("0"))
                state = STATE_NO;
            else if( alarmModel.getState().equals("1") )
                state = STATE_YES;
            else if( alarmModel.getState().equals("2") )
                state = STATE_DONE;
        }
        return state;
    }
}
