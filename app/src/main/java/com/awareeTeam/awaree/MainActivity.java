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
        setContentView(R.layout.activity_main);

        //database = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/");
        //reference = database.getReference();
        //user = new User(1, "Vasile", "Petrescu", "Vasi", "vasipetrescu@gmail.com", true, "vasitotal123", "CTI", "CB");

        //reference.child("User").child(user.getID() + "");
        //reference = database.getReference("User/" + user.getID() + "");
        //reference.child("User").child(user.getID() + "").setValue(user);

    }
}
