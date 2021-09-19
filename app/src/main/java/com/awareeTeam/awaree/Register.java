package com.awareeTeam.awaree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private TextInputEditText emailText, usernameText, passwordText;
    private TextInputLayout emailLayout, userLayout, passwordLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_register);

        TextView haveAccount = findViewById(R.id.alreadyHaveAccount);
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        database = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/");
        reference = database.getReference("User");

        initialSetup();

        configureRegistration();
    }

    private void configureRegistration() {
        Button backButton = findViewById(R.id.registerButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = emailText.getText().toString(), usernameString = usernameText.getText().toString(), passwordString = passwordText.getText().toString();
                if (emailString.isEmpty()) {
                    emailLayout.setError(getString(R.string.email_error));
                }
                if (usernameString.isEmpty()) {
                    userLayout.setError(getString(R.string.user_error));
                }
                if (passwordString.isEmpty()) {
                    passwordLayout.setError(getString(R.string.password_error));
                }

                if (!emailString.isEmpty() && !usernameString.isEmpty() && !passwordString.isEmpty()) {
                    if (!isEmailValid(emailString)) {
                        emailLayout.setError(getString(R.string.email_inv_error));
                    } else {
                        emailLayout.setError(null);

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
                                        emailLayout.setError(getString(R.string.email_taken_error));
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
                                        userLayout.setError(getString(R.string.taken_error));
                                    } else if (usernameString.length() < 3) {
                                        userLayout.setError(getString(R.string.user_short_error));
                                    } else if (passwordString.length() < 6) { //pana aici avem email valid si username bun
                                        passwordLayout.setError(getString(R.string.pass_short_error));
                                    } else {
                                        if (!passwordString.matches(".*\\d.*")) {
                                            passwordLayout.setError(getString(R.string.pass_num_error));
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
                rememberCredentials();
            }
        });
    }

    private void rememberCredentials(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailText.getText().toString());
        editor.putString("password", usernameText.getText().toString());
        editor.putString("username", passwordText.getText().toString());
        editor.putBoolean("loginStatus", true);
        editor.apply();
    }

    private void initialSetup() {
        emailText = findViewById(R.id.registerEmail);
        usernameText = findViewById(R.id.registerUsername);
        passwordText = findViewById(R.id.registerPassword);
        emailLayout = findViewById(R.id.emailLayoutR);
        passwordLayout = findViewById(R.id.passwordLayoutR);
        userLayout = findViewById(R.id.usernameLayoutR);
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}