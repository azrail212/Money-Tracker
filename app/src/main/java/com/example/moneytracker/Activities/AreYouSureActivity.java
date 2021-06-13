package com.example.moneytracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.DbAndDao.MoneyRecordDao;
import com.example.moneytracker.DbAndDao.UserDao;
import com.example.moneytracker.Entities.User;
import com.example.moneytracker.Helpers.CurrentUser;
import com.example.moneytracker.R;

public class AreYouSureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_you_sure);
    }

    public void onYesClickDeleteAccount(View view){
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        UserDao userDao = appDatabase.userDao();
        MoneyRecordDao moneyRecordDao = appDatabase.moneyRecordDao();
        User user = userDao.getUserById(CurrentUser.getId());
        new Thread(() -> {
            userDao.delete(user.getUsername());
            moneyRecordDao.deleteAll();
        }).start();

        Intent deleteIntent = new Intent(AreYouSureActivity.this, SignUpActivity.class);
        startActivity(deleteIntent);
        Toast.makeText(getApplicationContext(),
                "Account deleted succesfully.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onCancelDeleteAccountClick(View view){
        Intent cancelDeleteAccountIntent = new Intent(AreYouSureActivity.this, MainActivity.class);
        startActivity(cancelDeleteAccountIntent);
        Toast.makeText(getApplicationContext(),
                "Account deletion cancelled.", Toast.LENGTH_LONG).show();
    }
}