package com.example.kenkina.heroapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.androidnetworking.AndroidNetworking;
import com.example.kenkina.heroapp.database.AppRoomDatabase;
import com.example.kenkina.heroapp.utils.Constants;

public class HeroApplication extends Application {

    private final String SP_HERO_APP = "sp_hero_app";

    private static HeroApplication INSTANCE;

    private AppRoomDatabase database;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        database = Room.databaseBuilder(getApplicationContext(),
                AppRoomDatabase.class,
                AppRoomDatabase.DB_HERO_APP)
                .build();

        AndroidNetworking.initialize(getApplicationContext());

        sharedPreferences = getSharedPreferences(SP_HERO_APP, Context.MODE_PRIVATE);
    }

    public static HeroApplication getInstance() {
        return INSTANCE;
    }

    public AppRoomDatabase getDatabase() {
        return database;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
