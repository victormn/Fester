package br.usp.fester.fester.partygoer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.victor.fester.R;

import java.util.List;
import model.Song;

/**
 * Created by adria on 9/20/2016.
 */
public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsHolder> implements SongManager
{
	protected List<Song> mSongList;

	public AllSongsAdapter(List<Song> songs)
	{
		mSongList = songs;
	}

	@Override
	public AllSongsHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View view = layoutInflater.inflate(R.layout.selectable_song_card, parent, false);
		return new AllSongsHolder(view, this);
	}

	@Override
	public void onBindViewHolder(AllSongsHolder holder, int position)
	{
		Song song = AllSongsList.getInstance().getSongs().get(position);

		holder.setTitleTextView(song.getTitle());
		holder.setArtistTextView(song.getArtist());
	}

	@Override
	public int getItemCount()
	{
		return AllSongsList.getInstance().getSongs().size();
	}

	@Override
	public boolean onAddSong(Song song)
	{
		AllSongsList songList = AllSongsList.getInstance();

		songList.addSong(song);
		int position = songList.getSongs().indexOf(song);
		notifyItemInserted(position);

		return true;
	}

	@Override
	public Song onRemoveSong(int position)
	{
		Song removedSong = AllSongsList.getInstance().removeSong(position);
		notifyItemRemoved(position);
		return removedSong;
	}

	@Override
	public boolean onMoveSong(int fromPosition, int toPosition)
	{
		return false;
	}
}
