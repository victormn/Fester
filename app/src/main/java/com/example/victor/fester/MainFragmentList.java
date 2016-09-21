package com.example.victor.fester;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainFragmentList extends ListFragment {

    private ArrayList<Music> musics;
    private MusicAdapter musicAdapter;

    public MainFragmentList(){
        musics = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MusicDBAdapter dbAdapter = new MusicDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();

        musics = dbAdapter.getAllMusic();

        dbAdapter.close();

        musicAdapter = new MusicAdapter(getActivity(), musics);

        setListAdapter(musicAdapter);

        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
    }

}
