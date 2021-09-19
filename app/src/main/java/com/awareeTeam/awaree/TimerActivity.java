package com.awareeTeam.awaree;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TimerActivity extends AppCompatActivity {
    private String timeS;
    private String description, priority, difficulty;
    private String key;
    private int time;
    private long secondsUntilFinished;
    private boolean timerOn;
    private Homework homework;

    private CountDownTimer countDownTimer;
    private FloatingActionButton startTimer;
    private TextView timer;
    private TextView descriptionView;
    private Button addTime_1, addTime_2, addTime_3;
    private Button leaveForLater, finishTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        homework = new Homework();

        timeS = getIntent().getStringExtra("time");
        description = getIntent().getStringExtra("subject");
        priority = getIntent().getStringExtra("priority");
        difficulty = getIntent().getStringExtra("difficulty");
        key = getIntent().getStringExtra("key");

        time = Integer.parseInt(timeS);

        timer = findViewById(R.id.timer);
        descriptionView = findViewById(R.id.descriptionView);
        addTime_1 = findViewById(R.id.addtime_1);
        addTime_2 = findViewById(R.id.addtime_2);
        addTime_3 = findViewById(R.id.addtime_3);
        startTimer = findViewById(R.id.startTimer);
        finishTimer = findViewById(R.id.finishTimer);
        leaveForLater = findViewById(R.id.leaveForLater);

        descriptionView.setText(description);

        addTime_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondsUntilFinished = secondsUntilFinished * 1000L + 10*60*1000L;
                countDownTimer.cancel();
                startCountdown();
            }
        });
        addTime_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondsUntilFinished = secondsUntilFinished * 1000L + 30*60*1000L;
                countDownTimer.cancel();
                startCountdown();
            }
        });
        addTime_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondsUntilFinished = secondsUntilFinished * 1000L + 60*60*1000L;
                countDownTimer.cancel();
                startCountdown();
            }
        });

        finishTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseDatabaseHelper().deleteHomework(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedHw(List<Homework> homeworks, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        finish();
                    }
                });
            }
        });
        leaveForLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homework.setDifficulty(difficulty);
                homework.setDuration(Long.toString(secondsUntilFinished/60));
                homework.setClassRef(description);
                homework.setPriority(priority);

                new FirebaseDatabaseHelper().updateHomework(key, homework, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedHw(List<Homework> homeworks, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        finish();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerOn){
                    startTimer.setImageResource(R.drawable.timer_start);
                    countDownTimer.cancel();

                    timerOn = false;
                }else{
                    startTimer.setImageResource(R.drawable.timer_pause);
                    secondsUntilFinished = secondsUntilFinished * 1000L;
                    startCountdown();
                    timerOn = true;
                }

            }
        });
        timerOn = true;
        startTimer.setImageResource(R.drawable.timer_pause);
        secondsUntilFinished = time * 60 * 1000L;
        startCountdown();
    }

    private void startCountdown(){
        countDownTimer = new CountDownTimer(secondsUntilFinished, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsUntilFinished = millisUntilFinished/1000;
                long seconds = secondsUntilFinished%60;
                long minutes = (secondsUntilFinished/60)%60;
                long hours = (secondsUntilFinished/60)/60;
                String secondsS, minutesS, hoursS;

                if (seconds<10){
                    secondsS = String.format("0%s", seconds);
                }else{
                    secondsS = Long.toString(seconds);
                }
                if (minutes<10){
                    minutesS = String.format("0%s", minutes);
                }else{
                    minutesS = Long.toString(minutes);
                }
                if (hours<10){
                    hoursS = String.format("0%s", hours);
                }else{
                    hoursS = Long.toString(hours);
                }

                timer.setText(String.format("%s:%s:%s", hoursS, minutesS, secondsS));
            }

            public void onFinish() {
                timer.setText("Congrats, you finished");
            }
        }.start();

    }
}
