package com.appinionbd.abc.view.signUp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.ISignUp;
import com.appinionbd.abc.presenter.SignUpPresenter;
import com.appinionbd.abc.view.signIn.SignInActivity;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import io.github.pierry.progress.Progress;

public class SignUpActivity extends AppCompatActivity implements ISignUp.View {

    private TextInputEditText textInputEditTextSignUpName;
    private TextInputEditText textInputEditTextSignUpEmail;
    private TextInputEditText textInputEditText_sign_up_password;
    private TextInputEditText textInputEditTextSignUpRetypePassword;

    private Button buttonSignUp;

    private Progress progress;

    private ISignUp.Presenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpPresenter = new SignUpPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        progress = new Progress(this);

        textInputEditTextSignUpName = findViewById(R.id.textInputEditText_sign_up_name);
        textInputEditTextSignUpEmail = findViewById(R.id.textInputEditText_sign_up_email);
        textInputEditText_sign_up_password = findViewById(R.id.textInputEditText_sign_up_password);
        textInputEditTextSignUpRetypePassword = findViewById(R.id.textInputEditText_sign_up_retype_password);

        buttonSignUp = findViewById(R.id.button_sign_up);

        buttonSignUp.setOnClickListener(v -> {
            if(isNetworkConnected()) {
                checkForEmptyField();
            }
            else {
                showAlertDialogNetworkError();
            }
        });

    }

    private void checkForEmptyField() {
        progress = new Progress(this);
        progress.light("Please Wait !");

        if (textInputEditTextSignUpName.getText().toString().isEmpty()) {
            progress.dismiss();
            textInputEditTextSignUpName.setError("Please Enter a name !");
        }
        else if (textInputEditTextSignUpEmail.getText().toString().isEmpty()) {
            progress.dismiss();
            textInputEditTextSignUpEmail.setError("Please Enter a E-mail !");
        }
        else if (textInputEditText_sign_up_password.getText().toString().isEmpty()) {
            progress.dismiss();
            textInputEditText_sign_up_password.setError("Please Enter a password !");
        }
        else if (textInputEditTextSignUpRetypePassword.getText().toString().isEmpty()) {
            progress.dismiss();
            textInputEditTextSignUpRetypePassword.setError("Please retype password !");
        }
        else{
            signUpPresenter.signUp(
                    textInputEditTextSignUpName.getText().toString()
                    , textInputEditTextSignUpEmail.getText().toString()
                    , textInputEditText_sign_up_password.getText().toString()
                    , textInputEditTextSignUpRetypePassword.getText().toString());
        }

    }

    private void gotoSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void showAlertDialogNetworkError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Spannable required = new SpannableString("No Internet !");

        required.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),0,required.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setTitle(required);
        builder.setMessage("Please check your connection and try again !");
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        builder.create();
        builder.show();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(this).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void successful(String message) {
        progress.dismiss();
        Toasty.success(this , message , Toast.LENGTH_LONG , true);
        Intent intent = new Intent(this , SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void errorPasswordDidNotMatch() {
        progress.dismiss();
        Toasty.warning(this , "Password Did Not Match !" , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void networkProblem(String message) {
        progress.dismiss();
        Toasty.error(this , message , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void alreadyAccountCreated(String message) {
        progress.dismiss();
        Toasty.info(this , message , Toast.LENGTH_LONG , true).show();
    }

    @Override
    public void signUpError(String message) {
        progress.dismiss();
        Toasty.error(this , message , Toast.LENGTH_LONG , true).show();
    }
}
