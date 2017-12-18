package com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.rarahat02.rarahat02.spl3.ui.activity.MainActivity;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.helpers.InputValidation;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.rarahat02.instamaterial.R;

import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.sql.DatabaseHelper;

/**
 * Created by lalit on 8/27/2016.
 */
public class SqliteRegisterActivity extends AppCompatActivity
{

    private final AppCompatActivity activity = SqliteRegisterActivity.this;


    private static final String TAG = "SignupActivity";


    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_artist_id_signup) EditText artistIdText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @BindView(R.id.btn_back_from_register) Button _backToCustomerButtonFromRegister;



    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_activity_signup);


        ButterKnife.bind(this);

        //getSupportActionBar();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),SqliteLoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        _backToCustomerButtonFromRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        initObjects();
    }



    public void signup()
    {
        Log.d(TAG, "Signup");

        if (!validate())
        {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SqliteRegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String artistId = artistIdText.getText().toString();
        //String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();


        postDataToSQLite(name, artistId, mobile, password);








        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }



    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String artistId = artistIdText.getText().toString();
        //String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 5) {
            _nameText.setError("at least 5 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (artistId.isEmpty() || artistId.length() != 8 || !artistId.substring(7, 8).matches(".*\\d+.*"))
        {
            artistIdText.setError("Enter Valid Artist ID");
            valid = false;
        } else {
            artistIdText.setError(null);
        }


        if (mobile.isEmpty() || mobile.length()!=11) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }


    private void initObjects()
    {
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }




    private void postDataToSQLite(String name, String artistId, String mobile, String password)
    {
        user.setName(name);
        user.setEmail(artistId);
        user.setPassword(password);

        databaseHelper.addUser(user);

        Intent intent = new Intent(getApplicationContext(),SqliteLoginActivity.class);
        startActivity(intent);
        finish();


/*
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            //Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            //emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            //Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }*/


    }

}
