package com.example.moneytracker.DbAndDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneytracker.Entities.MoneyRecord;

import java.util.List;

@Dao
public interface MoneyRecordDao {

    @Insert
    void add(MoneyRecord moneyRecord);

    @Query("SELECT * FROM moneyrecords")
    List<MoneyRecord> getAll();

    /*@Query("SELECT * FROM moneyrecords WHERE id=:id LIMIT 1")
    MoneyRecord getMoneyRecordById(long id);*/

    @Query("UPDATE moneyrecords SET category=:category, amount=:amount, description=:description WHERE id=:id")
    void update(String category, double amount, String description, long id);

    @Query("DELETE FROM moneyrecords WHERE username=:username")
    void delete(String username);
}
