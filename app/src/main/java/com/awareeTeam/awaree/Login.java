package com.awareeTeam.awaree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ImageView missingEmail, missingPassword;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_login);

        missingEmail = (ImageView) findViewById(R.id.missingEmailImageView);
        missingPassword = (ImageView) findViewById(R.id.missingPassImageView);
        missingEmail.setVisibility(View.INVISIBLE);
        missingPassword.setVisibility(View.INVISIBLE);

        TextView needAccount = (TextView) findViewById(R.id.needAccount);
        needAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        configureLoginButton();
    }

    private void configureLoginButton() {

        Button loginButton = (Button) findViewById(R.id.login);
        EditText email = (EditText) findViewById(R.id.loginEmail);
        EditText password = (EditText) findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (emailString.isEmpty()) {
                    missingEmail.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                } else missingEmail.setVisibility(View.INVISIBLE);
                if (passwordString.isEmpty()) {
                    missingPassword.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                } else missingPassword.setVisibility(View.INVISIBLE);

                if (!emailString.isEmpty() && !passwordString.isEmpty()) {
                    database = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/");
                    reference = database.getReference("User");

                    Log.d("DB Read", "Checkpoint 1 passed successfully. Connected to Firebase.");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("DB Read", "Checkpoint 2 passed successfully. Looking through User's children.");
                            int i = 1;
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Log.d("DB Read", "Starting to check child no " + i + ".");
                                Log.d("DB Read", "User " + data.getValue(User.class).getUsername() + " has ID " + i + ".");
                                user = new User();
                                user.setEmail(data.getValue(User.class).getEmail());
                                if (user != null && user.getEmail().equals(emailString)) {
                                    Log.d("DB Read", "Found the dude.");
                                    user.setPassword(data.getValue(User.class).getPassword());
                                    if (user.getPassword().equals(passwordString)) {
                                        user.setProfile(data.getValue(User.class).getProfile());
                                        user.setSeries(data.getValue(User.class).getSeries());
                                        user.setUsername(data.getValue(User.class).getUsername());
                                        user.setEmailVerified(data.getValue(User.class).isEmailVerified());
                                        user.setID(i);

                                        missingEmail.setVisibility(View.INVISIBLE);
                                        missingPassword.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Login.this, "Welcome!", Toast.LENGTH_SHORT).show();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 400);

                                        break;
                                    } else {
                                        Log.d("DB Read", "Valid email address, incorrect password.");
                                        Toast.makeText(Login.this, "Incorrect Password.", Toast.LENGTH_SHORT).show();
                                        missingPassword.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            if (!user.getEmail().equals(emailString)) {
                                Log.d("DB Read", "Invalid email address.");
                                Toast.makeText(Login.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                                missingEmail.setVisibility(View.VISIBLE);
                                missingPassword.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("DB Read", "Error occurred.");
                            Toast.makeText(Login.this, "An error occurred.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
