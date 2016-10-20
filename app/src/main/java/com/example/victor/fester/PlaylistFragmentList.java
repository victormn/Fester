package com.example.victor.fester;


import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaylistFragmentList extends ListFragment {

    private ArrayList<Music> musics;
    private MusicAdapter musicAdapter;

    public PlaylistFragmentList(){
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

        registerForContextMenu(getListView());

        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(contextMenu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, contextMenu);

    }

    static PlaylistFragmentList newInstance() {
        return new PlaylistFragmentList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;

        Music music = (Music) getListAdapter().getItem(rowPosition);

        switch (item.getItemId()){

            case R.id.delete:

                MusicDBAdapter dbAdapter = new MusicDBAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteMusic(music.getMusicId());

                // Refresh
                musics.clear();
                musics.addAll(dbAdapter.getAllMusic());
                musicAdapter.notifyDataSetChanged();

                dbAdapter.close();
                return true;
        }

        return super.onContextItemSelected(item);

    }
}
