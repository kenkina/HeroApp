package com.example.kenkina.heroapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.kenkina.heroapp.database.dao.HeroDao;
import com.example.kenkina.heroapp.database.entities.Hero;

@Database(entities = {Hero.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    public final static String DB_HERO_APP = "db_hero_app";

    public abstract HeroDao mHeroDao();

}
