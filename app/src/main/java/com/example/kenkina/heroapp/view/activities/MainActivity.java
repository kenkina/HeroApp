package com.example.kenkina.heroapp.view.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kenkina.heroapp.R;
import com.example.kenkina.heroapp.view.adapters.HeroListAdapter;
import com.example.kenkina.heroapp.database.entities.Hero;
import com.example.kenkina.heroapp.viewmodels.HeroViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    Context mContext = this;
    private HeroViewModel mHeroViewModel;
    private HeroListAdapter heroListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.mainFab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, NewHeroActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        heroListAdapter = new HeroListAdapter();
        heroListAdapter.setClickListener((view, position) ->
                mHeroViewModel.change(Integer.valueOf(mHeroViewModel.getHeroes().getValue().get(position).getServerId())));

        mHeroViewModel = ViewModelProviders.of(this).get(HeroViewModel.class);
        mHeroViewModel.getHeroes().observe(this, pagedList -> {
            heroListAdapter.submitList(pagedList);
            Log.d("GG", "PAGELIST: " + (pagedList != null ? pagedList.size() : 0));
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(heroListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                //mHeroViewModel.change(1);
                mHeroViewModel.getHeroesCount();

                return true;
            case R.id.action_reset:
                heroListAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String serverId = data.getStringExtra(NewHeroActivity.EXTRA_HERO_SERVER_ID);
            String name = data.getStringExtra(NewHeroActivity.EXTRA_HERO_NAME);

            Hero hero = new Hero(serverId, name);
            mHeroViewModel.insert(hero);
            //Toast.makeText(mContext, "" + hero.getId() + ": " + hero.getServerId() + ": " + hero.getName(),
            //        Toast.LENGTH_LONG).show();
            Toast.makeText(mContext, "" + hero.getServerId() + ": " + hero.getName(),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Debe completar los campos",
                    Toast.LENGTH_LONG).show();
        }
    }
}
