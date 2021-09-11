package com.awareeTeam.awaree;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //vars for homework
    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mDifficulty = new ArrayList<>();
    private ArrayList<Integer> mDifficultyLvl = new ArrayList<>();
    private ArrayList<String> mDifficultyTime = new ArrayList<>();
    //vars for subjects
    private ArrayList<String> mSubjectNames = new ArrayList<>();
    private ArrayList<String> mSubjectCategory = new ArrayList<>();
    private ArrayList<Integer> mCredits = new ArrayList<>();
    private ArrayList<Integer> mCoursesNumber = new ArrayList<>();
    private ArrayList<Integer> mSeminariesNumber = new ArrayList<>();
    private ArrayList<Integer> mLabsNumber = new ArrayList<>();
    private ArrayList<Boolean> mIsExam = new ArrayList<>();

    private TextView greet;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Greeting the user
        //username = "Vasi";
        //greet = findViewById(R.id.greeting);
        //greet.setText("Welcome back " + username);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        initHomework();
        Log.d(TAG, "onCreate: initialised homework");

    }

    //Switch between views
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new DashboardFragment();
                            initHomework();
                            break;
                        case R.id.nav_subjects:
                            selectedFragment = new SubjectsFragment();
                            initSubjects();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


    // initialise homework
    // TODO make connections with the database
    private void initHomework(){

        mClassNames.add("Physics homework");
        mDifficulty.add("medium");
        mDifficultyLvl.add(30);
        mDifficultyTime.add("30 minutes");

        mClassNames.add("Maths");
        mDifficulty.add("hard");
        mDifficultyLvl.add(70);
        mDifficultyTime.add("50 minutes");

        mClassNames.add("English");
        mDifficulty.add("easy");
        mDifficultyLvl.add(10);
        mDifficultyTime.add("10 minutes");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerViewHw();
            }
        }, 10);

    }

    private void initRecyclerViewHw(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_hw);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerViewAdapterHw adapter = new RecyclerViewAdapterHw(this, mClassNames, mDifficulty, mDifficultyLvl, mDifficultyTime);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //initialise subjects
    // TODO make connections with the database
    private void initSubjects(){


        mSubjectNames.add("Algebra");
        mSubjectCategory.add("Maths");
        mCredits.add(5);
        mCoursesNumber.add(3);
        mSeminariesNumber.add(2);
        mLabsNumber.add(0);
        mIsExam.add(true);

        mSubjectNames.add("Calculus");
        mSubjectCategory.add("Maths");
        mCredits.add(5);
        mCoursesNumber.add(3);
        mSeminariesNumber.add(2);
        mLabsNumber.add(0);
        mIsExam.add(true);

        mSubjectNames.add("Mecanica");
        mSubjectCategory.add("Optionale");
        mCredits.add(3);
        mCoursesNumber.add(1);
        mSeminariesNumber.add(0);
        mLabsNumber.add(1);
        mIsExam.add(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerViewSj();
            }
        }, 10);

    }

    private void initRecyclerViewSj(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_sj);
        recyclerView.clearDisappearingChildren();
        RecyclerViewAdapterSj adapter = new RecyclerViewAdapterSj(this, mSubjectNames, mSubjectCategory, mCredits, mCoursesNumber, mSeminariesNumber, mLabsNumber, mIsExam);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
