package com.appinionbd.abc.view.signIn;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.presenterInterface.ILogin;
import com.appinionbd.abc.presenter.ChoosePatientOrMonitorPresenter;
import com.appinionbd.abc.presenter.LoginPresenter;
import com.appinionbd.abc.view.choosePatientOrMonitor.ChoosePatientOrMonitorActivity;
import com.appinionbd.abc.view.personalInformation.PersonalInformationActivity;
import com.appinionbd.abc.view.signUp.SignUpActivity;

import es.dmoral.toasty.Toasty;
import io.github.pierry.progress.Progress;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class SignInActivity extends AppCompatActivity implements ILogin.View {

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private TextView textViewSignUp;

    private Progress progress;

    private Button buttonLogIn;

    private ILogin.Presenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        progress = new Progress(this);
        progress = new Progress(this);
        progress.light("User Information Loading . . .");
        loginPresenter.checkUserExist();
//        startSignIn();

    }

    @Override
    public void emptyUser() {
        progress.dismiss();
        startSignIn();
    }

    @Override
    public void userExist() {
        gotoChoosePatientOrMonitor();
    }

    private void startSignIn() {


        textInputEditTextEmail = findViewById(R.id.textInputEditText_email);
        textInputEditTextPassword = findViewById(R.id.textInputEditText_password);
        textViewSignUp = findViewById(R.id.textView_sign_up);
        buttonLogIn = findViewById(R.id.button_log_in);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignInActivity();
            }
        });
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoPersonalInformation();
                gotoLoginVerification();
                AppUtil.log("SignInActivity" , "Email : " + textInputEditTextEmail.getText().toString()
                        + "Password : " + textInputEditTextPassword.getText().toString());
//                loginPresenter.loginVerification(textInputEditTextEmail.getText().toString() , textInputEditTextPassword.getText().toString());
            }
        });

    }

    private void gotoLoginVerification() {
        progress = new Progress(this);
        progress.light("Loading . . .");
        loginPresenter.loginVerification(textInputEditTextEmail.getText().toString() , textInputEditTextPassword.getText().toString());

    }

    private void gotoPersonalInformation() {
        Intent intent = new Intent(this, PersonalInformationActivity.class);
        startActivity(intent);
    }

    private void gotoSignInActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void gotoChoosePatientOrMonitor(){
        Intent intent = new Intent(this , ChoosePatientOrMonitorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void emptyUserName() {
        progress.dismiss();
        textInputEditTextEmail.setError("Empty Username ! ");
    }

    @Override
    public void emptyPassword() {
        progress.dismiss();
        textInputEditTextPassword.setError("Empty Password ! ");
    }

    @Override
    public void successful() {
        progress.dismiss();
        Toasty.warning(this , "Welcome" , Toast.LENGTH_LONG , true).show();
        gotoChoosePatientOrMonitor();
    }

    @Override
    public void loginError(String message) {
        progress.dismiss();
        Toasty.warning(this , message , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void wrongUsernameOrPassword(String message) {
        progress.dismiss();
        Toasty.error(this , message , Toast.LENGTH_LONG , true).show();
    }
}
