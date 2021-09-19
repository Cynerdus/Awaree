package com.awareeTeam.awaree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IntroductionActivity extends AppCompatActivity {

    private ImageView image;
    private TextView quote;
    private int current_layout = 1, start_mark = 0;
    private float targetX, targetY;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String INTRODUCTION_VIEWED = "introductionViewed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        quote = (TextView) findViewById(R.id.quoteTextView);
        image = (ImageView) findViewById(R.id.aestheticImageView);
        targetX = image.getX();
        targetY = image.getY();

        TextView dot1 = (TextView) findViewById(R.id.dotScrollTextView1);
        TextView dot2 = (TextView) findViewById(R.id.dotScrollTextView2);
        TextView dot3 = (TextView) findViewById(R.id.dotScrollTextView3);

        TextView back = (TextView) findViewById(R.id.backTextView);
        TextView next = (TextView) findViewById(R.id.nextTextView);

        back.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (current_layout) {
                    case 1:
                        dot1.setTextColor(getColor(R.color.Light_Gray));
                        dot2.setTextColor(getColor(R.color.Indigo_Dye));
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        dot2.setTextColor(getColor(R.color.Light_Gray));
                        dot3.setTextColor(getColor(R.color.Indigo_Dye));
                        next.setText("Start");
                        break;
                }
                if (current_layout != 3) {
                    imageTransitionToRight(current_layout);
                    current_layout++;
                } else {
                    start_mark++;

                    if(start_mark == 1) {
                        Animation fade_out = new AlphaAnimation(1.0f, 0.0f);
                        fade_out.setDuration(1300);
                        image.startAnimation(fade_out);
                        quote.startAnimation(fade_out);

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("seenIntroduction", true);
                        editor.apply();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(IntroductionActivity.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 800);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (current_layout) {
                    case 2:
                        dot2.setTextColor(getColor(R.color.Light_Gray));
                        dot1.setTextColor(getColor(R.color.Indigo_Dye));
                        back.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        if (start_mark == 0) {
                            dot3.setTextColor(getColor(R.color.Light_Gray));
                            dot2.setTextColor(getColor(R.color.Indigo_Dye));
                            next.setText("Next");
                        }
                        break;
                }
                if (current_layout != 1 && start_mark == 0) {
                    imageTransitionToLeft(current_layout);
                    current_layout--;
                }
            }
        });

    }

    private void imageTransitionToRight(int index) {
        Animation fade_out = new AlphaAnimation(1.0f, 0.0f);
        Animation fade_in = new AlphaAnimation(0.0f, 1.0f);
        fade_out.setDuration(700);
        fade_in.setDuration(600);

        TranslateAnimation animation = new TranslateAnimation(targetX, -image.getWidth(), targetY, targetY); // (xFrom,xTo, yFrom,yTo)
        TranslateAnimation reaction = new TranslateAnimation(600 + image.getWidth(), targetX, targetY, targetY);
        animation.setDuration(700);
        reaction.setDuration(700);

        image.startAnimation(animation);
        quote.startAnimation(fade_out);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (index) {
                    case 1:
                        image.setImageResource(R.drawable.aesthetic_pic2);
                        quote.setText("Easy to use.");
                        break;
                    case 2:
                        image.setImageResource(R.drawable.aesthetic_pic3);
                        quote.setText("Customized for each individual.");
                        break;
                }
                quote.startAnimation(fade_in);
                image.startAnimation(reaction);
            }
        }, 700);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.setX(targetX + 50);
            }
        }, 1400);
    }

    private void imageTransitionToLeft(int index) {
        Animation fade_out = new AlphaAnimation(1.0f, 0.0f);
        Animation fade_in = new AlphaAnimation(0.0f, 1.0f);
        fade_out.setDuration(700);
        fade_in.setDuration(600);

        TranslateAnimation animation = new TranslateAnimation(targetX, 600 + image.getWidth(), targetY, targetY); // (xFrom,xTo, yFrom,yTo)
        TranslateAnimation reaction = new TranslateAnimation(-image.getWidth(), targetX, targetY, targetY);
        animation.setDuration(700);
        reaction.setDuration(700);

        image.startAnimation(animation);
        quote.startAnimation(fade_out);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (index) {
                    case 2:
                        image.setImageResource(R.drawable.aesthetic_pic1);
                        quote.setText("The key is not in spending time, but in how you invest it.");
                        break;
                    case 3:
                        image.setImageResource(R.drawable.aesthetic_pic2);
                        quote.setText("Easy to use.");
                        break;
                }
                quote.startAnimation(fade_in);
                image.startAnimation(reaction);
            }
        }, 700);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.setX(targetX + 50);
            }
        }, 1400);
    }
}