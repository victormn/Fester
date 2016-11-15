package com.example.victor.fester.DJ.Playlist;


import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.victor.fester.Music.Music;
import com.example.victor.fester.R;

import java.util.ArrayList;

public class PlaylistFragmentList extends ListFragment {

    private ArrayList<Music> musics;
    private PlaylistAdapter playlistAdapter;

    public PlaylistFragmentList(){
        musics = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PlaylistDBAdapter dbAdapter = new PlaylistDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();

        musics = dbAdapter.getAllMusic();

        dbAdapter.close();

        playlistAdapter = new PlaylistAdapter(getActivity(), musics);

        setListAdapter(playlistAdapter);

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

    public static PlaylistFragmentList newInstance() {
        return new PlaylistFragmentList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;

        Music music = (Music) getListAdapter().getItem(rowPosition);

        switch (item.getItemId()){

            case R.id.delete:

                PlaylistDBAdapter dbAdapter = new PlaylistDBAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteMusic(music.getMusicId());

                // Refresh
                musics.clear();
                musics.addAll(dbAdapter.getAllMusic());
                playlistAdapter.notifyDataSetChanged();

                dbAdapter.close();
                return true;
        }

        return super.onContextItemSelected(item);

    }
}
