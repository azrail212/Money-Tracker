package com.example.moneytracker.DbAndDao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.moneytracker.Entities.Category;
import com.example.moneytracker.Entities.MoneyRecord;
import com.example.moneytracker.Entities.User;

@Database(entities = {MoneyRecord.class, User.class, Category.class} , version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String dbName = "money_tracker_database";

    public abstract MoneyRecordDao moneyRecordDao();
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbName).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
