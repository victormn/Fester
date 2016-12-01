package br.usp.fester.fester.party;

import br.usp.fester.fester.SingleFragmentActivity;
import br.usp.fester.fester.partygoer.SongListFragment;

public class AddPartyActivity extends SingleFragmentActivity
{

	@Override
	protected AddPartyFragment createFragment()
	{
		return new AddPartyFragment();
	}
}
