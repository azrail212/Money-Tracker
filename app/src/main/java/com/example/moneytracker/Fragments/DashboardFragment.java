package com.example.moneytracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneytracker.Activities.NewEntryActivity;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.Helpers.Helper;
import com.example.moneytracker.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {
    private Context context;
    private TextView currentDate, currentIncome, currentExpenses, currentBalance,
                annualIncome, annualExpenses, annualBalance;
    private Button addNewEntry;

    private Helper helper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        helper = new Helper(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);

        currentDate = (TextView) view.findViewById(R.id.current_date);
        currentIncome = (TextView) view.findViewById(R.id.current_income);
        currentExpenses = (TextView) view.findViewById(R.id.current_expenses);
        currentBalance = (TextView) view.findViewById(R.id.current_balance);
        annualIncome = (TextView) view.findViewById(R.id.annual_income);
        annualExpenses = (TextView) view.findViewById(R.id.annual_expenses);
        annualBalance = (TextView) view.findViewById(R.id.annual_balance);

        currentDate.setText(helper.getCurrentDate());
        DecimalFormat df = new DecimalFormat("0.00");
        currentIncome.setText(Double.toString(helper.getCurrentIncome()));
        currentExpenses.setText(Double.toString(helper.getCurrentExpenses()));
        currentBalance.setText(df.format(helper.getCurrentBalance()));
        annualIncome.setText(Double.toString(helper.getAnnualIncome()));
        annualExpenses.setText(Double.toString(helper.getAnnualExpenses()));
        annualBalance.setText(df.format(helper.getAnnualBalance()));

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
