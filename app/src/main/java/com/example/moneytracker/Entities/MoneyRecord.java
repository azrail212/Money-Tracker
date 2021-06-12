package com.example.moneytracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "moneyrecords")
public class MoneyRecord {

    @PrimaryKey (autoGenerate = true)
    private long id;
    private String type;
    //private Date date;
    private String category;
    private double amount;
    private String description;

    public MoneyRecord(String type, /*Date date,*/ String category, double amount, String description) {
        this.type = type;
        //this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/

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
