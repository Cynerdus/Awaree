package com.awareeTeam.awaree;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
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

    //vars
    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mDifficulty = new ArrayList<>();
    private ArrayList<Integer> mDifficultyLvl = new ArrayList<>();
    private ArrayList<String> mDifficultyTime = new ArrayList<>();
    private TextView greet;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Opened activity");
        //Greeting the user
        //username = "Vasi";
        //greet = findViewById(R.id.greeting);
        //greet.setText("Welcome back " + username);

        Log.d(TAG, "onCreate: Set greeting");
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();



        initHomework();
        Log.d(TAG, "onCreate: initialised homework");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_subjects:
                            selectedFragment = new SubjectsFragment();
                            break;
                        case R.id.nav_settings:
                            //selectedFragment = new HomeFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


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

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mClassNames, mDifficulty, mDifficultyLvl, mDifficultyTime);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
