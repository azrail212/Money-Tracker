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
    private Button signup, signupCancel;
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

            if (validateInput(user)){
                //Initialize database
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                UserDao userDao = appDatabase.userDao();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                    }
                }).start();

                Intent signUpIntent = new Intent(this, MainActivity.class);
                startActivity(signUpIntent);
                Toast toast = Toast.makeText(getApplicationContext(), "Signup Successful!", Toast.LENGTH_SHORT);
                toast.show();

            }else {
                Toast.makeText(getApplicationContext(),
                        "Please make sure that you filled out " +
                                "all the fields accordingly. " +
                                "The password must be longer than 8 digits.",
                        Toast.LENGTH_LONG).show();
                resetInput();
                password.setpri
            }
    }


    public void onCancelSignupClick(View view) {
            Intent goBackToLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(goBackToLoginIntent);
    }

    private Boolean validateInput(User user){
        if (user.getName().isEmpty() || user.getUsername().isEmpty()
            || user.getPassword().length()<8 || user.getPassword().isEmpty()){
            return false;
        } return true;
    }

    private void resetInput(){
        name.getText().clear();
        username.getText().clear();
        password.getText().clear();
    }


    /* public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }*/
}