package com.abhisheknarwal.lyricsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText etArtist, etSong;
    Button btnSearch;
    String artist, song;
    TextView tvArtist, tvSong, tvLyrics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etArtist = findViewById(R.id.etArtist);
        etSong = findViewById(R.id.etSong);
        tvArtist = findViewById(R.id.tvArtist);
        tvSong = findViewById(R.id.tvSong);
        tvLyrics = findViewById(R.id.tvLyrics);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artist = etArtist.getText().toString();
                song = etSong.getText().toString();
                if(artist.equals("")){
                    Toast.makeText(MainActivity.this, "enter the artist name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(song.equals("")){
                    Toast.makeText(MainActivity.this, "enter song name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "https://api.lyrics.ovh/v1/" + artist + "/" + song;
               getLyrics(url);
            }
        });
    }

    private void getLyrics(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                ApiResult apiResult = gson.fromJson(result,ApiResult.class);
                final String lyrics = apiResult.getLyrics();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.code() == 404){
                            Toast.makeText(MainActivity.this, "Song not Found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tvArtist.setText(artist);
                        tvSong.setText(song);
                        tvLyrics.setText(lyrics);
                    }
                });
            }
        });
    }
}
