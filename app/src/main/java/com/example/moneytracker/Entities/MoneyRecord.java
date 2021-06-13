package com.example.moneytracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "moneyrecords")
public class MoneyRecord {

    @PrimaryKey (autoGenerate = true)
    private long id;

    private String category;
    private double amount;
    private String description;
    private String username;

    public MoneyRecord(String category, Double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
