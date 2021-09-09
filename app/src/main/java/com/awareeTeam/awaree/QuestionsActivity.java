package com.awareeTeam.awaree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class QuestionsActivity extends AppCompatActivity {
    private String profile;
    private String serie;
    private String group;
    private boolean isVoluntar;

    private ViewFlipper viewFlipper;
    private Button cti;
    private Button is;
    private Button AA;
    private Button AB;
    private Button AC;
    private Button CA;
    private Button CB;
    private Button CC;
    private Button CD;
    private TextView answer_03;
    private Button yes;
    private Button no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        configureQuestion01();
        //configureQuestion02();
        configureQuestion03();
        configureQuestion04();

    }

    public void nextView() {
        viewFlipper = findViewById(R.id.flipper);
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.showNext();
    }



    public void configureQuestion01(){
        cti = findViewById(R.id.cti);
        is = findViewById(R.id.is);
        cti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO conecteaza raspunsul la baza de date
                profile = "CTI";
                setProfile(profile);
                nextView();
            }
        });
        is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO conecteaza raspunsul la baza de date
                profile = "IS";
                setProfile(profile);
                nextView();
            }
        });
    }
    public void serieAnswer(View v){
        switch (v.getId()){
            case R.id.AA:
                serie = "AA";
                break;
            case R.id.AB:
                serie = "AB";
                break;
            case R.id.AC:
                serie = "AB";
                break;
            case R.id.CA:
                serie = "CA";
                break;
            case R.id.CB:
                serie = "CB";
                break;
            case R.id.CC:
                serie = "CC";
                break;
            case R.id.CD:
                serie = "CD";
                break;
        }
        Toast.makeText(QuestionsActivity.this, serie, Toast.LENGTH_SHORT).show();
        nextView();
        // TODO conecteaza raspunsul la baza de date
    }
    public void configureQuestion03(){
        answer_03 = findViewById(R.id.answerText_03);
        answer_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO conecteaza raspunsul la baza de date
                group = answer_03.getText().toString();
                Toast.makeText(QuestionsActivity.this, group, Toast.LENGTH_SHORT).show();
                nextView();
            }
        });
    }
    public void configureQuestion04(){
        yes = findViewById(R.id.questionPositive);
        no = findViewById(R.id.questionNegative);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVoluntar = true;
                Toast.makeText(QuestionsActivity.this, isVoluntar + " congratulations", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVoluntar = false;
                Toast.makeText(QuestionsActivity.this, isVoluntar + " congratulations", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // TODO conecteaza raspunsul la baza de date
    }

    public void setProfile(String profile){
        AA = findViewById(R.id.AA);
        AB = findViewById(R.id.AB);
        AC = findViewById(R.id.AC);
        CA = findViewById(R.id.CA);
        CB = findViewById(R.id.CB);
        CC = findViewById(R.id.CC);
        CD = findViewById(R.id.CD);
        if (profile.equals("CTI")){
            AA.setVisibility(View.GONE);
            AB.setVisibility(View.GONE);
            AC.setVisibility(View.GONE);
            CA.setVisibility(View.VISIBLE);
            CB.setVisibility(View.VISIBLE);
            CC.setVisibility(View.VISIBLE);
            CD.setVisibility(View.VISIBLE);
        }else{
            AA.setVisibility(View.VISIBLE);
            AB.setVisibility(View.VISIBLE);
            AC.setVisibility(View.VISIBLE);
            CA.setVisibility(View.GONE);
            CB.setVisibility(View.GONE);
            CC.setVisibility(View.GONE);
            CD.setVisibility(View.GONE);
        }
    }
}
