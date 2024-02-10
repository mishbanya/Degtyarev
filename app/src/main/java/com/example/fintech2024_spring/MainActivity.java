package com.example.fintech2024_spring;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Film> data = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textError = findViewById(R.id.textError);

        RecyclerView Recycler = findViewById(R.id.recycler);
        recyclerAdapter = new RecyclerAdapter(this, data);
        Recycler.setAdapter(recyclerAdapter);
        Recycler.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Film film) {
                Intent intent = new Intent(MainActivity.this, FilmActivity.class);
                intent.putExtra("film", film);
                startActivity(intent);
            }
        });

        new FetchFilmsTask(new FetchFilmsTask.OnFilmsFetchedListener() {
            @Override
            public void onFilmsFetched(List<Film> films) {
                if (films.isEmpty()) {
                    textError.setText("Ошибка при загрузке фильмов");
                } else {
                    data.addAll(films);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }
        }).execute();
    }
}