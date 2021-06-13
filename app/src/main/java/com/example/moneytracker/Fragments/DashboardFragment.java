package com.example.moneytracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TextView currentDate, currrenIncome, currentExpenses, currentBalance,
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
        currrenIncome = (TextView) view.findViewById(R.id.current_income);
        currentExpenses = (TextView) view.findViewById(R.id.current_expenses);
        currentBalance = (TextView) view.findViewById(R.id.current_balance);
        annualIncome = (TextView) view.findViewById(R.id.annual_income);
        annualExpenses = (TextView) view.findViewById(R.id.annual_expenses);
        annualBalance = (TextView) view.findViewById(R.id.annual_balance);

        currentDate.setText(helper.getCurrentDate());
        DecimalFormat df = new DecimalFormat("0.00");
        currrenIncome.setText(Double.toString(helper.getCurrentIncome()));
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


   /* public String getCurrentDate(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MONTH, date.get(Calendar.MONTH));
        return date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + date.get(Calendar.YEAR);
    }


    public double getCurrentIncome(){
        List<MoneyRecord> moneyRecords = getMoneyRecordsForCurrentMonth();
        return getIncomeSum(moneyRecords);
    }

    public double getCurrentExpenses(){
        List<MoneyRecord> moneyRecords = getMoneyRecordsForCurrentMonth();
        return getExpensesSum(moneyRecords);
    }

    public double getCurrentBalance(){
        return getCurrentIncome() - getCurrentExpenses();
    }


    public double getAnnualIncome(){
        List<MoneyRecord> moneyRecords = AppDatabase.getInstance(context).moneyRecordDao().getAll();
        return getIncomeSum(moneyRecords);
    }

    public double getAnnualExpenses(){
        List<MoneyRecord> moneyRecords = AppDatabase.getInstance(context).moneyRecordDao().getAll();
        return getExpensesSum(moneyRecords);
    }

    public double getAnnualBalance(){
        return getAnnualIncome() - getAnnualExpenses();
    }


    public List<MoneyRecord> getMoneyRecordsForCurrentMonth(){
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.set(Calendar.MONTH, date1.get(Calendar.MONTH) + 1);
        date1.set(Calendar.DAY_OF_MONTH, 1);

        date2.set(Calendar.MONTH, date2.get(Calendar.MONTH) + 1);
        date2.set(Calendar.DAY_OF_MONTH, date2.getActualMaximum(Calendar.DATE));

        List<MoneyRecord> moneyRecords = AppDatabase.getInstance(context).moneyRecordDao().getAllFromSpecificPeriod(date1, date2);
        return moneyRecords;
    }

    public double getIncomeSum(List<MoneyRecord> moneyRecords){
        double income = 0;
        for(MoneyRecord mr : moneyRecords) {
            if(mr.getType().equals("Income"))
                income += mr.getAmount();
        }
        return income;
    }

    public double getExpensesSum(List<MoneyRecord> moneyRecords){
        double expenses = 0;
        for(MoneyRecord mr : moneyRecords){
            if(mr.getType().equals("Expense"))
                expenses += mr.getAmount();
        }
        return expenses;
    }*/
}
