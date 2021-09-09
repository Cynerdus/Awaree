package com.awareeTeam.awaree;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
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

        //Greeting the user
        username = "Vasi";
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        //NavController navController = Navigation.findNavController(this, R.id.nav_main);
        //AppBarConfiguration appBarConfiguration = AppBarConfiguration(R.id.mainActivity);
        //NavigationUI.setupWithNavController(bottomNavigationView, navController);


        greet = findViewById(R.id.greeting);
        greet.setText("Welcome back " + username);

        initHomework();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return false;
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
