package br.usp.fester.fester.partygoer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.victor.fester.R;


/**
 * Created by adria on 9/20/2016.
 */
public class SongListFragment extends Fragment implements OnStartDragListener
{
	private RecyclerView mSelectedSongsList;
	private RecyclerView mAllSongsList;
	private SelectedSongAdapter mSelectedSongsAdapter;
	private AllSongsAdapter mAllSongsAdapter;
	private ItemTouchHelper mItemTouchHelper;
	private Button mSendButton;

	private LinearLayout mSelectedSongsArea;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_song_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		mSelectedSongsList = (RecyclerView) view.findViewById(R.id.selected_songs_recycler_view);
		mSelectedSongsList.setLayoutManager(new LinearLayoutManager(getActivity()));
		//mSelectedSongsList.setItemAnimator(new DefaultItemAnimator());
		//mSelectedSongsList.smoothScrollBy(0, 50);

		//mSelectedSongsArea = (LinearLayout) view.findViewById(R.id.selected_area);
		//mSelectedSongsArea.setVisibility(View.GONE);

		mSendButton = (Button) view.findViewById(R.id.send_to_dj_button);
		mSendButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Snackbar.make(v, "Enviado para o DJ!", Snackbar.LENGTH_LONG).show();
			}
		});

		mSelectedSongsAdapter = new SelectedSongAdapter(SelectedSongList.getInstance().getSongs(), this);
		mSelectedSongsList.setAdapter(mSelectedSongsAdapter);

		mAllSongsList = (RecyclerView) view.findViewById(R.id.all_songs_recycler_view);
		mAllSongsList.setLayoutManager(new LinearLayoutManager(getActivity()));
		//mAllSongsList.setItemAnimator(new DefaultItemAnimator());
		//mAllSongsList.smoothScrollBy(0,50);

		mAllSongsAdapter = new AllSongsAdapter(AllSongsList.getInstance().getSongs());
		mAllSongsList.setAdapter(mAllSongsAdapter);

		ItemTouchHelper.Callback selectedCallback = new SongListTouchHelperCallback(mSelectedSongsAdapter, mAllSongsAdapter);
		mItemTouchHelper = new ItemTouchHelper(selectedCallback);
		mItemTouchHelper.attachToRecyclerView(mSelectedSongsList);
	}

	@Override
	public void onStartDrag(RecyclerView.ViewHolder viewHolder)
	{
		mItemTouchHelper.startDrag(viewHolder);
	}
}
