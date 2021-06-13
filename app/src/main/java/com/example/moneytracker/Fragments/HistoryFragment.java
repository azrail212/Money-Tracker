package com.example.moneytracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneytracker.Activities.CategoryDetailActivity;
import com.example.moneytracker.Activities.NewEntryActivity;
import com.example.moneytracker.Adapters.MoneyRecordAdapter;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Helpers.Helper;
import com.example.moneytracker.R;

import java.text.DecimalFormat;
import java.util.Calendar;

public class HistoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MONEY_RECORD_ID = "HistoryFragment/EXTRA_MONEY_RECORD_ID";

    private Context context;
    private Spinner monthPicker;
    private TextView totalIncome, totalExpenses, balance;
    private ListView listView;

    private String[] months = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };
    private int selectedMonth;

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
        View view = inflater.inflate(R.layout.fragment_history,container,false);

        monthPicker = (Spinner) view.findViewById(R.id.month_picker);
        totalIncome = (TextView) view.findViewById(R.id.history_income);
        totalExpenses = (TextView) view.findViewById(R.id.history_expenses);
        balance = (TextView) view.findViewById(R.id.history_balance);
        listView = (ListView) view.findViewById(R.id.history_list);
        setUpMoneyRecordAdapter();


        // Adapter for month picking spinner
        ArrayAdapter<String> monthPickerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, months);
        monthPickerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthPicker.setAdapter(monthPickerAdapter);
        monthPicker.setOnItemSelectedListener(this);

        // Setting selected item to current month
        selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        monthPicker.setSelection(selectedMonth);

        DecimalFormat df = new DecimalFormat("0.00");
        totalIncome.setText(Double.toString(helper.getSelectedIncome(selectedMonth)));
        totalExpenses.setText(Double.toString(helper.getSelectedExpenses(selectedMonth)));
        balance.setText(df.format(helper.getSelectedBalance(selectedMonth)));


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long itemId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(context, NewEntryActivity.class);
                intent.putExtra(EXTRA_MONEY_RECORD_ID, itemId);
                startActivity(intent);
            }
        });*/

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedMonth = position;
        setUpMoneyRecordAdapter();
        DecimalFormat df = new DecimalFormat("0.00");
        totalIncome.setText(Double.toString(helper.getSelectedIncome(selectedMonth)));
        totalExpenses.setText(Double.toString(helper.getSelectedExpenses(selectedMonth)));
        balance.setText(df.format(helper.getSelectedBalance(selectedMonth)));
        Log.i("month", Integer.toString(selectedMonth));
        Log.i("income", Double.toString(helper.getSelectedExpenses(selectedMonth)));
        Log.i("expenses", Double.toString(helper.getSelectedExpenses(selectedMonth)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void setUpMoneyRecordAdapter(){
        MoneyRecordAdapter adapter = new MoneyRecordAdapter(context, helper.getMoneyRecordsForSelectedMonth(selectedMonth));
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpMoneyRecordAdapter();
    }

}
