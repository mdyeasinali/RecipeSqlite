package com.hitechwebdesign.yeasin.recipesqlite.authentication;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.database.RecipeDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.database.UsersDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.model.UsersModel;
import com.hitechwebdesign.yeasin.recipesqlite.view.NewRecipes;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUp";
    private UsersDbSource source;
    private EditText et_name;
    private EditText et_email;
    private EditText et_mobile;
    private EditText et_password;
    private EditText et_reEnterPassword;
    private Button b_signup;
    private TextView tv_login;

    private String username;
    private String email;
    private String mobile;
    private String password;

    private View coordinatorLayoutView;


    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private String lattitude, longitude;
    private String add = "";
    private String cuntry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        source = new UsersDbSource(getApplicationContext());
        initView();
        initListeners();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.input_username);
        et_email = (EditText) findViewById(R.id.input_email);
        et_password = (EditText) findViewById(R.id.input_password);
        et_reEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);
        et_mobile = (EditText) findViewById(R.id.input_mobile);
        b_signup = (Button) findViewById(R.id.btn_signup);
        tv_login = (TextView) findViewById(R.id.link_login);
        //coordinatorLayoutView = findViewById(R.id.snackbarPosition);
    }

    private void initListeners() {
        b_signup.setOnClickListener(this);
        tv_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.link_login:
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;

            case R.id.btn_signup:
               signup();
                break;
        }

    }

    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }

        b_signup.setEnabled(false);

        username = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        mobile = et_mobile.getText().toString().trim();
        password = et_password.getText().toString().trim();
        Toast.makeText(this, "U"+username+"E"+email+"M"+mobile+"P"+password, Toast.LENGTH_SHORT).show();
        UsersModel users = new UsersModel(username,email,password,mobile,"1");
        boolean userMsg = source.insertUser(users);
        if (userMsg){
            Toast.makeText(SignUp.this, "Successfully Sign Up", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(SignUp.this,"Some Error", Toast.LENGTH_LONG).show();
        }

//        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                onSignupSuccess();
            }
        }, 3000);
    }

    public void onSignupFailed() {
       // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        b_signup.setEnabled(true);
    }

    public void onSignupSuccess() {
        //Toast.makeText(getBaseContext(), "Login Succesful", Toast.LENGTH_LONG).show();
       Intent intent = new Intent(SignUp.this,SignIn.class);
       startActivity(intent);
        b_signup.setEnabled(true);
        setResult(RESULT_OK, null);

    }


    public boolean validate() {
        boolean valid = true;
        username = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        mobile = et_mobile.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String reEnterPassword = et_reEnterPassword.getText().toString().trim();


        if (username.isEmpty()) {
            et_name.setError("Enter a valid Username");
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid email address");
            valid = false;
        } else {
            et_mobile.setError(null);
    }

        if (mobile.isEmpty()) {
            et_mobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            et_mobile.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_password.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            et_reEnterPassword.setError("Password Do not match");
            valid = false;
        } else {
            et_reEnterPassword.setError(null);
        }

        return valid;
    }




}

