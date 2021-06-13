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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneytracker.Activities.LoginActivity;
import com.example.moneytracker.Activities.MainActivity;
import com.example.moneytracker.Activities.SignUpActivity;
import com.example.moneytracker.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    private ImageView profilePicture;
    private Button changeProfilePictureButton;
    private Button logoutButton;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        profilePicture = view.findViewById(R.id.profile_picture);
        changeProfilePictureButton = view.findViewById(R.id.change_profile_picture_button);
        logoutButton = view.findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(v->{
            SharedPreferences myPrefs =  context.getSharedPreferences("MY",
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
            MainActivity.AppState.getSingleInstance().setLoggingOut(true);
            Log.d(TAG, "Now log out and start the activity login");
            Intent intent = new Intent(context,
                    LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } );
        changeProfilePictureButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        );
        return view;
    }
}

