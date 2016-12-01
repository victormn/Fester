package br.usp.fester.fester.landing;

import android.support.annotation.Nullable;
import android.os.Bundle;

import br.usp.fester.fester.AmazonAppHelper;
import br.usp.fester.fester.SingleFragmentActivity;
import br.usp.fester.fester.partygoer.SongListFragment;

public class LandingActivity extends SingleFragmentActivity
{
    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

		// AmazonAppHelper.initialize(LandingActivity.this);

		FragmentManager fragmentManager = getSupportFragmentManager();

		Fragment fragment = fragmentManager.findFragmentById(R.id.activity_landing);

		if (fragment == null)
		{
			fragment = new LandingFragment();
			fragmentManager.beginTransaction()
					.add(R.id.activity_landing, fragment)
					.commit();
		}
    }*/

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		AmazonAppHelper.initialize(LandingActivity.this);
	}

	@Override
	protected LandingFragment createFragment()
	{
		return new LandingFragment();
	}
}
