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
import com.example.moneytracker.Helpers.CurrentUser;
import com.example.moneytracker.R;

public class ChangeNameActivity extends AppCompatActivity {

    private EditText newName, password;
    private TextView currentName;
    private Button cancelButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        currentName = findViewById(R.id.current_name_textview);
        newName = findViewById(R.id.new_name_input);
        password = findViewById(R.id.new_name_password_confirmation_input);
        currentName.setText("Your current name is: " +
                AppDatabase.getInstance(this).userDao().getUserById(CurrentUser.getId()).getName());
    }

    public void onSave(View view) {

        String newNameText = newName.getText().toString();
        String passwordText = password.getText().toString();
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

        UserDao userDao = appDatabase.userDao();

        User user = userDao.getUserById(CurrentUser.getId());

        if (!(passwordText.equals(user.getPassword()))) {
            Toast.makeText(getApplicationContext(),
                    "The password is invalid. ", Toast.LENGTH_LONG).show();
        }else{
            AppDatabase.getInstance(this).userDao().changeName(newNameText, user.getId());
            Toast.makeText(getApplicationContext(),
                    "Your new name "+newNameText +" is saved successfully. ", Toast.LENGTH_LONG).show();
            Intent saveNewNameIntent = new Intent(ChangeNameActivity.this, MainActivity.class);
            startActivity(saveNewNameIntent);
        }
    }

    public void onCancel(View view){
        finish();
    }
}
