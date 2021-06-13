package com.example.moneytracker.Helpers;

public abstract class CurrentUser {
    private static long id;

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        CurrentUser.id = id;
    }
}
