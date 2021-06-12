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

import java.util.List;

public class CategoryDetailAdapter extends BaseAdapter {
    private Context context;
    private List<MoneyRecord> moneyRecords;

    public CategoryDetailAdapter(Context context, List<MoneyRecord> moneyRecords) {
        this.context = context;
        this.moneyRecords = moneyRecords;
    }

   /* @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_record_item, parent, false);
        return new ViewHolder(view);
    }*/
/*
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        //viewHolder.date.setText(moneyRecords.get(position).getDate());
        viewHolder.type.setText(moneyRecords.get(position).getType());
        viewHolder.amount.setText(Double.toString(moneyRecords.get(position).getAmount()));
        viewHolder.category.setText(moneyRecords.get(position).getCategory());
        viewHolder.description.setText(moneyRecords.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewEntryActivity.class);
                context.startActivity(intent);
            }
        });
    }*/


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

        //date.setText(moneyRecord.getDate());
        type.setText(moneyRecord.getType());
        amount.setText(Double.toString(moneyRecord.getAmount()));
        category.setText(moneyRecord.getCategory());
        description.setText(moneyRecord.getDescription());

        return convertView;
    }

/*
    // View Holder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, type, amount, category, description;

        ViewHolder(View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.money_record_date);
            type = itemView.findViewById(R.id.money_record_type);
            amount = itemView.findViewById(R.id.money_record_amount);
            category = itemView.findViewById(R.id.money_record_category);
            description = itemView.findViewById(R.id.money_record_description);
        }
    }*/
}
