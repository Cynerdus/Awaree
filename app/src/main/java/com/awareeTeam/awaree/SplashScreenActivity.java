package com.awareeTeam.awaree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!sharedPreferences.getBoolean("seenIntroduction", false)) {
                    intent = new Intent(SplashScreenActivity.this, IntroductionActivity.class);
                }else if(!sharedPreferences.getBoolean("loginStatus", false)){
                    intent = new Intent(SplashScreenActivity.this, Login.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
