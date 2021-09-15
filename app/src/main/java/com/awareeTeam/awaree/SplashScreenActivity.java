package com.awareeTeam.awaree;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String INTRODUCTION_VIEWED = "introductionViewed";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isFirstTime()) { // TODO variabila care se salveaza pe system care determina daca e logat sau nu, si daca e prima oara cand intra in app
                    intent = new Intent(SplashScreenActivity.this, IntroductionActivity.class);
                }else if(isLoginValid()){
                    intent = new Intent(SplashScreenActivity.this, Login.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1);

    }

    private boolean isFirstTime(){
        boolean firstTime;
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean(INTRODUCTION_VIEWED, false);
        Log.d(TAG, "isFirstTime: " + firstTime);
        return false;
    }
    private boolean isLoginValid(){
        boolean loginValid = false;


        return loginValid;
    }
}
