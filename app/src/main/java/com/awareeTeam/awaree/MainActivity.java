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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class MainActivity extends AppCompatActivity {
    private ProgressBar progressIndicator;
    private FloatingActionButton fab;
    private long backPressedTime;
    private Toast backToast;
    private int homeworkTotal;


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

        CircularProgressIndicator circularProgressIndicator = findViewById(R.id.progressBar);
        int freeTime = 500;
        int total = homeworkTotal+freeTime;
        total = 1000;
        //circularProgressIndicator.setMaxProgress(total);
        //circularProgressIndicator.setCurrentProgress(homeworkTotal);

        initRecyclerViewHw();

        Log.d(TAG, "onCreate: initialised homework");

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
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
                            initRecyclerViewHw();
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

    /*public static MainActivity openDialog() {
        CoordinatorLayout coordinatorLayout =
        Snackbar.make(coordinatorLayout, "Homework created (jk it's not implemented yet)", BaseTransientBottomBar.LENGTH_SHORT)
                .show();
    }*/

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
    private void initRecyclerViewHw(){

        progressIndicator = findViewById(R.id.indicator);
        progressIndicator.setVisibility(View.VISIBLE);
        Log.d(TAG, "initRecyclerViewSj: found it");
        new FirebaseDatabaseHelper().readHomework(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

            }

            @Override
            public void DataIsLoadedHw(List<Homework> homework, List<String> keys) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: delayed");
                        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_hw);
                        mRecyclerView.setNestedScrollingEnabled(false);
                        new RecyclerViewAdapterHw().setConfig(mRecyclerView, MainActivity.this, homework, keys);
                        progressIndicator.setVisibility(View.GONE);
                        initFab();
                        for (int i = 0; i < homework.size(); i++)
                            homeworkTotal += Integer.parseInt(homework.get(i).getDuration());
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
    }
    private void initFab(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeworkDialog.display(getSupportFragmentManager());

            }
        });
    }
}
