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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.name_signup_input);
        username = (EditText) findViewById(R.id.username_signup_input);
        password = (EditText) findViewById(R.id.password_signup_input);

    }

    public void onCreateAccountClick(View view) {
            //Intitialize user entity
            User user = new User();

            user.setName(name.getText().toString());
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());

            if (validateInput(user) == 0 ){ //0 code means all ok, -1 means problem
                //Initialize database
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                UserDao userDao = appDatabase.userDao();
                if (userDao.getUserByUserName(username.getText().toString()) == null) {
                    new Thread(() -> {
                        //call the register aka. add method
                        userDao.add(user);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Signup Successful! ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).start();

                    Intent signUpReturnUserToLoginIntent = new Intent(this, LoginActivity.class);
                    startActivity(signUpReturnUserToLoginIntent);

                    Toast toast = Toast.makeText(getApplicationContext(), "Signup Successful!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Toast.makeText(getApplicationContext(),
                            "A user with username "+ username.getText().toString()+ " already exists on this phone. ",
                            Toast.LENGTH_LONG).show();
                }
            }
    }


    public void onCancelSignupClick(View view) {
            Intent goBackToLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(goBackToLoginIntent);
    }

    private int validateInput(User user){
        if (user.getName().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please input your name. ",
                    Toast.LENGTH_SHORT).show();
            return -1;
        } else if (user.getUsername().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please input valid username. ",
                    Toast.LENGTH_SHORT).show();
            return -1;
        } else if (user.getPassword().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please input a password. ",
                    Toast.LENGTH_SHORT).show();
            return -1;
        }else if (!isValidPassword(user.getPassword())) {
            Toast.makeText(getApplicationContext(),
                    "Password too simple. Must contain a minimum of 8 characters, at least one letter or number. ",
                    Toast.LENGTH_LONG).show();
            return -1;
        }else return 0;  // 0 stands for OK
    }



    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}$"; //min 8 char, 1 letter, 1 number
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}