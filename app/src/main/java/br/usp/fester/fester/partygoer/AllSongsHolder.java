package br.usp.fester.fester.partygoer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victor.fester.R;

import model.Song;

/**
 * Created by adria on 9/20/2016.
 */
public class AllSongsHolder extends RecyclerView.ViewHolder
{
	private TextView mTitleTextView;
	private TextView mArtistTextView;
	private ImageView mAddToPlaylistView;

	private static AllSongsAdapter sSongAdapter;

	private static String TAG = "AllSongsHolder";

	public AllSongsHolder(final View itemView, AllSongsAdapter adapter)
	{
		super(itemView);

		mTitleTextView = (TextView) itemView.findViewById(R.id.selectable_song_title_text_view);
		mArtistTextView = (TextView) itemView.findViewById(R.id.selectable_song_artist_text_view);
		mAddToPlaylistView = (ImageView) itemView.findViewById(R.id.add_to_playlist);

		if (sSongAdapter == null)
		{
			sSongAdapter = adapter;
		}

		// Maybe you can set the listeners for the handle here
		mAddToPlaylistView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				//Snackbar.make(itemView, "Mandou para a lista de cima", Snackbar.LENGTH_SHORT).show();

				/*Log.d(TAG, "Título: " + mTitleTextView.getText());
				Log.d(TAG, "Artista: " + mArtistTextView.getText());
				Log.d(TAG, "Posição: " + getLayoutPosition());*/
				int removedPosition = getAdapterPosition();
				int insertedPosition;

				SelectedSongList selectedSongList = SelectedSongList.getInstance();
				AllSongsList allSongsList = AllSongsList.getInstance();

				Song exchangedSong = allSongsList.removeSong(removedPosition);
				insertedPosition = selectedSongList.addSong(exchangedSong);

				updateUI(v, removedPosition, insertedPosition);
			}
		});
	}

	private void updateUI(View v, int removedPosition, int insertedPosition)
	{
		RecyclerView selectedSongsRecyclerView = (RecyclerView) v.getRootView().getRootView()
				.findViewById(R.id.selected_songs_recycler_view);
		RecyclerView allSongsRecyclerView = (RecyclerView) v.getRootView().getRootView()
				.findViewById(R.id.all_songs_recycler_view);

		selectedSongsRecyclerView.getAdapter().notifyItemInserted(insertedPosition);
		allSongsRecyclerView.getAdapter().notifyItemRemoved(removedPosition);
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
