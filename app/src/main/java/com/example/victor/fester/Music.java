package com.example.victor.fester;

/**
 * Created by Victor on 20/09/2016.
 */
public class Music {

    private String title, artist;
    private long musicId;
    private int ranking;

    public Music (String title, String artist){
        this.title = title;
        this.artist = artist;
        this.musicId = 0;
        this.ranking = 0;
    }

    public Music (String title, String artist, long musicId){
        this.title = title;
        this.artist = artist;
        this.musicId = musicId;
        this.ranking = 0;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
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
