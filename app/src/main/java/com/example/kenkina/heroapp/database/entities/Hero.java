package com.example.kenkina.heroapp.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.kenkina.heroapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Hero.TB_HERO,
        indices = {@Index(value = {Hero.CL_HERO_SERVER_ID}, unique = true)})
public class Hero {

    public static final String TB_HERO = "tb_hero";
    public static final String CL_HERO_ID = "cl_hero_id";
    public static final String CL_HERO_SERVER_ID = "cl_hero_server_id";
    public static final String CL_HERO_NAME = "cl_hero_name";


    /*@PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = Hero.CL_HERO_ID)
    private Integer id;*/

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Hero.CL_HERO_SERVER_ID)
    private String serverId;

    @NonNull
    @ColumnInfo(name = Hero.CL_HERO_NAME)
    private String name;


    public Hero() {
    }

    public Hero(@NonNull String serverId, @NonNull String name) {
        this.serverId = serverId;
        this.name = name;
    }

    /*@NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public Hero setIdBuilder(@NonNull Integer id) {
        this.id = id;
        return this;
    }*/


    @NonNull
    public String getServerId() {
        return serverId;
    }

    public void setServerId(@NonNull String serverId) {
        this.serverId = serverId;
    }

    public Hero setServerIdBuilder(@NonNull String serverId) {
        this.serverId = serverId;
        return this;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Hero setNameBuilder(@NonNull String name) {
        this.name = name;
        return this;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Hero.class && obj == this) {
            return true;
        }

        Hero hero = (Hero) obj;
        return hero.serverId.equals(this.serverId) && hero.name.equals(this.name);
    }


    public static class ProviderBuilder {

        private Hero hero;
        private List<Hero> heroes;

        public ProviderBuilder(Hero hero) {
            this.hero = hero;
        }

        public ProviderBuilder(List<Hero> heroes) {
            this.heroes = heroes;
        }

        // JSONObject -> JavaObject
        public static ProviderBuilder from(JSONObject jsonSource) {
            try {
                return new ProviderBuilder(new Hero()
                        .setServerIdBuilder(jsonSource.getString("id"))
                        .setNameBuilder(jsonSource.getString("login")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // JSONArray -> JavaList
        public static ProviderBuilder from(JSONArray jsonSources) {
            int length = jsonSources.length();
            List<Hero> sources = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                try {
                    ProviderBuilder providerBuilder = ProviderBuilder.from(jsonSources.getJSONObject(i));
                    if (providerBuilder != null) {
                        sources.add(providerBuilder.build());
                    }
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
            return new ProviderBuilder(sources);
        }


        public Hero build() {
            return hero;
        }

        public List<Hero> buildAll() {
            return heroes;
        }
    }


    /*public static DiffUtil.ItemCallback<Hero> DIFF_CALLBACK = new ItemCallback<Hero>() {
        @Override
        public boolean areItemsTheSame(Hero oldItem, Hero newItem) {
            return oldItem.id.equals(newItem.id) && oldItem.name.equals(newItem.name);
        }

        @Override
        public boolean areContentsTheSame(Hero oldItem, Hero newItem) {
            return oldItem.equals(newItem);
        }
    };*/

    /*public static class HeroDiffCallback extends DiffUtil.Callback {

        List<Hero> mOldHeros;
        List<Hero> mNewHeros;

        public HeroDiffCallback(List<Hero> mOldHeros, List<Hero> mNewHeros) {
            this.mOldHeros = mOldHeros;
            this.mNewHeros = mNewHeros;
        }

        @Override
        public int getOldListSize() {
            return mOldHeros.size();
        }

        @Override
        public int getNewListSize() {
            return mNewHeros.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if(mOldHeros.get(oldItemPosition) == null || mNewHeros.get(newItemPosition) == null){
                return false;
            }

            return mOldHeros.get(oldItemPosition).getId() == mNewHeros.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            final Hero oldHero = mOldHeros.get(oldItemPosition);
            final Hero newHero = mNewHeros.get(newItemPosition);

            return oldHero.getName().equals(newHero.getName());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }*/
}
