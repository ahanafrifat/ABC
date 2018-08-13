package com.appinionbd.abc.view.createTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.presenterInterface.ICreateTask;
import com.appinionbd.abc.interfaces.reminderInterface.IReminder;
import com.appinionbd.abc.model.dataModel.Task;
import com.appinionbd.abc.presenter.CreateTaskPresenter;
import com.appinionbd.abc.view.adapter.RecyclerAdapterReminder;
import com.appinionbd.abc.view.home.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class CreateTaskActivity extends AppCompatActivity implements ICreateTask.View {

    private Calendar calendar = Calendar.getInstance();
    private CardView cardViewCreateTaskAddReminder;
    private CardView cardViewCreateTaskDate;
    private CardView cardViewSubmit;
    private TextView textViewCreateTaskDate;
    private EditText editTextName;
    private EditText editTextNoOfDays;

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private RecyclerView recyclerViewCreatedTask;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinnerCreateTask;
    private DatePickerDialog.OnDateSetListener onDateSetListenerCreateTask;

    private ICreateTask.Presenter createTaskPresenter;

    private List<String> stringList;
    private List<String> reminderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Realm.init(this);
        createTaskPresenter = new CreateTaskPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        cardViewCreateTaskAddReminder = findViewById(R.id.cardView_create_task_add_reminder);
        cardViewCreateTaskDate = findViewById(R.id.cardView_create_task_date);
        cardViewSubmit = findViewById(R.id.cardView_submit);

        editTextName = findViewById(R.id.editText_name);
        editTextNoOfDays = findViewById(R.id.editText_no_of_days);
        textViewCreateTaskDate = findViewById(R.id.textView_create_task_date);

        spinnerCreateTask = findViewById(R.id.spinner_create_task);
        recyclerViewCreatedTask = findViewById(R.id.recyclerView_created_task);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCreatedTask.setLayoutManager(layoutManager);
        recyclerViewCreatedTask.setHasFixedSize(true);

        reminderList = new ArrayList<>();

        stringList = new ArrayList<>();
        stringList.add("Pill Reminder");
        stringList.add("Feeding");
        stringList.add("Exercise");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this , android.R.layout.simple_spinner_item ,stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCreateTask.setAdapter(adapter);
        spinnerCreateTask.getSelectedItemPosition();
        spinnerCreateTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY , hourOfDay);
                calendar.set(Calendar.MINUTE , minute);
                getTime();
            }
        };
        cardViewCreateTaskDate.setOnClickListener(v -> updateCreateTaskDate());
        cardViewCreateTaskAddReminder.setOnClickListener(v -> updateReminderTime());

        updateTaskStartDate();

        onDateSetListenerCreateTask= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR , year);
                calendar.set(Calendar.MONTH , month);
                calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);
                updateTaskStartDate();
            }
        };
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkTaskName();
            }
        });

        AppUtil.log("CreateTaskActivity" , "Size : " + reminderList.size());
        cardViewSubmit.setOnClickListener(v -> createTaskPresenter.submitTask(
                editTextName.getText().toString()
                , editTextNoOfDays.getText().toString()
                , convertDate(textViewCreateTaskDate.getText().toString().trim())
                , reminderList
                , spinnerCreateTask.getSelectedItemPosition() + 1));

    }

    private void checkTaskName() {
        if(editTextName.getText().toString().isEmpty())
            cardViewSubmit.setVisibility(View.GONE);
        else
            cardViewSubmit.setVisibility(View.VISIBLE);
    }

    private void updateTaskStartDate() {
        String dateMonthYear = "dd ,MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateMonthYear , Locale.ENGLISH);
        textViewCreateTaskDate.setText(simpleDateFormat.format(calendar.getTime()));
        Toasty.info(this , simpleDateFormat.format(calendar.getTime()) ).show();
    }

    private void updateCreateTaskDate() {
        new DatePickerDialog(this
                , onDateSetListenerCreateTask
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateReminderTime() {
        new TimePickerDialog(this
                , onTimeSetListener
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                ,false).show();
    }

    private void getTime(){
        String dateMonthYear = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateMonthYear , Locale.ENGLISH);
        String time = simpleDateFormat.format(calendar.getTime());
        reminderList.add(time);
        Toasty.success(this ,time ).show();
        updateReminderList(reminderList);
    }

    private void updateReminderList(List<String> reminderList) {
        RecyclerAdapterReminder recyclerAdapterReminder = new RecyclerAdapterReminder(reminderList, new IReminder() {
            @Override
            public void deleteReminder(int layoutPosition) {
                deleteFromReminder(layoutPosition);
            }
        });
        recyclerViewCreatedTask.setAdapter(recyclerAdapterReminder);
        recyclerAdapterReminder.notifyDataSetChanged();
    }

    private void deleteFromReminder(int layoutPosition) {
        reminderList.remove(layoutPosition);
        updateReminderList(reminderList);
    }

    private String  convertDate(String dateYear) {
        String convertDate = "";
        SimpleDateFormat simpleDateFormatSort = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd ,MMMM yyyy" , Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(dateYear);
            convertDate = simpleDateFormatSort.format(date);
            return convertDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return convertDate;
        }
    }

    @Override
    public void successfullySubmitted(String message) {
        Toasty.success(this , message).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("patientOrMonitor", "patient");
        startActivity(intent);
    }

    @Override
    public void unAuthorized(String message) {
        Toasty.warning(this , message).show();
    }

    @Override
    public void error(String message) {
        Toasty.error(this , message).show();
    }

    @Override
    public void connectionProblem(String message) {
        Toasty.error(this , message).show();
    }
}
