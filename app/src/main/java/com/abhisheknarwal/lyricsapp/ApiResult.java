package com.abhisheknarwal.lyricsapp;

public class ApiResult {
    private String lyrics;

    public ApiResult(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
