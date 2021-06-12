package com.example.moneytracker.DbAndDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Entities.MoneyRecord;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void  add(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE id=:id LIMIT 1")
    Category getCategoryById(long id);

    @Query("UPDATE categories SET categoryName=:categoryName WHERE id=:id")
    void update(long id, String categoryName);

    @Query("DELETE FROM categories WHERE id=:id")
    void delete(long id);


    @Query("SELECT * FROM categories WHERE categoryName=:categoryName LIMIT 1")
    Category getCategoryByName(String categoryName);
}
