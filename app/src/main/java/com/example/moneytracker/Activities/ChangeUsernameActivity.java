package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.DbAndDao.UserDao;
import com.example.moneytracker.Entities.User;
import com.example.moneytracker.Fragments.SettingsFragment;
import com.example.moneytracker.R;

public class ChangeUsernameActivity extends AppCompatActivity {

    private EditText newUsername, password;
    private TextView currentUsername;
    private Button cancelButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        currentUsername = findViewById(R.id.current_username_textview);
        newUsername = findViewById(R.id.new_username_input);
        password = findViewById(R.id.new_username_password_confirmation_input);
        currentUsername.append(LoginActivity.username.getText().toString());
    }

    public void onSave(View view) {

        String newUsernameText = newUsername.getText().toString();
        String passwordText = password.getText().toString();
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

        UserDao userDao = appDatabase.userDao();

        User user = userDao.getUserByUserName(LoginActivity.username.getText().toString());

        if(userDao.getUserByUserName(newUsernameText)!=null){
            Toast.makeText(getApplicationContext(),
                    "The username you want to choose already exists. ", Toast.LENGTH_LONG).show();
        }else if (!(passwordText.equals(user.getPassword()))) {
            Toast.makeText(getApplicationContext(),
                    "The username you want to choose already exists. ", Toast.LENGTH_LONG).show();
        }else{
            AppDatabase.getInstance(this).userDao().changeUsername(newUsernameText, user.getUsername());
            Toast.makeText(getApplicationContext(),
                    "Your new username "+newUsernameText +" is saved succesfully. ", Toast.LENGTH_LONG).show();
            Intent saveNewUsernameIntent = new Intent(ChangeUsernameActivity.this, SettingsFragment.class);
            startActivity(saveNewUsernameIntent);
        }
    }

    public void onCancel(View view){
        finish();
    }
}
