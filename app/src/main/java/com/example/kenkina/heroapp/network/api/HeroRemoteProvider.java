package com.example.kenkina.heroapp.network.api;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.kenkina.heroapp.database.entities.Hero;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class HeroRemoteProvider {

    String TAG = "GGx";
    private final String BASE_URL = "https://api.github.com";

    /*public static HeroRemoteProvider createHeroProvider() {
        return AndroidNetworking.get(BASE_URL);
    }*/

    private String getHeroUrl() {
        return BASE_URL + "/users";
    }


    public void getHeros(ProviderRequestListener callback) {
        AndroidNetworking.get(getHeroUrl())
                .setPriority(Priority.LOW)
                .setTag("TAG")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: OK");
                        try {
                            if (response != null) {
                                List<Hero> heroes = Hero.ProviderBuilder
                                        .from(response).buildAll();
                                callback.onResponse(heroes);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        callback.onError(anError.getMessage());
                    }
                });
    }


    public interface ProviderRequestListener<T> {
        void onResponse(T entities);

        void onError(String message);
    }
}
