package com.example.kenkina.heroapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kenkina.heroapp.HeroApplication;
import com.example.kenkina.heroapp.database.dao.HeroDao;
import com.example.kenkina.heroapp.database.entities.Hero;
import com.example.kenkina.heroapp.network.api.HeroRemoteProvider;

import java.util.List;

public class HeroRepository {

    private static HeroRepository INSTANCE;

    private HeroDao mHeroDao;
    private HeroRemoteProvider mHeroRemoteProvider;

    String TAG = "GGx";

    public HeroRepository() {
        mHeroDao = HeroApplication.getInstance().getDatabase().mHeroDao();
        mHeroRemoteProvider = new HeroRemoteProvider();
    }

    public static HeroRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeroRepository();
        }
        return INSTANCE;
    }

    // DAO: Get from local db
    public LiveData<List<Hero>> getHeroes() {
        return mHeroDao.getHeroes();
    }

    public DataSource.Factory<Integer, Hero> getPagedHeroes() {
        return mHeroDao.getPagedHeroes();
    }

    public int getHeroesCount() {
        int count = 0;
        try {
            count = new GetHeroesCountAsyncTask(mHeroDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getHeroesCount: " + count);
        return count;
    }

    public void insert(Hero hero) {
        new InsertAsyncTask(mHeroDao).execute(hero);
    }

    public void insert(List<Hero> heroes) {
        try {
            new InsertAllAsyncTask(mHeroDao).execute(heroes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getHeroesCount();
    }

    public void changeById(int id) {
        new ChangeIdAsyncTask(mHeroDao).execute(id);
    }


    // Remote repositories from Provider
    public void insertHeroesFromProvider() {
        mHeroRemoteProvider.getHeros(new HeroRemoteProvider.ProviderRequestListener() {
            @Override
            public void onResponse(Object entities) {
                try {
                    List<Hero> heroes = (List<Hero>) entities;
                    insert(heroes);
                    Log.d(TAG, "New heroes size: " + heroes.size());
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {

            }
        });

    }


    // AsyncTask for operations

    private static class GetHeroesCountAsyncTask extends AsyncTask<Void, Void, Integer> {

        private HeroDao asyncTaskDao;

        GetHeroesCountAsyncTask(HeroDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected final Integer doInBackground(Void... voids) {
            return asyncTaskDao.getHeroesCount();
        }
    }

    private static class InsertAllAsyncTask extends AsyncTask<List<Hero>, Void, Void> {

        private HeroDao asyncTaskDao;

        InsertAllAsyncTask(HeroDao dao) {
            asyncTaskDao = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Hero>... heroes) {
            asyncTaskDao.insert(heroes[0].toArray(new Hero[heroes[0].size()]));
            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Hero, Void, Void> {

        private HeroDao asyncTaskDao;

        InsertAsyncTask(HeroDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Hero... heroes) {
            asyncTaskDao.insert(heroes);
            return null;
        }

    }

    private static class ChangeIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private HeroDao asyncTaskDao;

        ChangeIdAsyncTask(HeroDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            asyncTaskDao.changeById(integers[0]);
            return null;
        }
    }
}
