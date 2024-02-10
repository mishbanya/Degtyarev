package com.example.fintech2024_spring;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchFilmDescriptionTask extends AsyncTask<Void, Void, String> {

    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/";
    private static final String API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b";
    private static final String TAG = "FetchFilmDescriptionTask";


    private final int kinopoiskId;
    private final OnDescriptionFetchedListener listener;

    public FetchFilmDescriptionTask(int kinopoiskId, OnDescriptionFetchedListener listener) {
        this.kinopoiskId = kinopoiskId;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String url = API_URL + kinopoiskId;
        String description = null;
        Request request = new Request.Builder()
                .url(url)
                .header("accept", "application/json")
                .header("X-API-KEY", API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                if (jsonObject.has("description") && !jsonObject.get("description").isJsonNull()) {
                    description = jsonObject.get("description").getAsString();
                }
                return description;
            } else {
                Log.e(TAG, "Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            super.onPostExecute(description);
        }
        if (listener != null) {
            listener.onDescriptionFetched(description);
        }
    }

    public interface OnDescriptionFetchedListener {
        void onDescriptionFetched(String description);
    }
}