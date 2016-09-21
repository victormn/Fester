package com.example.victor.fester;

/**
 * Created by Victor on 20/09/2016.
 */
public class Music {

    private String title, artist;
    private long musicId;

    public Music (String title, String artist){
        this.title = title;
        this.artist = artist;
        this.musicId = 0;
    }

    public Music (String title, String artist, long musicId){
        this.title = title;
        this.artist = artist;
        this.musicId = musicId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getMusicId() {
        return musicId;
    }
}
