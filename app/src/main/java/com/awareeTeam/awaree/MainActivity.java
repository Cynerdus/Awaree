package com.awareeTeam.awaree;

import static android.content.ContentValues.TAG;

import static com.google.android.material.math.MathUtils.lerp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.brkckr.circularprogressbar.CircularProgressBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

//import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private long showedGreet;
    private Toast backToast;
    private int homeworkTotal;

    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressIndicator;
    private CircularProgressBar circularProgressBar;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;

    private TextView greet;
    private TextView freeTime, hwTime;
    private TextView noHw;
    private String username;
    private String hwS, freeS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressIndicator = findViewById(R.id.indicator);
        bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();

        initRecyclerViewHw();
        initLate();
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
                            initLate();
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

    private void initLate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initUsername();
                initFab();
                initProgressbar();
            }
        }, 10);
    }

    /*public static MainActivity openDialog() {
        CoordinatorLayout coordinatorLayout =
        Snackbar.make(coordinatorLayout, "Homework created (jk it's not implemented yet)", BaseTransientBottomBar.LENGTH_SHORT)
                .show();
    }*/

    @Override
    protected void onStart() {
        super.onStart();
    }

    //initialise subject view
    private void initRecyclerViewSj(){
        progressIndicator.setVisibility(View.VISIBLE);
        new FirebaseDatabaseHelper().readSubjects(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView = findViewById(R.id.recycler_view_sj);
                        new RecyclerViewAdapterSj().setConfig(mRecyclerView, MainActivity.this, sortListBy(subjects), keys);
                        progressIndicator.setVisibility(View.GONE);
                    }
                }, 10);
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

    public void initRecyclerViewHw(){
        progressIndicator.setVisibility(View.VISIBLE);
        new FirebaseDatabaseHelper().readHomework(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

            }
            @Override
            public void DataIsLoadedHw(List<Homework> homework, List<String> keys) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Homework homework1;
                        List<Homework> sortedHomework;
                        RecyclerView mRecyclerView;

                        homeworkTotal = 0;
                        noHw = findViewById(R.id.noHw);
                        circularProgressBar = findViewById(R.id.progressBar);
                        mRecyclerView = findViewById(R.id.recycler_view_hw);

                        mRecyclerView.setNestedScrollingEnabled(false);
                        progressIndicator.setVisibility(View.GONE);

                        // Merge the List
                        for (int i = 0; i < homework.size(); i++){
                            homework1 = homework.get(i);
                            homework1.setTempKey(keys.get(i));
                            homework.set(i, homework1);
                        }

                        sortedHomework = sortListByPriority(homework);

                        for (int i = 0; i < homework.size(); i++){
                            keys.set(i, homework.get(i).getTempKey());
                        }

                        if (homework.isEmpty()){
                            noHw.setVisibility(View.VISIBLE);
                        }else{
                            noHw.setVisibility(View.GONE);
                            new RecyclerViewAdapterHw().setConfig(mRecyclerView, MainActivity.this, sortedHomework, keys);
                        }

                        for (int i = 0; i < homework.size(); i++) {
                            homeworkTotal += Integer.parseInt(homework.get(i).getDuration());
                        }
                        initProgressbar();
                        float home = homeworkTotal;
                        float amount = home/480;
                        float homework = lerp(0, 100, amount);
                        Log.d(TAG, "run: " + homework);
                        Log.d(TAG, "run: " + amount);
                        circularProgressBar.setProgressValueWithAnimation(homework);
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

    private void initProgressbar() {
        freeTime = findViewById(R.id.freeTime);
        hwTime = findViewById(R.id.homeworkTime);
        int free;
        free = 8*60 - homeworkTotal;

        if (homeworkTotal < 60){
            hwS = homeworkTotal + "m";
        }else if (homeworkTotal%60 != 0) {
            hwS = homeworkTotal/60 + "h" + homeworkTotal%60 + "m";
        }else {
            hwS = homeworkTotal/60 + "h";
        }

        if (free < 60){
            freeS = free + "m";
        }else if (free%60 != 0) {
            freeS = free/60 + "h" + free%60 + "m";
        }else {
            freeS = free/60 + "h";
        }

        freeTime.setText(freeS);
        hwTime.setText(hwS);
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

    private void initUsername(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        sharedPreferences.getString("username", username);

        greet = findViewById(R.id.greeting);
        String[] greetings = getResources().getStringArray(R.array.greetings);
        if (showedGreet + 5000 > System.currentTimeMillis()){
            String randomStr = greetings[new Random().nextInt(greetings.length)];
            greet.setText(String.format("%s %s", randomStr, username));
        }

        showedGreet = System.currentTimeMillis();
    }

    private void sortListByDate(List<Homework> homework){
        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        SimpleDateFormat day = new SimpleDateFormat("d");
        String date = dateFormat.format(calendar.getTime());
        String today = dateFormat.format(calendar.getTime());
        String hwDay;

        Log.d(TAG, "sortListByDate: "+ date);
        for (int i = 0; i < homework.size(); i++) {
            hwDay = dateFormat.format(homework.get(i).getDueDate());

            //hwDay.
            if (true){

            }
        }
    }

    private List<Homework> sortListByPriority(List<Homework> homework){

        Collections.sort(homework, new Comparator<Homework>(){
            public int compare(Homework obj1, Homework obj2) {
                return obj1.getPriority().compareToIgnoreCase(obj2.getPriority());
                // TODO make priority update the database when set, that way the list sorts by priority

            }
        });
        return homework;
    }

    private List<Subject> sortListBy(List<Subject> subjects){
        Spinner spinnerSort = findViewById(R.id.sortBy);
        Spinner spinnerWay = findViewById(R.id.upDown);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                initRecyclerViewSj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        spinnerWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                initRecyclerViewSj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        Collections.sort(subjects, new Comparator<Subject>(){
            public int compare(Subject obj1, Subject obj2) {
                // ## Ascending order
                Object selectedItem = spinnerSort.getSelectedItem();
                Object selectedItem1 = spinnerWay.getSelectedItem();
                Log.d(TAG, "compare: " + selectedItem1);
                Log.d(TAG, "compare: " + spinnerWay.getItemIdAtPosition(1));
                if (spinnerWay.getItemAtPosition(0).equals(selectedItem1)) {
                    if (spinnerSort.getItemAtPosition(0).equals(selectedItem)) {
                        return obj1.getSubjectName().compareToIgnoreCase(obj2.getSubjectName());
                    } else if (spinnerSort.getItemAtPosition(1).equals(selectedItem)) {
                        return Integer.valueOf(obj1.getCredits()).compareTo(Integer.valueOf(obj2.getCredits()));
                    } else {
                        return obj1.getIsExam().compareToIgnoreCase(obj2.getIsExam());
                    }
                } else {
                    if (spinnerSort.getItemAtPosition(0).equals(selectedItem)) {
                        return obj2.getSubjectName().compareToIgnoreCase(obj1.getSubjectName());
                    } else if (spinnerSort.getItemAtPosition(1).equals(selectedItem)) {
                        return Integer.valueOf(obj2.getCredits()).compareTo(Integer.valueOf(obj1.getCredits()));
                    } else {
                        return obj2.getIsExam().compareToIgnoreCase(obj1.getIsExam());
                    }
                }

            }
        });
        return subjects;
    }
}
