package com.example.moneytracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneytracker.Activities.ChangePasswordActivity;
import com.example.moneytracker.Activities.ChangeUsernameActivity;
import com.example.moneytracker.Activities.LoginActivity;
import com.example.moneytracker.Activities.MainActivity;
import com.example.moneytracker.Activities.SignUpActivity;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.DbAndDao.MoneyRecordDao;
import com.example.moneytracker.DbAndDao.UserDao;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.Entities.User;
import com.example.moneytracker.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    private ImageView profilePicture;
    private Button changeProfilePictureButton;
    private Button logoutButton, deleteAccountButton, changePasswordButton, changeUsernameButton;
    private Context context;
    private TextView name, username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        profilePicture = view.findViewById(R.id.profile_picture);
        changeProfilePictureButton = view.findViewById(R.id.change_profile_picture_button);
        logoutButton = view.findViewById(R.id.logout_button);
        deleteAccountButton = view.findViewById(R.id.delete_account_button);
        changePasswordButton = view.findViewById(R.id.change_password_button);
        changeUsernameButton = view.findViewById(R.id.change_username_button);

        username = view.findViewById(R.id.settings_username_textview);
        name = view.findViewById(R.id.settings_name_textview);

        username.append(LoginActivity.username.getText().toString());

        logoutButton.setOnClickListener(v->{
            AppDatabase appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());

            UserDao userDao = appDatabase.userDao();
                    new Thread(() -> {
                        userDao.logoutUser(false, getId());

                    }).start();
            Intent logoutIntent = new Intent(context, LoginActivity.class);
            startActivity(logoutIntent);
            Toast.makeText(getActivity(),
                    "Logged out succefully. ", Toast.LENGTH_LONG).show();

        } );

        changeProfilePictureButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        deleteAccountButton.setOnClickListener(v-> {
            AppDatabase appDatabase = AppDatabase.getInstance((getActivity().getApplicationContext()));
            UserDao userDao = appDatabase.userDao();
            MoneyRecordDao moneyRecordDao = appDatabase.moneyRecordDao();

            new Thread(() -> {
                userDao.delete(LoginActivity.username.getText().toString());

            }).start();

            Intent deleteIntent = new Intent(SettingsFragment.this.getActivity().getApplicationContext(), SignUpActivity.class);
            startActivity(deleteIntent);
            Toast.makeText(getActivity(),
                    "Account deleted succesfully.", Toast.LENGTH_LONG).show();

        });

        changePasswordButton.setOnClickListener(v->{
            Intent changePasswordIntent = new Intent(SettingsFragment.this.getActivity().getApplicationContext(), ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
                });

        changeUsernameButton.setOnClickListener(v->{
            Intent changeUsernameIntent = new Intent(SettingsFragment.this.getActivity().getApplicationContext(), ChangeUsernameActivity.class);
            startActivity(changeUsernameIntent);
        });

            return view;
    }
}

