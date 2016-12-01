package br.usp.fester.fester.party;

import br.usp.fester.fester.SingleFragmentActivity;
import br.usp.fester.fester.partygoer.SongListFragment;

public class PartiesActivity extends SingleFragmentActivity
{
	@Override
	public void onBackPressed()
	{
		// Might be dangerous (?)
		finishAffinity();
	}

	@Override
	protected PartiesFragment createFragment()
	{
		return new PartiesFragment();
	}
}
