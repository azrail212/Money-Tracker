package com.example.moneytracker.DbAndDao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.Entities.User;

@Database(entities = {MoneyRecord.class, User.class} , version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MoneyRecordDao moneyRecordDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "money_tracker_database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
