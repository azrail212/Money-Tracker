package com.example.moneytracker.Helpers;

import android.content.Context;

import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.MoneyRecord;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Helper {
    private Context context;

    public Helper(Context context) {
        this.context = context;
    }


    // Get current date
    public String getCurrentDate(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MONTH, date.get(Calendar.MONTH));
        return date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + date.get(Calendar.YEAR);
    }


    // Get income, expenses and balance for current month
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


    // Get income, expenses and balance for selected month
    public double getSelectedIncome(int month){
        List<MoneyRecord> moneyRecords = getMoneyRecordsForSelectedMonth(month);
        return getIncomeSum(moneyRecords);
    }

    public double getSelectedExpenses(int month){
        List<MoneyRecord> moneyRecords = getMoneyRecordsForSelectedMonth(month);
        return getExpensesSum(moneyRecords);
    }

    public double getSelectedBalance(int month){
        return getSelectedIncome(month) - getSelectedExpenses(month);
    }


    // Get income, expenses and balance for year
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


    // Get list of money records for current month
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


    // Get list of money records for selected month
    public List<MoneyRecord> getMoneyRecordsForSelectedMonth(int month){
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.set(Calendar.MONTH, (month + 1));
        date1.set(Calendar.DAY_OF_MONTH, 1);

        date2.set(Calendar.MONTH, (month + 1));
        date2.set(Calendar.DAY_OF_MONTH, date2.getActualMaximum(Calendar.DATE));

        List<MoneyRecord> moneyRecords = AppDatabase.getInstance(context).moneyRecordDao().getAllFromSpecificPeriod(date1, date2);
        return moneyRecords;
    }


    // Get income sum
    public double getIncomeSum(List<MoneyRecord> moneyRecords){
        double income = 0;
        for(MoneyRecord mr : moneyRecords) {
            if(mr.getType().equals("Income"))
                income += mr.getAmount();
        }
        return income;
    }


    // Get expenses sum
    public double getExpensesSum(List<MoneyRecord> moneyRecords){
        double expenses = 0;
        for(MoneyRecord mr : moneyRecords){
            if(mr.getType().equals("Expense"))
                expenses += mr.getAmount();
        }
        return expenses;
    }
}
