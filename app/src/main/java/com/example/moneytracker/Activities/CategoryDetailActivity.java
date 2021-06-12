package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moneytracker.Adapters.CategoryDetailAdapter;
import com.example.moneytracker.Adapters.CategoryListAdapter;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "CategoryDetailActivity/EXTRA_CATEGORY_ID";
    public static final String EXTRA_TODO_ID = "CategoryDetailActivity/EXTRA_TODO_ID";

    TextView categoryName;
    ListView listView;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        categoryName = (TextView) findViewById(R.id.category_detail_name);
        listView = (ListView) findViewById(R.id.category_detail_list);
        setUpCategoryDetailAdapter();


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Category category = AppDatabase.getInstance(this).categoryDao()
                    .getCategoryByName(extras.getString(CategoryListAdapter.EXTRA_CATEGORY_NAME));
            categoryName.setText(category.getCategoryName());
            id = category.getId();
        }


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
                intent.putExtra(EXTRA_TODO_ID, itemId);
                startActivity(intent);
            }
        });
    }


    private void setUpCategoryDetailAdapter(){
        List<MoneyRecord> moneyRecords = new ArrayList<>();
        moneyRecords.add(new MoneyRecord("INCOME", "", 50, "Tutoring" ));
        moneyRecords.add(new MoneyRecord("EXPENSE", "Groceries", 10.5, "Groceries" ));
        /*if(categoryName.getText().toString().equals("All"))
            moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAll();
        else if(categoryName.getText().toString().equals("Uncategorized"))
            moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAllFromCategory("");
        else
            moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAllFromCategory(categoryName.getText().toString());*/

        CategoryDetailAdapter adapter = new CategoryDetailAdapter(this, moneyRecords);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpCategoryDetailAdapter();
    }
}