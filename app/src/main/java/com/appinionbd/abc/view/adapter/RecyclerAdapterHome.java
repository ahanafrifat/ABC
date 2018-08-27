package com.appinionbd.abc.view.adapter;

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

        int state;

        state = checkAlarm(taskCategories.get(position).getId() , taskCategories.get(position).getTaskId());

        if (state == STATE_YES) {
            holder.imageViewTime.setImageResource(R.drawable.ic_notifications_active_24dp);
            holder.buttonDone.setVisibility(View.VISIBLE);
//            updateStatus(STATE_YES ,
//                    taskCategories.get(position).getId(),
//                    taskCategories.get(position).getTaskId() ,
//                    taskCategories.get(position).getStartDate(),
//                    taskCategories.get(position).getReminderTime());
        }
        else if(state == STATE_NO) {
            holder.imageViewTime.setImageResource(R.drawable.ic_notifications_black_24dp);
            holder.buttonDone.setVisibility(View.GONE);
//            updateStatus(STATE_NO,
//                    taskCategories.get(position).getId(),
//                    taskCategories.get(position).getTaskId() ,
//                    taskCategories.get(position).getStartDate(),
//                    taskCategories.get(position).getReminderTime());
        }
        else if(state == STATE_DONE) {
            holder.imageViewTime.setImageResource(R.drawable.ic_done_24dp);
            holder.buttonDone.setVisibility(View.GONE);
//            updateStatus(STATE_DONE,
//                    taskCategories.get(position).getId(),
//                    taskCategories.get(position).getTaskId() ,
//                    taskCategories.get(position).getStartDate(),
//                    taskCategories.get(position).getReminderTime());
        }


        holder.imageViewTime.setOnClickListener(v -> {

            AppUtil.log("RecyclerAdapterHome" , "Status : " + taskCategories.get(position).getReminderStatus());

            if(taskCategories.get(position).getReminderStatus().equals(STRING_STATE_NO)) {
                v.setVisibility(View.GONE);
                holder.imageViewTime.setImageResource(R.drawable.ic_notifications_active_24dp);
                holder.buttonDone.setVisibility(View.VISIBLE);
                updateStatus(STATE_YES,
                        taskCategories.get(position).getId(),
                        taskCategories.get(position).getTaskId(),
                        taskCategories.get(position).getStartDate(),
                        taskCategories.get(position).getReminderTime()
                );
            }
            else if(taskCategories.get(position).getReminderStatus().equals(STRING_STATE_YES)) {
                v.setVisibility(View.VISIBLE);
                holder.imageViewTime.setImageResource(R.drawable.ic_notifications_black_24dp);
                holder.buttonDone.setVisibility(View.GONE);
                updateStatus(STATE_NO,
                        taskCategories.get(position).getId(),
                        taskCategories.get(position).getTaskId(),
                        taskCategories.get(position).getStartDate(),
                        taskCategories.get(position).getReminderTime()
                );
            }
        }
        );

        holder.buttonDone.setOnClickListener(v -> {
            updateStatus(STATE_DONE,
                    taskCategories.get(position).getId(),
                    taskCategories.get(position).getTaskId() ,
                    taskCategories.get(position).getStartDate(),
                    taskCategories.get(position).getReminderTime()
            );
            v.setVisibility(View.GONE);
            holder.imageViewTime.setImageResource(R.drawable.ic_done_24dp);
            holder.imageViewTime.setVisibility(View.VISIBLE);
        } );

    }

    private void updateStatus(int state, String id, String taskId, String startDate, String reminderTime) {

        String token;
        //time = 2018-08-08 12:20:00 ; this is the format
//        String time = startDate + " " + reminderTime;

        try(Realm realm = Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }

        UpdateReminderModel updateReminderModel = new UpdateReminderModel();
        updateReminderModel.setReminderId(Integer.valueOf(id));
        updateReminderModel.setStatus(String.valueOf(state));

        ApiUpdateReminder.getApiUpdateReminder().setApiUpdateReminder(token, updateReminderModel, new IUpdateReminder() {
            @Override
            public void successfullyUpdated(String message) {
                if(state == STATE_DONE){
                    updateDatabase(id , taskId , state);
                    iTaskSelection.reminderDone(id , taskId);
                }
                else if(state == STATE_YES){
                    updateDatabase(id , taskId, state);
                    iTaskSelection.reminderSetOn(id , taskId , reminderTime);
                }
                else if(state == STATE_NO){
                    updateDatabase(id , taskId, state);
                    iTaskSelection.reminderSetOff(id , taskId);
                }
            }

            @Override
            public void errorInUpdateReminder(String message) {
                iTaskSelection.errorInUpdatingReminder(message);
            }

            @Override
            public void connectionErrorInUpdateReminder(String message) {
                iTaskSelection.connectionErrorInUpdatingReminder(message);
            }
        });
    }

    private void updateDatabase(String id, String taskId, int state) {

        String alarmId = id + "" + taskId;

        AlarmModel alarmModelSave = new AlarmModel();

        try(Realm realm = Realm.getDefaultInstance()){

            AlarmModel alarmModel = realm.where(AlarmModel.class)
                    .equalTo("alarmId" , alarmId)
                    .findFirst();

            alarmModelSave.setAlarmId(alarmModel.getAlarmId());
            alarmModelSave.setState(alarmModel.getState());
            alarmModelSave.setTime(alarmModel.getTime());

            realm.executeTransaction(realm1 -> {
                realm1.insertOrUpdate(alarmModelSave);
            });
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
