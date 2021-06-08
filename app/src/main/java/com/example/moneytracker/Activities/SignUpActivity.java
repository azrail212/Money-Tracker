package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytracker.Entities.User;
import com.example.moneytracker.R;

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
            if (password.getText().toString().length() < 8 && !isValidPassword(password.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "Password too simple! Make sure you use a character, lowcase letter, capital letter and a number", Toast.LENGTH_LONG);
                toast.show();
            } else user.setPassword(password.getText().toString());

            Intent signUpIntent = new Intent(this, MainActivity.class);
            startActivity(signUpIntent);
            Toast toast = Toast.makeText(getApplicationContext(), "Signup Successful! ", Toast.LENGTH_SHORT);
            toast.show();
    }


    public void onCancelSignupClick(View view) {
            Intent goBackToLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(goBackToLoginIntent);
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}