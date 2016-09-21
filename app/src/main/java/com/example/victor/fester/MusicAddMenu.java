package com.example.victor.fester;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MusicAddMenu extends AppCompatActivity {

    public final static String MUSIC_TITLE = "com.example.victor.fester.MUSIC_TITLE";
    public final static String MUSIC_ARTIST = "com.example.victor.fester.MUSIC_ARTIST";

    public String titleMusic, artistMusic;
    public EditText etTitle, etArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_add_menu);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                etTitle = (EditText) findViewById(R.id.titleMusicAdd);
                etArtist = (EditText) findViewById(R.id.artistMusicAdd);

                artistMusic = etArtist.getText().toString();
                titleMusic = etTitle.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(MUSIC_TITLE, titleMusic);
                intent.putExtra(MUSIC_ARTIST, artistMusic);
                startActivity(intent);

            }
        });
    }

}
