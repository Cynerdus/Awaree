package com.awareeTeam.awaree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

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
        configureLoginButton();



    }

    private void configureLoginButton(){


        Button loginButton = (Button) findViewById(R.id.login);
        EditText email = (EditText) findViewById(R.id.loginEmail);
        EditText password = (EditText) findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                Toast.makeText(Login.this, "Hello " + emailString, Toast.LENGTH_LONG).show();
                if (true){ // TODO implement error if login incorrect
                    startActivity(new Intent(Login.this, QuestionsActivity.class));
                    finish();
                }else{
                    Toast.makeText(Login.this, "password incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
