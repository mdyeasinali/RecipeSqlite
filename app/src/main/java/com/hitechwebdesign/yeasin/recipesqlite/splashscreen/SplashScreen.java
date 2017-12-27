package com.hitechwebdesign.yeasin.recipesqlite.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.authentication.SignIn;
import com.hitechwebdesign.yeasin.recipesqlite.session.MyPreferencesHelperSinglaton;
import com.hitechwebdesign.yeasin.recipesqlite.view.MainActivity;

/**
 * Created by BITM Student md yeasin ail  on 12/27/2017.
 */

public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private String id;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        email = MyPreferencesHelperSinglaton.getInstance(getApplicationContext()).getString(MyPreferencesHelperSinglaton.Key.KEY_USER_EMAIL);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                    if (email != null ) {
                        Intent mm = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(mm);
                    } else {
                        Intent mm = new Intent(SplashScreen.this, SignIn.class);
                        startActivity(mm);
                    }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
