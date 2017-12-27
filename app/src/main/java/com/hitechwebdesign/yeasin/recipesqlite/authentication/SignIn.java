package com.hitechwebdesign.yeasin.recipesqlite.authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.database.UsersDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.model.UsersModel;
import com.hitechwebdesign.yeasin.recipesqlite.session.MyPreferencesHelperSinglaton;
import com.hitechwebdesign.yeasin.recipesqlite.view.MainActivity;


public class SignIn extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "SignIn";
    private EditText emailText;
    private EditText passwordText;
    private TextView link_signup;
    private Button signIn;
    private ProgressDialog progressDialog;
    private UsersDbSource source;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sine_in);
        source = new UsersDbSource(getApplicationContext());
        context = this;
        initView();
        initListeners();
    }

    private void initView() {
        //String email = MyPreferencesHelperSinglaton.getInstance(getApplicationContext()).getString(MyPreferencesHelperSinglaton.Key.KEY_USER_EMAIL);
        emailText = (EditText) findViewById(R.id.et_email);
        //emailText.setText(email);
        passwordText = (EditText) findViewById(R.id.et_password);
        link_signup = (TextView) findViewById(R.id.link_signup);
        signIn = (Button) findViewById(R.id.btn_login);
    }

    private void initListeners() {
        signIn.setOnClickListener(this);
        link_signup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.btn_login:
                String email = emailText.getText().toString().trim();
                String pass = passwordText.getText().toString().trim();

               login(email,pass);
                break;

        }

    }

    public void login(String email,String pass) {




        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        signIn.setEnabled(false);

        final boolean b = source.checkUser(email, pass);
        if(b){
           // final UsersModel userByEmail = source.getUserByEmail(email);


            MyPreferencesHelperSinglaton.getInstance(context.getApplicationContext()).put(MyPreferencesHelperSinglaton.Key.KEY_USER_EMAIL, email);
        Intent intent = new Intent(SignIn.this,MainActivity.class);
        }else{
            Intent intent = new Intent(SignIn.this,SignIn.class);
            Toast.makeText(context, "Plz agen username and pass", Toast.LENGTH_SHORT).show();
        }

        //communicator.logincheck(user.getUemail(), user.getPassword(),context);


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                // On complete call either onLoginSuccess or onLoginFailed
                onLoginSuccess();
                // onLoginFailed();

            }
        }, 3000);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        signIn.setEnabled(true);
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(SignIn.this,MainActivity.class);
        startActivity(intent);
        signIn.setEnabled(true);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
