package com.awareeTeam.awaree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_login);


        //Toast.makeText(MainActivity.this, "Firebase connected successfully.", Toast.LENGTH_SHORT).show();

        //reference = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/").getReference();
        //user = new User(1, "Vasile", "Petrescu", "Vasi", "vasipetrescu@gmail.com", true, "vasitotal123", "CTI", "CB");

        //reference.push().setValue(user);

    }
}
