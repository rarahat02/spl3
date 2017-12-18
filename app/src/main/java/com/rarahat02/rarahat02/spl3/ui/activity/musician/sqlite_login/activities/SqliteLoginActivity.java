package com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rarahat02.rarahat02.spl3.ui.activity.MainActivity;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.MusicianHome;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.rarahat02.instamaterial.R;

import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.sql.DatabaseHelper;

public class SqliteLoginActivity extends AppCompatActivity
{
    private final AppCompatActivity activity = SqliteLoginActivity.this;

    private static final String TAG = "SqliteLoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_artist_id) EditText artistIdText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @BindView(R.id.btn_back_from_login) Button _backToCustomerButtonFromLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_activity_login);
        //getSupportActionBar();

 /*       initViews();
        initListeners();*/
        initObjects();

        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SqliteRegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        _backToCustomerButtonFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void login()
    {
        Log.d(TAG, "Login");

        if (!validate())
        {
            onLoginFailed();
            return;
        }

        //_loginButton.setEnabled(false);

/*        final ProgressDialog progressDialog = new ProgressDialog(SqliteLoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();*/

        String artistId = artistIdText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.


        verifyFromSQLite(artistId, password);








/*        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/
    }



    /**
     * This method is to initialize objects to be used
     */
    private void initObjects()
    {
        databaseHelper = new DatabaseHelper(activity);

    }
    @Override
    public void onBackPressed() {
        // Disable going back to the LoginMainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    private void verifyFromSQLite(String artistId, String password)
    {

        if (databaseHelper.checkUser(artistId, password))
        {

            Intent intent = new Intent(activity, MusicianHome.class);

            User user = databaseHelper.getAnUser(artistId);
            intent.putExtra("artistId", artistId);
            intent.putExtra("password", password);
            intent.putExtra("artistName", user.getName());
            //emptyInputEditText();
            startActivity(intent);


        }
        else
        {
            Log.v("sqlite", artistId + password);

            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            // Snack Bar to show success message that record is wrong
            //Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


/*        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, SqliteUsersListActivity.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }*/



    public boolean validate()
    {
        boolean valid = true;

        String artistId = artistIdText.getText().toString();
        String password = _passwordText.getText().toString();

        if (artistId.isEmpty() || artistId.length() != 8 || !artistId.substring(7, 8).matches(".*\\d+.*"))
        {
            artistIdText.setError("enter a valid artist ID");
            valid = false;
        }

        else
        {
            artistIdText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
