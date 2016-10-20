package com.example.victor.fester;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class RankingFragmentList extends ListFragment {

    private ArrayList<Music> musics;
    private RankingAdapter rankingAdapter;

    public RankingFragmentList(){
        musics = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RankingDBAdapter dbAdapter = new RankingDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();

        musics = dbAdapter.getAllMusic();

        dbAdapter.close();

        rankingAdapter = new RankingAdapter(getActivity(), musics);

        setListAdapter(rankingAdapter);

        registerForContextMenu(getListView());

        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
    }

    static RankingFragmentList newInstance() {
        return new RankingFragmentList();
    }

    public void refreshRanking(){

        RankingDBAdapter dbAdapter = new RankingDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();

        musics.clear();
        musics.addAll(dbAdapter.getAllMusic());
        rankingAdapter.notifyDataSetChanged();

        dbAdapter.close();
    }
}
