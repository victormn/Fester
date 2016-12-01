package br.usp.fester.fester.partygoer;

import br.usp.fester.fester.SingleFragmentActivity;

public class SongSelectionActivity extends SingleFragmentActivity
{
	@Override
	protected SongListFragment createFragment()
	{
		return new SongListFragment();
	}
}
