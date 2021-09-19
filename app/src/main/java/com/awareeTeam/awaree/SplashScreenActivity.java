package com.awareeTeam.awaree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!sharedPreferences.getBoolean("seenIntroduction", false)) {
                    intent = new Intent(SplashScreenActivity.this, IntroductionActivity.class);
                }else if(!sharedPreferences.getBoolean("loginStatus", false)) {
                    intent = new Intent(SplashScreenActivity.this, Login.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this).toBundle());
                finish();
            }
        }, 1);

    }

    private boolean checkLogin() {
        boolean checkLogin = true;
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        return checkLogin;
    }

}
