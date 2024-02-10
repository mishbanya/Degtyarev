package com.example.fintech2024_spring;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.fintech2024_spring.databinding.ActivityFilmBinding;
import com.squareup.picasso.Picasso;

public class FilmActivity extends AppCompatActivity {
    private ActivityFilmBinding binding;
    Film film;
    String description=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("film")) {
            film = (Film) intent.getSerializableExtra("film");
        }

        ImageView filmCover = findViewById(R.id.filmCover);
        String genres="";
        String countries="";
        for(int i=0;i<film.getCountries().size();i++) {
            countries = i == film.getCountries().size()-1 ? countries + film.getCountries().get(i) : countries + film.getCountries().get(i) + "\n";
        }
        for(int i=0;i<film.getGenres().size();i++) {
            genres = i == film.getGenres().size()-1 ? genres + film.getGenres().get(i) : genres + film.getGenres().get(i) + "\n" ;
        }

        FetchFilmDescriptionTask fetchFilmDescriptionTask = new FetchFilmDescriptionTask(film.getKinopoiskId(), new FetchFilmDescriptionTask.OnDescriptionFetchedListener() {
            @Override
            public void onDescriptionFetched(String fetchedDescription) {
                description = fetchedDescription;
                if (description != null) {
                    binding.filmDescription.setText(description);
                }
            }
        });
        fetchFilmDescriptionTask.execute();
        binding.filmRating.setText(String.valueOf(film.getRatingKinopoisk()));
        binding.filmGenre.setText(genres);
        binding.filmCountry.setText(countries);
        binding.filmName.setText(film.getName());
        binding.filmYear.setText(String.valueOf(film.getYear()));
        Picasso.get().load(film.getPosterUrl()).into(filmCover);

    }
}