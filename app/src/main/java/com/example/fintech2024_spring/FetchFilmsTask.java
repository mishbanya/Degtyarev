package com.example.fintech2024_spring;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchFilmsTask extends AsyncTask<Void, Void, List<Film>> {

    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/collections?type=TOP_POPULAR_MOVIES&page=1";
    private static final String API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b";
    private static final String TAG = "FetchFilmsTask";
    private final OnFilmsFetchedListener listener;

    public FetchFilmsTask(OnFilmsFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Film> doInBackground(Void... voids) {
        List<Film> films = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .header("accept", "application/json")
                .header("X-API-KEY", API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                JsonArray itemsArray = jsonObject.getAsJsonArray("items");

                for (JsonElement element : itemsArray) {
                    JsonObject filmObject = element.getAsJsonObject();
                    Film film = new Film();
                    if (filmObject.has("kinopoiskId") && !filmObject.get("kinopoiskId").isJsonNull()) {
                        film.setKinopoiskId(filmObject.get("kinopoiskId").getAsInt());
                    }
                    if (filmObject.has("imdbId") && !filmObject.get("imdbId").isJsonNull()) {
                        film.setImdbId(filmObject.get("imdbId").getAsString());
                    }
                    if (filmObject.has("nameRu") && !filmObject.get("nameRu").isJsonNull()) {
                        film.setName(filmObject.get("nameRu").getAsString());
                    }
                    if (filmObject.has("nameEn") && !filmObject.get("nameEn").isJsonNull()) {
                        film.setNameEn(filmObject.get("nameEn").getAsString());
                    }
                    if (filmObject.has("nameOriginal") && !filmObject.get("nameOriginal").isJsonNull()) {
                        film.setNameOriginal(filmObject.get("nameOriginal").getAsString());
                    }

                    // Преобразование массива стран в List<String>
                    if (filmObject.has("countries") && !filmObject.get("countries").isJsonNull()) {
                        JsonArray countriesArray = filmObject.getAsJsonArray("countries");
                        List<String> countriesList = new ArrayList<>();
                        for (JsonElement countryElement : countriesArray) {
                            JsonObject countryObject = countryElement.getAsJsonObject();
                            countriesList.add(countryObject.get("country").getAsString());
                        }
                        film.setCountries(countriesList);
                    }

                    // Преобразование массива жанров в List<String>
                    if (filmObject.has("genres") && !filmObject.get("genres").isJsonNull()) {
                        JsonArray genresArray = filmObject.getAsJsonArray("genres");
                        List<String> genresList = new ArrayList<>();
                        for (JsonElement genreElement : genresArray) {
                            JsonObject genreObject = genreElement.getAsJsonObject();
                            genresList.add(genreObject.get("genre").getAsString());
                        }
                        film.setGenres(genresList);
                    }

                    if (filmObject.has("ratingKinopoisk") && !filmObject.get("ratingKinopoisk").isJsonNull()) {
                        film.setRatingKinopoisk(filmObject.get("ratingKinopoisk").getAsDouble());
                    }
                    if (filmObject.has("ratingImdb") && !filmObject.get("ratingImdb").isJsonNull()) {
                        film.setRatingImdb(filmObject.get("ratingImdb").getAsDouble());
                    }
                    if (filmObject.has("year") && !filmObject.get("year").isJsonNull()) {
                        film.setYear(filmObject.get("year").getAsInt());
                    }
                    if (filmObject.has("type") && !filmObject.get("type").isJsonNull()) {
                        film.setType(filmObject.get("type").getAsString());
                    }
                    if (filmObject.has("posterUrl") && !filmObject.get("posterUrl").isJsonNull()) {
                        film.setPosterUrl(filmObject.get("posterUrl").getAsString());
                    }
                    if (filmObject.has("posterUrlPreview") && !filmObject.get("posterUrlPreview").isJsonNull()) {
                        film.setPosterUrlPreview(filmObject.get("posterUrlPreview").getAsString());
                    }
                    films.add(film);
                }
            } else {
                Log.e(TAG, "Error: " + response.code() + " " + response.message());
                films = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
            films = null;
        }
        return films;
    }

    @Override
    protected void onPostExecute(List<Film> films) {
        if (listener != null) {
            if (films != null) {
                listener.onFilmsFetched(films);
            } else {
                listener.onFilmsFetched(new ArrayList<>());
            }
        }
    }

    public interface OnFilmsFetchedListener {
        void onFilmsFetched(List<Film> films);
    }
}

