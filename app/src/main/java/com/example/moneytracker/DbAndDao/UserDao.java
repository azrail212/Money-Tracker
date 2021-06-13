package com.example.moneytracker.DbAndDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneytracker.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void add(User user);

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id=:id LIMIT 1")
    User getUserById(long id);

    @Query("SELECT * FROM users WHERE username=:username AND password = :password")
    User loginUser(String username, String password);

    @Query("UPDATE users SET isLoggedIn = :isLoggedIn WHERE id=:id")
    void logoutUser(boolean isLoggedIn, long id);

    @Query("SELECT * FROM users WHERE username=:username LIMIT 1")
    User getUserByUserName(String username);

    @Query("UPDATE users SET name=:name, username=:username, password=:password WHERE id=:id")
    void update(long id, String name, String username, String password);

    @Query("UPDATE users SET password=:password WHERE username=:username")
    void changePassword(String password, String username);

    @Query("UPDATE users SET username=:newUsername WHERE username=:username")
    void changeUsername(String newUsername, String username);

    @Query("DELETE FROM users WHERE username=:username")
    void delete(String username);
}

