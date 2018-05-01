package com.example.kenkina.heroapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.kenkina.heroapp.database.entities.Hero;
import com.example.kenkina.heroapp.utils.Constants;

import java.util.List;

@Dao
public interface HeroDao {

    @Query(" SELECT " +
            //Hero.CL_HERO_ID + ", " +
            Hero.CL_HERO_SERVER_ID + ", " +
            Hero.CL_HERO_NAME +
            " FROM " + Hero.TB_HERO)
        //+" ORDER BY " + Hero.CL_HERO_ID + " ASC")
    LiveData<List<Hero>> getHeroes();

    @Query(" SELECT " +
            //Hero.CL_HERO_ID + ", " +
            Hero.CL_HERO_SERVER_ID + ", " +
            Hero.CL_HERO_NAME +
            " FROM " + Hero.TB_HERO)
    //LivePagedListProvider<Integer, Hero> getPagedHeroes();
    public abstract DataSource.Factory<Integer, Hero> getPagedHeroes();

    @Query(" SELECT COUNT(1)" +
            " FROM " + Hero.TB_HERO)
    int getHeroesCount();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hero hero);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hero... heroes);


    @Query("UPDATE " + Hero.TB_HERO +
            " SET " + Hero.CL_HERO_NAME + " = 'UPDATED' " +
            //" WHERE " + Hero.CL_HERO_ID + " = :id")
            " WHERE " + Hero.CL_HERO_SERVER_ID + " = :id")
    void changeById(int id);


    @Query(" DELETE FROM " + Hero.TB_HERO)
    void deleteHeroes();
}
