package com.awareeTeam.awaree;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private TextView errorEmailText, errorUsernameText, errorPasswordText;
    private EditText emailText, usernameText, passwordText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_register);

        TextView haveAccount = (TextView) findViewById(R.id.alreadyHaveAccount);
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/");
        reference = database.getReference("User");

        initialSetup();

        configureRegistration();
    }

    private void configureRegistration() {
        Button backButton = (Button) findViewById(R.id.registerButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = emailText.getText().toString(), usernameString = usernameText.getText().toString(), passwordString = passwordText.getText().toString();
                if (emailString.isEmpty()) {
                    errorEmailText.setText("Please enter your email address.");
                    errorEmailText.setVisibility(View.VISIBLE);
                } else errorEmailText.setVisibility(View.INVISIBLE);
                if (usernameString.isEmpty()) {
                    errorUsernameText.setText("Please enter a username.");
                    errorUsernameText.setVisibility(View.VISIBLE);
                } else errorUsernameText.setVisibility(View.INVISIBLE);
                if (passwordString.isEmpty()) {
                    errorPasswordText.setText("Please enter a password.");
                    errorPasswordText.setVisibility(View.VISIBLE);
                } else errorPasswordText.setVisibility(View.INVISIBLE);

                if (!emailString.isEmpty() && !usernameString.isEmpty() && !passwordString.isEmpty()) {
                    if (!isEmailValid(emailString)) {
                        errorEmailText.setText("Please enter a valid email address.");
                        errorEmailText.setVisibility(View.VISIBLE);
                    } else {
                        errorEmailText.setVisibility(View.INVISIBLE);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean foundTheDude = false, usernameTaken = false, check = false;
                                int countIds = 1;

                                Log.d("DB Read", "Starting to look through users for email '" + emailString + "'.");
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    if (data.getValue(User.class).getID() == countIds && !check) {
                                        countIds++;
                                    } else check = true;


                                    if (data.getValue(User.class).getEmail().equals(emailString)) {
                                        errorEmailText.setText("Email address already registered.");
                                        errorEmailText.setVisibility(View.VISIBLE);
                                        foundTheDude = true;
                                        Log.d("DB Read", "Found it. Email already registered.");
                                    }
                                    if (data.getValue(User.class).getUsername().equals(usernameString)) {
                                        usernameTaken = true;
                                        Log.d("DB Read", "Username '" + usernameString + "' taken.");
                                    }
                                }
                                if (!foundTheDude) {
                                    if (usernameTaken) {
                                        errorUsernameText.setText("Username taken.");
                                        errorUsernameText.setVisibility(View.VISIBLE);
                                    } else if (usernameString.length() < 3) {
                                        errorUsernameText.setText("Username length should be at least 3 characters.");
                                        errorUsernameText.setVisibility(View.VISIBLE);
                                    } else if (passwordString.length() < 6) { //pana aici avem email valid si username bun
                                        errorPasswordText.setText("Password length should be over 5 characters.");
                                        errorPasswordText.setVisibility(View.VISIBLE);
                                    } else {
                                        if (!passwordString.matches(".*\\d.*")) {
                                            errorPasswordText.setText("Password should have at least one digit.");
                                            errorPasswordText.setVisibility(View.VISIBLE);
                                        } else {
                                            //daca am ajuns aici, suntem bines
                                            Log.d("DB Read", "User information is valid. Proceeding to next step.");
                                            User user = new User();
                                            user.setID(countIds);
                                            user.setEmail(emailString);
                                            user.setUsername(usernameString);
                                            user.setPassword(passwordString);
                                            reference.child(countIds + "").setValue(user);
                                            Log.d("DB Read", "Added data to database successfully.");

                                            Intent intent = new Intent(Register.this, QuestionsActivity.class);
                                            intent.putExtra("id", user.getID());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
        });
    }

    private void initialSetup() {
        emailText = (EditText) findViewById(R.id.registerEmail);
        usernameText = (EditText) findViewById(R.id.registerUsername);
        passwordText = (EditText) findViewById(R.id.registerPassword);

        errorEmailText = (TextView) findViewById(R.id.errorEmailText);
        errorUsernameText = (TextView) findViewById(R.id.errorUsernameText);
        errorPasswordText = (TextView) findViewById(R.id.errorPasswordText);
        errorEmailText.setTextColor(getColor(R.color.Burnt_Sienna));
        errorUsernameText.setTextColor(getColor(R.color.Burnt_Sienna));
        errorPasswordText.setTextColor(getColor(R.color.Burnt_Sienna));
        errorEmailText.setVisibility(View.INVISIBLE);
        errorUsernameText.setVisibility(View.INVISIBLE);
        errorPasswordText.setVisibility(View.INVISIBLE);
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}