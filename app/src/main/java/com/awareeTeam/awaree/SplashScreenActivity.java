package com.awareeTeam.awaree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    private boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstTime = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (firstTime) { // TODO variabila care se salveaza pe system care determina daca e logat sau nu, si daca e prima oara cand intra in app
                    intent = new Intent(SplashScreenActivity.this, IntroductionActivity.class);
                }else if(firstTime){
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
