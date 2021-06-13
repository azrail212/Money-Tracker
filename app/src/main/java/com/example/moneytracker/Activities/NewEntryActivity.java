package com.example.moneytracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.Fragments.DatePickerFragment;
import com.example.moneytracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewEntryActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener
{
    EditText dateInput, amountInput, descriptionInput;
    Spinner typeInput, categoryInput;
    Calendar date = Calendar.getInstance();
    String type = "";
    String category = "";
    Button deleteButton;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        dateInput = (EditText) findViewById(R.id.date_input);
        dateInput.setInputType(InputType.TYPE_NULL);
        amountInput = (EditText) findViewById(R.id.amount_input);
        descriptionInput = (EditText) findViewById(R.id.description_input);
        typeInput = (Spinner) findViewById(R.id.type_input);
        categoryInput = (Spinner) findViewById(R.id.category_input);
        deleteButton = (Button) findViewById(R.id.delete_entry_button);


        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });


        // Adapter for type spinner
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getTypes()){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                if(position == 0) return false;
                else return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                // Set the hint text color gray
                if(position == 0)tv.setTextColor(Color.GRAY);
                else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeInput.setAdapter(typeAdapter);
        typeInput.setOnItemSelectedListener(this);


        // Adapter for category spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getCategories()){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                if(position == 0) return false;
                else return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                // Set the hint text color gray
                if(position == 0)tv.setTextColor(Color.GRAY);
                else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryInput.setAdapter(categoryAdapter);
        categoryInput.setOnItemSelectedListener(this);


        // This part has to go after adapters in order to update selected item in spinners
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getLong(CategoryDetailActivity.EXTRA_MONEY_RECORD_ID);
            MoneyRecord moneyRecord = AppDatabase.getInstance(this).moneyRecordDao().getMoneyRecordById(id);
            date = moneyRecord.getDate();
            dateInput.setText(Integer.toString(date.get(Calendar.DAY_OF_MONTH))+"."+
                    Integer.toString(date.get(Calendar.MONTH))+"."+
                    Integer.toString(date.get(Calendar.YEAR)));
            amountInput.setText(Double.toString(moneyRecord.getAmount()));
            descriptionInput.setText(moneyRecord.getDescription());
            typeInput.setSelection(getTypes().indexOf(moneyRecord.getType()));
            categoryInput.setSelection(getCategories().indexOf(moneyRecord.getCategory()));
        }
        else
            deleteButton.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date.set(year, (month + 1), dayOfMonth);
        dateInput.setText(dayOfMonth + "." + (month+1) + "." + year);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
        if(parent.getId() == R.id.type_input){
            if(position == 0) type = "";
            else type = getTypes().get(position);
        }
        if(parent.getId() == R.id.category_input){
            if(position == 0) category = "";
            else category = getCategories().get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if(parent.getId() == R.id.type_input)
            Toast.makeText(this, "You must choose type", Toast.LENGTH_SHORT).show();
    }


    public List<String> getTypes(){
        List<String> types = new ArrayList<>();
        types.add("Type");
        for (String t : MainActivity.TYPES)
            types.add(t);
        return types;
    }

    public List<String> getCategories(){
        List<Category> pickableCategories = AppDatabase.getInstance(this).categoryDao().getPickableCategories();
        List<String> categories = new ArrayList<>();
        categories.add("Category");
        for(Category c : pickableCategories)
            categories.add(c.getCategoryName());
        return  categories;
    }


    public void onCancel(View view){
        finish();
    }


    public void onSave(View view){
        if(validateInput() == 0){
            if(id != 0){
                AppDatabase.getInstance(this).moneyRecordDao().update(id, date, type, category,
                        Double.parseDouble(amountInput.getText().toString()), descriptionInput.getText().toString());
            }
            else {
                MoneyRecord moneyRecord = new MoneyRecord(type, date, category,
                        Double.parseDouble(amountInput.getText().toString()), descriptionInput.getText().toString());
                AppDatabase.getInstance(this).moneyRecordDao().add(moneyRecord);
            }
            finish();
        }
        else {
            Toast.makeText(this, "You must enter date, type and amount.", Toast.LENGTH_LONG).show();
        }

    }


    public void onDelete(View view){
        if(id != 0)
            AppDatabase.getInstance(this).moneyRecordDao().delete(id);
        finish();
    }


    private int validateInput(){
        if(date == null){
            Toast.makeText(this, "Please enter date.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else if(type == ""){
            Toast.makeText(this, "Please choose type.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else if(amountInput.getText().toString() == "" || amountInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter amount.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else return 0;
    }
}