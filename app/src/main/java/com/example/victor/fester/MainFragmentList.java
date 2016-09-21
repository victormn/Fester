package com.example.victor.fester;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainFragmentList extends ListFragment {

    private ArrayList<Music> musics;
    private MusicAdapter musicAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        musics = new ArrayList<Music>();
        musics.add(new Music("Under Pressure", "Queen, David Bowie"));
        musics.add(new Music("Bohemian Rhapsody", "Queen"));
        musics.add(new Music("Don't Stop Me Now", "Queen"));
        musics.add(new Music("I Bet You Look Good on The Dancefloor", "Arctic Monkeys"));
        musics.add(new Music("R U Mine", "Arctic Monkeys"));
        musics.add(new Music("Fluorescent Adolescent", "Arctic Monkeys"));
        musics.add(new Music("Numb Encore", "Linkin Park, Jay-Z"));
        musics.add(new Music("In The End", "Linkin Park"));
        musics.add(new Music("Eight Days a Week", "The Beatles"));
        musics.add(new Music("Come Together", "The Beatles"));

        musicAdapter = new MusicAdapter(getActivity(), musics);

        setListAdapter(musicAdapter);

        getListView().setDivider(null);

    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
    }
}
