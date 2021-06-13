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
import com.example.moneytracker.Fragments.SettingsFragment;
import com.example.moneytracker.R;

import static com.example.moneytracker.Activities.SignUpActivity.isValidPassword;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPassword, newPassword;
    private Button cancelButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPassword = findViewById(R.id.current_password_input);
        newPassword = findViewById(R.id.new_password_input);
    }

    public void onSave(View view) {
        String currentPasswordText = currentPassword.getText().toString();
        String newPasswordText = newPassword.getText().toString();
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        UserDao userDao = appDatabase.userDao();

        User user = userDao.getUserByUserName(LoginActivity.username.toString());

        if(!(currentPasswordText.equals(user.getPassword()))){
            Toast.makeText(getApplicationContext(),
                    "Invalid password.", Toast.LENGTH_SHORT).show();
            currentPassword.getText().clear();
        }else if(!(isValidPassword(newPasswordText))){
            Toast.makeText(getApplicationContext(),
                    "New password must be longer than 8 characters and contain at least 1 letter " +
                            "and 1 number.",
                    Toast.LENGTH_LONG).show();
            newPassword.getText().clear();
        }else {
            AppDatabase.getInstance(this).userDao().changePassword(newPasswordText, user.getUsername());

            Intent saveNewPasswordIntent = new Intent(ChangePasswordActivity.this, SettingsFragment.class);
            startActivity(saveNewPasswordIntent);
            finish();
        }

    }

    public void onCancel(View view){
        finish();
    }
}