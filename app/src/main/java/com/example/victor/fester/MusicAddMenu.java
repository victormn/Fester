package com.example.victor.fester;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
                    PlaylistDBAdapter dbAdapter = new PlaylistDBAdapter(getBaseContext());

                    dbAdapter.open();

                    dbAdapter.createMusic(titleMusic, artistMusic);

                    RankingDBAdapter dbAdapter2 = new RankingDBAdapter(getBaseContext());

                    dbAdapter2.open();

                    dbAdapter2.createMusic(titleMusic, artistMusic, 1);
                }

                // Mudando para a tela principal
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(intent);

            }
        });
    }

}

/*    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        titles = new ArrayList<>();
        titles.add("The View from the Afternoon ");
        titles.add("I Bet You Look Good on the Dancefloor");
        titles.add("Fake Tales of San Francisco");
        titles.add("Dancing Shoes");
        titles.add("You Probably Couldn't See for the Lights but You Were Staring Straight at Me");
        titles.add("Still Take You Home");
        titles.add("Riot Van");
        titles.add("Red Light Indicates Doors Are Secured");
        titles.add("Mardy Bum");
        titles.add("Perhaps Vampires Is a Bit Strong But...");*/
