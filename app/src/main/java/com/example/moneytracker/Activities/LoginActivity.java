package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.DbAndDao.UserDao;
import com.example.moneytracker.Entities.User;
import com.example.moneytracker.R;

public class LoginActivity extends AppCompatActivity {

    public static EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
    }

    public void onLoginButtonClick(View view){

        String userNameText= username.getText().toString();
        String passwordText= password.getText().toString();

        if (userNameText.isEmpty() || passwordText.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Please fill all fields. ", Toast.LENGTH_SHORT).show();
        }else{
            AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
            UserDao userDao = appDatabase.userDao();
            new Thread(() -> {
                User user = userDao.loginUser(userNameText, passwordText);
                if (user == null) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                            "Invalid credentials.", Toast.LENGTH_SHORT).show());
                }else{
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }).start();
        }
        }

    private int validateInput(User user){
        String userNameText= username.getText().toString();
        String passwordText= password.getText().toString();

        if (userNameText.isEmpty() || passwordText.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Please fill all fields. ", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public void onSignUpButtonClick(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}