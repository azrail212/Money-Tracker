package com.example.moneytracker.DbAndDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneytracker.Entities.MoneyRecord;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Dao
public interface MoneyRecordDao {

    @Insert
    void add(MoneyRecord moneyRecord);

    @Query("SELECT * FROM moneyrecords")
    List<MoneyRecord> getAll();

    @Query("SELECT * FROM moneyrecords WHERE id=:id LIMIT 1")
    MoneyRecord getMoneyRecordById(long id);

    @Query("UPDATE moneyrecords SET date=:date, type=:type, category=:category, amount=:amount, description=:description WHERE id=:id")
    void update(long id, Calendar date, String type, String category, double amount, String description);

    @Query("DELETE FROM moneyrecords WHERE id=:id")
    void delete(long id);

    @Query("SELECT * FROM moneyrecords WHERE category=:category")
    List<MoneyRecord> getAllFromCategory(String category);

    @Query("SELECT * FROM moneyrecords WHERE date BETWEEN :date1 AND :date2")
    List<MoneyRecord> getAllFromSpecificPeriod(Calendar date1, Calendar date2);

    @Query("DELETE FROM moneyrecords")
    void deleteAll();
}
