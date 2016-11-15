package com.example.victor.fester.DJ.Playlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.victor.fester.Music.Music;
import com.example.victor.fester.R;

import java.util.ArrayList;

/**
 * Created by Victor on 20/09/2016.
 */
public class PlaylistAdapter extends ArrayAdapter<Music>{

    // Utilizado para armazenar os valores de Titulo e Artista
    // Dessa forma, nao precisa ficar carregando-os caso os mesmos ja existam
    public static class ViewHolder{
        TextView title;
        TextView artist;
    }


    public PlaylistAdapter(Context context, ArrayList<Music> musics){
        super(context, 0 , musics);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        Music music = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null){

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.music_fragment, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.musicTitle);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.musicArtist);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(music.getTitle());
        viewHolder.artist.setText(music.getArtist());

        return convertView;
    }

}
