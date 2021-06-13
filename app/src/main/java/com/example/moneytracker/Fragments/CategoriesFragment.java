package com.example.moneytracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytracker.Activities.NewCategoryActivity;
import com.example.moneytracker.Adapters.CategoryListAdapter;
import com.example.moneytracker.DbAndDao.AppDatabase;
import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private Button addNewCategory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

  
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories,container,false);

        addNewCategory = (Button) view.findViewById(R.id.add_category_button);
        recyclerView = (RecyclerView) view.findViewById(R.id.category_list_container);
        setUpCategoryListAdapter();

        int numberOfColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, numberOfColumns){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = getWidth() / getSpanCount();
                lp.height = getHeight() / 4;
                return  true;
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));


        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewCategoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void setUpCategoryListAdapter(){
        List<Category> categories = AppDatabase.getInstance(context).categoryDao().getAll();
        CategoryListAdapter adapter = new CategoryListAdapter(context, categories);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpCategoryListAdapter();
    }
}
