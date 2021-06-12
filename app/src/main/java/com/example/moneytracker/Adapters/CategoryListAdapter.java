package com.example.moneytracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytracker.Activities.CategoryDetailActivity;
import com.example.moneytracker.Activities.NewCategoryActivity;
import com.example.moneytracker.Activities.NewEntryActivity;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.R;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter {
    public static final String EXTRA_CATEGORY_NAME = "CategoryListAdapter/EXTRA_CATEGORY_NAME";

    private Context context;
    private List<Category> categories;

    public CategoryListAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.categoryName.setText(categories.get(position).getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailActivity.class);
                intent.putExtra(EXTRA_CATEGORY_NAME, categories.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    // View Holder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        ViewHolder(View itemView){
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
