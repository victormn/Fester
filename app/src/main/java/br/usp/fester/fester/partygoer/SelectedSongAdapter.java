package br.usp.fester.fester.partygoer;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.victor.fester.R;

import java.util.Collections;
import java.util.List;
import model.Song;

/**
 * Created by adria on 9/21/2016.
 */
public class SelectedSongAdapter extends RecyclerView.Adapter<SelectedSongHolder> implements SongManager
{
	protected List<Song> mSongList;
	private final OnStartDragListener mDragListener;

	private final String TAG = "SelectedSongAdapter";

	public SelectedSongAdapter(List<Song> songs, OnStartDragListener dragListener)
	{
		mSongList = songs;
		mDragListener = dragListener;
	}

	@Override
	public SelectedSongHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View view = layoutInflater.inflate(R.layout.selected_song_card, parent, false);
		return new SelectedSongHolder(view, this);
	}

	@Override
	public void onBindViewHolder(final SelectedSongHolder holder, int position)
	{
		Song song = SelectedSongList.getInstance().getSongs().get(position);

		holder.setTitleTextView(song.getTitle());
		holder.setArtistTextView(song.getArtist());

		holder.getHandlerView().setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				int action = MotionEventCompat.getActionMasked(event);
				if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)
				{
					mDragListener.onStartDrag(holder);
				}

				return false;
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return SelectedSongList.getInstance().getSongs().size();
	}

	@Override
	public boolean onAddSong(Song song)
	{
		int position;

		mSongList.add(song);
		position = mSongList.indexOf(song);
		notifyItemInserted(position);

		return true;
	}

	@Override
	public Song onRemoveSong(int position)
	{
		Song song = mSongList.remove(position);
		notifyItemRemoved(position);
		return song;
	}

	@Override
	public boolean onMoveSong(int fromPosition, int toPosition)
	{
		Collections.swap(mSongList, fromPosition, toPosition);
		notifyItemMoved(fromPosition, toPosition);
		return true;
	}
}
