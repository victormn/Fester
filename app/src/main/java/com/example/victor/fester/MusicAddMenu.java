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

    public String titleMusic, artistMusic;
    public EditText etTitle, etArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music_add_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // Recuperando os valores das variaveis titleMusicAdd e artistMusicAdd em Strings
                etTitle = (EditText) findViewById(R.id.titleMusicAdd);
                etArtist = (EditText) findViewById(R.id.artistMusicAdd);

                artistMusic = etArtist.getText().toString();
                titleMusic = etTitle.getText().toString();

                // Verificando se foi digitado algo
                if(etArtist.getText().length() > 0 && etTitle.getText().length() > 0) {

                    // Salvando esses valores na base de dados do aplicativo
                    MusicDBAdapter dbAdapter = new MusicDBAdapter(getBaseContext());

                    dbAdapter.open();

                    dbAdapter.createMusic(titleMusic, artistMusic);
                }

                // Mudando para a tela principal
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

}
