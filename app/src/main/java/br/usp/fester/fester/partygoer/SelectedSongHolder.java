package br.usp.fester.fester.partygoer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victor.fester.R;

/**
 * Created by adria on 9/21/2016.
 */
public class SelectedSongHolder extends RecyclerView.ViewHolder
{
	private TextView mTitleTextView;
	private TextView mArtistTextView;
	private ImageView mHandlerView;

	public SelectedSongHolder(final View itemView, SelectedSongAdapter adapter)
	{
		super(itemView);

		mTitleTextView = (TextView) itemView.findViewById(R.id.selected_song_title_text_view);
		mArtistTextView = (TextView) itemView.findViewById(R.id.selected_song_artist_text_view);
		mHandlerView = (ImageView) itemView.findViewById(R.id.reorder_handle);
	}

	public ImageView getHandlerView()
	{
		return mHandlerView;
	}

	private void updateUI(View v, int removedPosition, int insertedPosition)
	{
		RecyclerView selectedSongsRecyclerView = (RecyclerView) v.getRootView().getRootView()
				.findViewById(R.id.selected_songs_recycler_view);
		RecyclerView allSongsRecyclerView = (RecyclerView) v.getRootView().getRootView()
				.findViewById(R.id.all_songs_recycler_view);

		selectedSongsRecyclerView.getAdapter().notifyItemRemoved(removedPosition);
		allSongsRecyclerView.getAdapter().notifyItemInserted(insertedPosition);
	}

	public void setTitleTextView(String title)
	{
		mTitleTextView.setText(title);
	}

	public void setArtistTextView(String artist)
	{
		mArtistTextView.setText(artist);
	}
}
