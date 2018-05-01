package com.example.kenkina.heroapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kenkina.heroapp.database.entities.Hero;
import com.example.kenkina.heroapp.repositories.HeroRepository;

public class HeroViewModel extends ViewModel {

    private HeroRepository mRepository;
    private LiveData<PagedList<Hero>> mHeroes;
    //private LiveData<List<Hero>> mHeroes;

    String TAG = "GGx";

    public HeroViewModel() {
        super();
        mRepository = HeroRepository.getInstance();
        Log.d(TAG, "HeroViewModel");
    }

    //public LiveData<List<Hero>> getHeroes() {
    public LiveData<PagedList<Hero>> getHeroes() {
        //mHeroes = mRepository.getHeroes();
        /*PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(10)
                .build();
        if (mHeroes == null) {
            mHeroes = new LivePagedListBuilder<>
                    (mRepository.getPagedHeroes(), pagedListConfig)
                    .build();
        }

        return mHeroes;*/

        Log.d(TAG, "getHeroes");

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(10)
                .build();

        return new LivePagedListBuilder<>
                (mRepository.getPagedHeroes(), pagedListConfig)
                .setBoundaryCallback(new HeroBoundaryCallback())
                .build();
    }

    public int getHeroesCount() {
        return mRepository.getHeroesCount();
    }

    public void insert(Hero hero) {
        mRepository.insert(hero);
    }

    public void change(int id) {
        mRepository.changeById(id);
    }

    private class HeroBoundaryCallback extends PagedList.BoundaryCallback<Hero> {

        private boolean requested = false;

        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();

            Log.d(TAG, "onZeroItemsLoaded");
            if (!requested) {
                mRepository.insertHeroesFromProvider();
                requested = true;
            }
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull Hero itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull Hero itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);

            Log.d(TAG, "onItemAtEndLoaded");
            if (!requested) {
                mRepository.insertHeroesFromProvider();
                requested = true;
            }

        }

    }
}
