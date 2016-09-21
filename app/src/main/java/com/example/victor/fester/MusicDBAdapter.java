package com.example.victor.fester;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Victor on 21/09/2016.
 */
public class MusicDBAdapter {

    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    public static final String MUSIC_TABLE = "music";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";

    private String[] allColumns = {COLUMN_ARTIST, COLUMN_ID, COLUMN_TITLE};

    public static final String DATABASE_CREATE = "create table " + MUSIC_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_ARTIST + " text not null);";

    private SQLiteDatabase sqlDB;
    private Context context;
}
