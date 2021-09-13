package com.awareeTeam.awaree;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ProgressBar progressIndicator;
    private FloatingActionButton fab;


    //vars for homework
    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mDifficulty = new ArrayList<>();
    private ArrayList<Integer> mDifficultyLvl = new ArrayList<>();
    private ArrayList<String> mDifficultyTime = new ArrayList<>();
    private ArrayList<String> mPriority = new ArrayList<>();

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
                            initRecyclerViewSj();

                            break;
                        case R.id.nav_profile:
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
        mDifficultyTime.add("30");
        mPriority.add("High");

        mClassNames.add("Maths");
        mDifficulty.add("hard");
        mDifficultyLvl.add(70);
        mDifficultyTime.add("120");
        mPriority.add("Medium");

        mClassNames.add("English");
        mDifficulty.add("easy");
        mDifficultyLvl.add(15);
        mDifficultyTime.add("15");
        mPriority.add("Low");

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
        RecyclerViewAdapterHw adapter = new RecyclerViewAdapterHw(this, mClassNames, mDifficulty, mDifficultyLvl, mDifficultyTime, mPriority);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fab);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        fab.setOnClickListener(v -> openDialog());

    }

    private void openDialog() {HomeworkDialog.display(getSupportFragmentManager());}

    //initialise subject view
    private void initRecyclerViewSj(){

        progressIndicator = findViewById(R.id.indicator);
        progressIndicator.setVisibility(View.VISIBLE);
        Log.d(TAG, "initRecyclerViewSj: found it");
        new FirebaseDatabaseHelper().readSubjects(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {
                Log.d(TAG, "DataIsLoaded: not yet");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: delayed");
                        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_sj);
                        new RecyclerViewAdapterSj().setConfig(mRecyclerView, MainActivity.this, subjects, keys);
                        progressIndicator.setVisibility(View.GONE);
                    }
                }, 10);
                Log.d(TAG, "DataIsLoaded: true");
            }

            @Override
            public void DataIsLoadedHw(List<Homework> homework, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
    /*private void initRecyclerViewHw(){

        progressIndicator = findViewById(R.id.indicator);
        progressIndicator.setVisibility(View.VISIBLE);
        Log.d(TAG, "initRecyclerViewSj: found it");
        new FirebaseDatabaseHelper().readSubjects(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

            }

            @Override
            public void DataIsLoadedHw(List<Homework> homework, List<String> keys) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: delayed");
                        RecyclerView recyclerView = findViewById(R.id.recycler_view_hw);
                        recyclerView.setNestedScrollingEnabled(false);
                        RecyclerViewAdapterHw adapter = new RecyclerViewAdapterHw(this, mClassNames, mDifficulty, mDifficultyLvl, mDifficultyTime, mPriority);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        progressIndicator.setVisibility(View.GONE);
                    }
                }, 10);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }*/
}
