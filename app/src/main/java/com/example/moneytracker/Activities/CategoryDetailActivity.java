package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moneytracker.Adapters.MoneyRecordAdapter;
import com.example.moneytracker.Adapters.CategoryListAdapter;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "CategoryDetailActivity/EXTRA_CATEGORY_ID";
    public static final String EXTRA_MONEY_RECORD_ID = "CategoryDetailActivity/EXTRA_MONEY_RECORD_ID";

    private TextView categoryName, totalIncome, totalExpenses, balance;
    private ListView listView;
    private long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        categoryName = (TextView) findViewById(R.id.category_detail_name);
        totalIncome = (TextView) findViewById(R.id.category_detail_income);
        totalExpenses = (TextView) findViewById(R.id.category_detail_expenses);
        balance = (TextView) findViewById(R.id.category_detail_balance);
        listView = (ListView) findViewById(R.id.category_detail_list);
        setUpMoneyRecordAdapter();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Category category = AppDatabase.getInstance(this).categoryDao()
                    .getCategoryByName(extras.getString(CategoryListAdapter.EXTRA_CATEGORY_NAME));
            categoryName.setText(category.getCategoryName());
            id = category.getId();
        }

        totalIncome.setText(Double.toString(getTotalIncome()));
        totalExpenses.setText(Double.toString(getTotalExpenses()));
        DecimalFormat df = new DecimalFormat("0.00");
        balance.setText(df.format(getBalance()));


        categoryName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(categoryName.getText().toString().equals("All") || categoryName.getText().toString().equals("Uncategorized"))
                    return true;

                Intent intent = new Intent(CategoryDetailActivity.this, NewCategoryActivity.class);
                intent.putExtra(EXTRA_CATEGORY_ID, id);
                startActivity(intent);
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long itemId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(CategoryDetailActivity.this, NewEntryActivity.class);
                intent.putExtra(EXTRA_MONEY_RECORD_ID, itemId);
                startActivity(intent);
            }
        });
    }


    private void setUpMoneyRecordAdapter(){
        MoneyRecordAdapter adapter = new MoneyRecordAdapter(this, getMoneyRecordList());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }


    public  List<MoneyRecord> getMoneyRecordList() {
        List<MoneyRecord> moneyRecords = new ArrayList<>();
        if(categoryName.getText().toString().equals("All"))
            moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAll();
        else if(categoryName.getText().toString().equals("Uncategorized"))
            moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAllFromCategory("");
        else
            moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAllFromCategory(categoryName.getText().toString());

        return moneyRecords;
    }


    @Override
    public void onResume() {
        super.onResume();
        Category category = AppDatabase.getInstance(this).categoryDao().getCategoryById(id);
        categoryName.setText(category.getCategoryName());
        setUpMoneyRecordAdapter();
    }


    public double getTotalIncome(){
        double income = 0;
        List<MoneyRecord> moneyRecords = getMoneyRecordList();
        for(MoneyRecord mr : moneyRecords){
            if(mr.getType().equals("Income"))
                income += mr.getAmount();
        }
        return income;
    }

    public double getTotalExpenses(){
        double expenses = 0;
        List<MoneyRecord> moneyRecords = getMoneyRecordList();
        for(MoneyRecord mr : moneyRecords){
            if(mr.getType().equals("Expense"))
                expenses += mr.getAmount();
        }
        return expenses;
    }

    public double getBalance(){
        return getTotalIncome() - getTotalExpenses();
    }
}