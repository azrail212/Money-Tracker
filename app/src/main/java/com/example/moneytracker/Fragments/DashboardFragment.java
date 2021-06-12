package com.example.moneytracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneytracker.Activities.NewEntryActivity;
import com.example.moneytracker.R;

public class DashboardFragment extends Fragment {
    private Button addNewEntry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);

        addNewEntry = (Button) view.findViewById(R.id.add_new_entry_button);
        addNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewEntryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
