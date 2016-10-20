package com.example.victor.fester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Victor on 21/09/2016.
 */
public class PlaylistDBAdapter {

    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    public static final String MUSIC_TABLE = "music";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";

    private String[] allColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_ARTIST};

    public static final String CREATE_TABLE_MUSIC = "create table " + MUSIC_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_ARTIST + " text not null);";

    private SQLiteDatabase sqlDB;
    private Context context;

    private MusicDBHelper musicDBHelper;

    public PlaylistDBAdapter(Context ctx){
        context = ctx;
    }

    public PlaylistDBAdapter open() throws android.database.SQLException{
        musicDBHelper = new MusicDBHelper(context);
        sqlDB = musicDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        musicDBHelper.close();
    }

    public Music createMusic(String title, String artist){

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_ARTIST, artist);

        long insertId = sqlDB.insert(MUSIC_TABLE,null, values);

        Cursor cursor = sqlDB.query(MUSIC_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Music music = cursorToMusic(cursor);
        cursor.close();

        return music;
    }

    public ArrayList<Music> getAllMusic(){
        ArrayList<Music> musics = new ArrayList<>();

        Cursor cursor = sqlDB.query(MUSIC_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            Music music = cursorToMusic(cursor);
            musics.add(music);
        }

        cursor.close();

        return musics;
    }

    private Music cursorToMusic(Cursor cursor){
        Music newMusic = new Music(cursor.getString(1), cursor.getString(2), cursor.getLong(0));
        return newMusic;
    }

    public long deleteMusic(long idToDelete){
        return sqlDB.delete(MUSIC_TABLE, COLUMN_ID + " = " + idToDelete, null);
    }

    private static class MusicDBHelper extends SQLiteOpenHelper{

        MusicDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_MUSIC);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(MusicDBHelper.class.getName(), "Upgrading database from version "
            + oldVersion + " to " + newVersion + ", which will destroy all old date");

            db.execSQL("DROP TABLE IF EXISTS " + MUSIC_TABLE);
            onCreate(db);
        }
    }
}
