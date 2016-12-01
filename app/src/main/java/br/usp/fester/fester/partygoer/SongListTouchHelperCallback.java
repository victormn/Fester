package br.usp.fester.fester.partygoer;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import model.Song;

/**
 * Created by adria on 9/21/2016.
 */
public class SongListTouchHelperCallback extends ItemTouchHelper.Callback
{
	private SelectedSongAdapter mSelectedSongAdapter;
	private AllSongsAdapter mAllSongsAdapter;

	public SongListTouchHelperCallback(SelectedSongAdapter selectedSongAdapter, AllSongsAdapter allSongsAdapter)
	{
		mSelectedSongAdapter = selectedSongAdapter;
		mAllSongsAdapter = allSongsAdapter;
	}

	@Override
	public boolean isLongPressDragEnabled()
	{
		// Change to true if you want to drag cards not using the handle
		return false;
	}

	@Override
	public boolean isItemViewSwipeEnabled()
	{
		return true;
	}

	@Override
	public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
	{
		int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
		int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

		return makeMovementFlags(dragFlags, swipeFlags);
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
	{
		mSelectedSongAdapter.onMoveSong(viewHolder.getAdapterPosition(), target.getAdapterPosition());
		return true;
	}

	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
	{
		Song exchangedSong;

		exchangedSong = mSelectedSongAdapter.onRemoveSong(viewHolder.getAdapterPosition());
		mAllSongsAdapter.onAddSong(exchangedSong);
	}
}
