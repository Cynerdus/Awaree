package com.awareeTeam.awaree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
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
    private User user;
    private Button loginButton;
    private EditText email, password;
    private TextView emailError, passwordError, passwordForgotten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_login);

        TextView needAccount = (TextView) findViewById(R.id.needAccount);
        needAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        initialSetup();
        configureLoginButton();
        forgotPassword();
    }

    private void configureLoginButton() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (emailString.isEmpty()) {
                    emailError.setText("Please enter your email address.");
                    emailError.setVisibility(View.VISIBLE);
                } else emailError.setVisibility(View.INVISIBLE);
                if (passwordString.isEmpty()) {
                    passwordError.setText("Please enter your password.");
                    passwordError.setVisibility(View.VISIBLE);
                } else passwordError.setVisibility(View.INVISIBLE);

                if (!emailString.isEmpty() && !passwordString.isEmpty()) {
                    database = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/");
                    reference = database.getReference("User");

                    Log.d("DB Read", "Checkpoint 1 passed successfully. Connected to Firebase.");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("DB Read", "Checkpoint 2 passed successfully. Looking through User's children.");

                            boolean validEmail = false;
                            for (DataSnapshot data : snapshot.getChildren()) {

                                Log.d("DB Read", "User " + data.getValue(User.class).getUsername() + " has ID " + data.getValue(User.class).getID() + ".");
                                user = new User();
                                user.setEmail(data.getValue(User.class).getEmail());
                                if (user != null && user.getEmail().equals(emailString)) {
                                    validEmail = true;
                                    Log.d("DB Read", "Found the dude.");
                                    user.setPassword(data.getValue(User.class).getPassword());
                                    if (user.getPassword().equals(passwordString)) {
                                        user.setProfile(data.getValue(User.class).getProfile());
                                        user.setSeries(data.getValue(User.class).getSeries());
                                        user.setUsername(data.getValue(User.class).getUsername());
                                        user.setEmailVerified(data.getValue(User.class).isEmailVerified());
                                        user.setID((int) snapshot.getChildrenCount() + 1);

                                        emailError.setVisibility(View.INVISIBLE);
                                        passwordError.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Login.this, "Welcome!", Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("loginStatus", true);
                                        editor.apply();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 400);

                                    } else {
                                        Log.d("DB Read", "Valid email address, incorrect password.");
                                        passwordError.setText("Incorrect password.");
                                        passwordError.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            if (!validEmail) {
                                Log.d("DB Read", "Invalid email address.");
                                emailError.setText("Invalid email address.");
                                emailError.setVisibility(View.VISIBLE);
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

    private void forgotPassword() {
        passwordForgotten = (TextView) findViewById(R.id.passwordForgot);

        passwordForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    emailError.setText("Please enter the email first.");
                    emailError.setVisibility(View.VISIBLE);
                } else {
                    if (!isEmailValid(email.getText().toString())) {
                        emailError.setText("Please enter a valid email address.");
                        emailError.setVisibility(View.VISIBLE);
                    } else {
                        emailError.setVisibility(View.INVISIBLE);
                        database = FirebaseDatabase.getInstance("https://awaree-ea116-default-rtdb.firebaseio.com/");
                        reference = database.getReference("User");

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean foundTheDude = false;
                                User user = new User();
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    if (data.getValue(User.class).getEmail().equals(email.getText().toString())) {
                                        foundTheDude = true;
                                        user = data.getValue(User.class);
                                    }
                                }
                                if (foundTheDude) {
                                    emailError.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Login.this, "A mail containing the account details has been sent to the registered email address.", Toast.LENGTH_LONG).show();

                                    String emailBody = "Hello there! You requested a mail with your account details.\n\n\n\n---------------------\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword() + "\n---------------------\n\n\nThank you for choosing Awaree! We wish you all the best,\nAwareeTeam.";

                                    new Thread(new Runnable() {
                                        public GMailSender sender;

                                        @Override
                                        public void run() {
                                            try {
                                                sender = new GMailSender("awareeFeedback@gmail.com",
                                                        "qwertyuiop345"); //o tinem temporar, facem alta daca publicam aplicatia
                                                sender.sendMail("Awaree Password Recovery", emailBody,
                                                        "awareeFeedback@gmail.com", email.getText().toString());
                                            } catch (Exception e) {
                                                Log.e("SendMail", e.getMessage(), e);
                                            }
                                        }

                                    }).start();
                                }else{
                                    emailError.setText("Email not registered.");
                                    emailError.setVisibility(View.VISIBLE);
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
        loginButton = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);

        emailError = (TextView) findViewById(R.id.emailError);
        passwordError = (TextView) findViewById(R.id.passwordError);
        emailError.setTextColor(getColor(R.color.Burnt_Sienna));
        passwordError.setTextColor(getColor(R.color.Burnt_Sienna));
        emailError.setVisibility(View.INVISIBLE);
        passwordError.setVisibility(View.INVISIBLE);
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
