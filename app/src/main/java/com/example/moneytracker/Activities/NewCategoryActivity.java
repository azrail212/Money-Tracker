package com.example.moneytracker.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytracker.Adapters.CategoryListAdapter;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.Fragments.CategoriesFragment;
import com.example.moneytracker.R;

import java.util.List;
import java.util.Objects;

public class NewCategoryActivity extends AppCompatActivity {
    private EditText categoryNameInput;
    private Button deleteButton, saveButton;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        categoryNameInput = findViewById(R.id.new_category_input);
        deleteButton = findViewById(R.id.delete_category_button);
        saveButton = findViewById(R.id.save_new_category_button);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Category category = AppDatabase.getInstance(this).categoryDao()
                                .getCategoryById(extras.getLong(CategoryDetailActivity.EXTRA_CATEGORY_ID));
            categoryNameInput.setText(category.getCategoryName());
            id = category.getId();
        }
        else
            deleteButton.setVisibility(View.GONE);
    }


    public void onCancel(View view){
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSave(View view){
        if(id != 0){
            String oldName = AppDatabase.getInstance(this).categoryDao().getCategoryById(id).getCategoryName();
            AppDatabase.getInstance(this).categoryDao().update(id, categoryNameInput.getText().toString());
            List<MoneyRecord> moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAllFromCategory(oldName);
            for (MoneyRecord mr : moneyRecords){
                mr.setCategory(categoryNameInput.getText().toString());
                AppDatabase.getInstance(this).moneyRecordDao().update(mr.getId(), mr.getDate(), mr.getType(),
                        mr.getCategory(), mr.getAmount(), mr.getDescription());
            }
        }

        else{
            if(Objects.nonNull(AppDatabase.getInstance(this).categoryDao().getCategoryByName(categoryNameInput.getText().toString()))){
                Toast.makeText(getApplicationContext(),
                        "A category with name " + categoryNameInput.getText().toString() +
                                " already exists.", Toast.LENGTH_LONG).show();
            }
            else {
                Category category = new Category(categoryNameInput.getText().toString());
                AppDatabase.getInstance(this).categoryDao().add(category);
            }
        }

        finish();
    }


    public void onDelete(View view){
        if(id != 0){
            List<MoneyRecord> moneyRecords = AppDatabase.getInstance(this).moneyRecordDao().getAllFromCategory(categoryNameInput.getText().toString());
            for (MoneyRecord mr : moneyRecords){
                mr.setCategory("");
                AppDatabase.getInstance(this).moneyRecordDao().update(mr.getId(), mr.getDate(), mr.getType(),
                                                                        mr.getCategory(), mr.getAmount(), mr.getDescription());
            }

            AppDatabase.getInstance(this).categoryDao().delete(id);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}