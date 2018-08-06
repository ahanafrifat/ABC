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

public class SignUpActivity extends AppCompatActivity implements ISignUp.View {

    private TextInputEditText textInputEditTextSignUpName;
    private TextInputEditText textInputEditTextSignUpEmail;
    private TextInputEditText textInputEditText_sign_up_password;
    private TextInputEditText textInputEditTextSignUpRetypePassword;

    private Button buttonSignUp;

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

        textInputEditTextSignUpName = findViewById(R.id.textInputEditText_sign_up_name);
        textInputEditTextSignUpEmail = findViewById(R.id.textInputEditText_sign_up_email);
        textInputEditText_sign_up_password = findViewById(R.id.textInputEditText_sign_up_password);
        textInputEditTextSignUpRetypePassword = findViewById(R.id.textInputEditText_sign_up_retype_password);

        buttonSignUp = findViewById(R.id.button_sign_up);

        buttonSignUp.setOnClickListener(v -> {
            if(isNetworkConnected()) {
                checkForEmptyField();
            }
            else
                showAlertDialogNetworkError();
        });

    }

    private void checkForEmptyField() {
        if(textInputEditTextSignUpName.getText().toString().isEmpty())
            textInputEditTextSignUpName.setError("Please Enter a name !");
        else if(textInputEditTextSignUpEmail.getText().toString().isEmpty())
            textInputEditTextSignUpEmail.setError("Please Enter a E-mail !");
        else if(textInputEditText_sign_up_password.getText().toString().isEmpty())
            textInputEditText_sign_up_password.setError("Please Enter a password !");
        else if(textInputEditTextSignUpRetypePassword.getText().toString().isEmpty())
            textInputEditTextSignUpRetypePassword.setError("Please retype password !");
        else
            signUpPresenter.signUp(
                    textInputEditTextSignUpName.getText().toString()
                    , textInputEditTextSignUpEmail.getText().toString()
                    , textInputEditText_sign_up_password.getText().toString()
                    , textInputEditTextSignUpRetypePassword.getText().toString());

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
    public void errorPasswordDidNotMatch() {
        Toasty.error(this , "Password Did Not Match !" , Toast.LENGTH_LONG , true).show();
    }
}
