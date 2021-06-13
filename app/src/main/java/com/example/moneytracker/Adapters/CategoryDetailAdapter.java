package com.example.moneytracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytracker.Activities.NewEntryActivity;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.R;

import java.util.Calendar;
import java.util.List;

public class CategoryDetailAdapter extends BaseAdapter {
    private Context context;
    private List<MoneyRecord> moneyRecords;

    public CategoryDetailAdapter(Context context, List<MoneyRecord> moneyRecords) {
        this.context = context;
        this.moneyRecords = moneyRecords;
    }


    @Override
    public int getCount() { return moneyRecords.size(); }

    @Override
    public Object getItem(int position) { return moneyRecords.get(position); }

    @Override
    public long getItemId(int position) { return moneyRecords.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.money_record_item, parent, false);

        MoneyRecord moneyRecord = moneyRecords.get(position);
        TextView date = convertView.findViewById(R.id.money_record_date);
        TextView type = convertView.findViewById(R.id.money_record_type);
        TextView amount = convertView.findViewById(R.id.money_record_amount);
        TextView category = convertView.findViewById(R.id.money_record_category);
        TextView description = convertView.findViewById(R.id.money_record_description);

        Calendar c = moneyRecord.getDate();
        date.setText(Integer.toString(c.get(Calendar.DAY_OF_MONTH))+"."+
                        Integer.toString(c.get(Calendar.MONTH))+"."+
                                Integer.toString(c.get(Calendar.YEAR)));
        type.setText(moneyRecord.getType());
        amount.setText(Double.toString(moneyRecord.getAmount()));
        category.setText(moneyRecord.getCategory());
        description.setText(moneyRecord.getDescription());

        return convertView;
    }
}
