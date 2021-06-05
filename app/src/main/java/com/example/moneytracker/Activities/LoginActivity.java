package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moneytracker.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onLogin(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onSignUp(View view){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}