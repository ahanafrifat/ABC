package com.appinionbd.abc.view.personalInformation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.IHome;
import com.appinionbd.abc.interfaces.presenterInterface.IPersonalInformation;
import com.appinionbd.abc.presenter.PersonalInformationPresenter;
import com.appinionbd.abc.view.choosePatientOrMonitor.ChoosePatientOrMonitorActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class PersonalInformationActivity extends AppCompatActivity implements IPersonalInformation.View {

    private CardView cardViewDateOfBirth;
    private TextView textViewDateOfBirth;
    private Switch switchGender;
    private EditText editTextFeet;
    private EditText editTextInches;
    private EditText editTextKg;

    private Button buttonNext;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private Calendar calendar = Calendar.getInstance();

    private IPersonalInformation.Presenter personalInformationpresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        Realm.init(this);
        personalInformationpresenter = new PersonalInformationPresenter(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cardViewDateOfBirth = findViewById(R.id.cardView_date_of_birth);
        textViewDateOfBirth = findViewById(R.id.textView_date_of_birth);
        editTextFeet = findViewById(R.id.editText_feet);
        editTextInches = findViewById(R.id.editText_inches);
        editTextKg = findViewById(R.id.editText_kg);
        switchGender = findViewById(R.id.switch_gender);


        if(switchGender.isChecked() == true){
            switchGender.getThumbDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary) , PorterDuff.Mode.MULTIPLY);
            switchGender.getTrackDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.MULTIPLY);
        }
        else{
            switchGender.getThumbDrawable().setColorFilter(Color.RED , PorterDuff.Mode.MULTIPLY);
            switchGender.getTrackDrawable().setColorFilter(Color.GREEN , PorterDuff.Mode.MULTIPLY);
        }


        switchGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    switchGender.getThumbDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary) , PorterDuff.Mode.MULTIPLY);
                    switchGender.getTrackDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.MULTIPLY);
                }
                else{
                    switchGender.getThumbDrawable().setColorFilter(Color.RED , PorterDuff.Mode.MULTIPLY);
                    switchGender.getTrackDrawable().setColorFilter(Color.GREEN , PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR , year);
                calendar.set(Calendar.MONTH ,month);
                calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);
                update();
            }
        };

        cardViewDateOfBirth.setOnClickListener(v -> openCalendar());



        buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfAllInfoHasGiven();
            }
        });
    }

    private void checkIfAllInfoHasGiven() {
        if( !editTextFeet.getText().toString().trim().isEmpty()
                && !editTextInches.getText().toString().trim().isEmpty()
                && !editTextKg.getText().toString().trim().isEmpty()
                && !textViewDateOfBirth.getText().toString().equals("Date"))
            personalInformationpresenter.uploadAndSavePersonalInformation(
                      textViewDateOfBirth.getText().toString()
                    , editTextFeet.getText().toString()
                    , editTextInches.getText().toString()
                    , editTextKg.getText().toString()
                    , switchGender.isChecked());
        else
            openDialogBox();
    }

    private void openDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Empty Field !");
        builder.setMessage("Please Fill All The Field !");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

    private void openCalendar() {
        new DatePickerDialog(this
                , onDateSetListener
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void update() {
        String dateMonthYear = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateMonthYear , Locale.ENGLISH);
        textViewDateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void successfullyUploaded() {
        Toasty.success(this , "Information Updated !" , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this , ChoosePatientOrMonitorActivity.class);
        startActivity(intent);
    }

    @Override
    public void noNewInfoToUpload(String message) {
        Toasty.warning(this, message , Toast.LENGTH_LONG ,true).show();
    }

    @Override
    public void unAuthorizeAccess(String message) {
        Toasty.error(this , message , Toast.LENGTH_LONG  , true).show();
    }

    @Override
    public void networkProblem(String message) {
        Toasty.error(this , message , Toast.LENGTH_LONG  , true).show();
    }
}
