package br.usp.fester.fester.party;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.example.victor.fester.Admin.AdminScreen;
import com.example.victor.fester.Admin.Reader;
import com.example.victor.fester.DJ.TabbedActivity;
import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.Party.PartyInfo;
import com.example.victor.fester.R;

import java.util.Calendar;
import java.util.List;

import backend.FesterClient;
import backend.model.PartyResponse;
import backend.model.PartyResponsePartiesItem;
import backend.model.PartyResponsePartiesItemDate;
import backend.model.Permissions;
import br.usp.fester.fester.AmazonAppHelper;
import model.PartiesSingleton;

// TODO: Ler as festas do backend e atualizar a recyclerview

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PartiesFragment extends Fragment
{
	private ImageButton mRefreshButton;

    private RecyclerView mPartyRecyclerView;
	private PartyAdapter mPartyAdapter;

	private FloatingActionButton mFloatingButton;

	private ProgressDialog mWaitDialog;

	public PartiesFragment(){}

	private void updateUI()
	{
		PartiesSingleton partiesSingleton = PartiesSingleton.getInstance();
		List<PartyResponsePartiesItem> parties = partiesSingleton.getParties();

		mPartyAdapter = new PartyAdapter(parties);
		mPartyRecyclerView.setAdapter(mPartyAdapter);
	}

	private void onPartySelected(final PartyResponsePartiesItem party)
	{
		int partyId = party.getId();

		// Checks permissions
		ApiClientFactory factory = new ApiClientFactory();
		final FesterClient client = factory.build(FesterClient.class);

		new AsyncTask<String, Void, Permissions>()
		{
			@Override
			protected Permissions doInBackground(String... params)
			{
				String username = params[0];
				String party = params[1];

				return client.partiesPermissionsGet(username, party);
			}

			@Override
			protected void onPostExecute(Permissions permissions)
			{
				openParty(permissions, party);
			}
		}.execute(AmazonAppHelper.getUser(), Integer.toString(partyId));
	}

	private void updatePartyList()
	{
		ApiClientFactory factory = new ApiClientFactory();
		final FesterClient client = factory.build(FesterClient.class);

		new AsyncTask<String, Void, PartyResponse>()
		{
			@Override
			protected void onPostExecute(PartyResponse partyResponse)
			{
				PartiesSingleton.getInstance().setParties(partyResponse.getParties());
				mWaitDialog.dismiss();

				updateUI();
			}

			@Override
			protected PartyResponse doInBackground(String... params)
			{
				String username = params[0];

				return client.partiesGet(username);
			}

			@Override
			protected void onPreExecute()
			{
				mWaitDialog.show();
			}
		}.execute(AmazonAppHelper.getUser());
	}

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parties, container, false);
    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		mPartyRecyclerView = (RecyclerView) view.findViewById(R.id.parties_partylist_recyclerview);
		mPartyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mFloatingButton = (FloatingActionButton) view.findViewById(R.id.party_add_floating_button);
		mRefreshButton = (ImageButton) view.findViewById(R.id.party_list_refresh_button);

		mWaitDialog = new ProgressDialog(getActivity());
		mWaitDialog.setTitle(R.string.party_list_refreshing);
		mWaitDialog.setMessage("Por favor, aguarde");

		mFloatingButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(), AddPartyActivity.class);
				startActivity(intent);
			}
		});

		mRefreshButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				updatePartyList();
			}
		});

		updatePartyList();
	}

	private class PartyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mPartyName;
        private TextView mPartyTime;

        private TextView mAdministrator;
        private TextView mDJ;
        private TextView mSecurity;

		private PartyResponsePartiesItem mParty;

        public PartyHolder(View itemView)
        {
            super(itemView);

            mPartyName = (TextView) itemView.findViewById(R.id.card_party_title);
            mPartyTime = (TextView) itemView.findViewById(R.id.card_party_date);

            mAdministrator = (TextView) itemView.findViewById(R.id.party_card_administrator_textview);
            mDJ = (TextView) itemView.findViewById(R.id.party_card_dj_textview);
            mSecurity = (TextView) itemView.findViewById(R.id.party_card_security_id);

			itemView.setOnClickListener(this);
        }

		public void bindParty(PartyResponsePartiesItem party)
		{
			mParty = party;

			mPartyName.setText(party.getName());

			Calendar calendar = Calendar.getInstance();
			PartyResponsePartiesItemDate date = party.getDate();

			calendar.set(date.getYear(), date.getMonth() - 1, date.getDay(), date.getHour(), date.getMinute());

			mPartyTime.setText(DateFormat.getLongDateFormat(getActivity()).format(calendar.getTime()));

			/*if (party.isAdministrator()) mAdministrator.setVisibility(View.VISIBLE);
			if (party.isDj()) mDJ.setVisibility(View.VISIBLE);
			if (party.isSecurity()) mSecurity.setVisibility(View.VISIBLE);*/
		}

		@Override
		public void onClick(View v)
		{
			onPartySelected(mParty);
		}
	}

	private class PartyAdapter extends RecyclerView.Adapter<PartyHolder>
	{
		private List<PartyResponsePartiesItem> mPartyList;

		public PartyAdapter(List<PartyResponsePartiesItem> parties)
		{
			this.mPartyList = parties;
		}

		@Override
		public PartyHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

			View itemView = layoutInflater.inflate(R.layout.card_party, parent, false);

			return new PartyHolder(itemView);
		}

		@Override
		public void onBindViewHolder(PartyHolder holder, int position)
		{
			PartyResponsePartiesItem party = mPartyList.get(position);
			holder.bindParty(party);
		}

		@Override
		public int getItemCount()
		{
			return mPartyList.size();
		}
	}

	public String getPartyStatus(int hora, int dia, int mes, int ano){

		System.out.println(hora + " " +dia + " " +mes + " " +ano + " ");

		String result = null;

		Calendar calendar = Calendar.getInstance();

		if(calendar.get(Calendar.YEAR) > ano){
			result = "finished";
		}
		else if(calendar.get(Calendar.YEAR) == ano){

			if(calendar.get(Calendar.MONTH) > mes){
				result = "finished";
			}
			else if (calendar.get(Calendar.MONTH) == mes){

				if (calendar.get(Calendar.DAY_OF_MONTH) < dia){
					result = "incoming";
				}
				else if (calendar.get(Calendar.DAY_OF_MONTH) == dia){
					if((hora <= calendar.get(Calendar.HOUR_OF_DAY)) && ((hora+5) > calendar.get(Calendar.HOUR_OF_DAY)))
						result = "started";
					else if(hora > calendar.get(Calendar.HOUR_OF_DAY))
						result = "incoming";
					else if((hora+5) < calendar.get(Calendar.HOUR_OF_DAY))
						result = "finished";
				}
				else if (calendar.get(Calendar.DAY_OF_MONTH) == dia+1){
					if((hora+5)%24 > calendar.get(Calendar.HOUR_OF_DAY))
						result = "started";
					else result = "finished";
				}
				else result = "finished";
			}
			else result = "incoming";
		}
		else result = "incoming";

		return result;
	}

	public void openParty(Permissions permissions, PartyResponsePartiesItem party){

		int hora = party.getDate().getHour();
		int dia = party.getDate().getDay();
		int mes = party.getDate().getMonth();
		int ano = party.getDate().getYear();
		String descricao = party.getDescription();
		String nomeFesta = party.getName();
		String local = party.getAddress();

		String partyId = Integer.toString(party.getId());

		String partyStatus = getPartyStatus(hora, dia, mes-1, ano);
		// valores possiveis: incoming, started, finished


		String partyRole;
		if(permissions.getAdministrator()!= null && permissions.getAdministrator()) partyRole = "admin";
		else if(permissions.getAgent()!= null && permissions.getAgent()) partyRole = "vendedor";
		else if(permissions.getSecurity()!= null && permissions.getSecurity()) partyRole = "segurança";
		else partyRole = "festeiro";
		// valores possiveis: DJ, admin, vendedor, segurança, festeiro

		Intent intent;
		System.out.println("role " + partyRole);

		switch (partyStatus) {
			case "incoming":
				intent = new Intent(getActivity(), PartyInfo.class);
				intent.putExtra(EXTRA_STATUS, partyStatus);
				intent.putExtra(EXTRA_DESCRICAO, descricao);
				intent.putExtra(EXTRA_NOME, nomeFesta);
				intent.putExtra(EXTRA_LOCAL, local);
				intent.putExtra(EXTRA_HORA, Integer.toString(hora));
				intent.putExtra(EXTRA_DIA, Integer.toString(dia));
				intent.putExtra(EXTRA_MES, Integer.toString(mes));
				intent.putExtra(EXTRA_ANO, Integer.toString(ano));
				startActivity(intent);
				break;
			case "started":
				switch (partyRole) {
					case "DJ":
						intent = new Intent(getActivity(), TabbedActivity.class);
						intent.putExtra(EXTRA_ID, partyId);
						startActivity(intent);
						break;
					case "admin":
						intent = new Intent(getActivity(), AdminScreen.class);
						intent.putExtra(EXTRA_ID, partyId);
						startActivity(intent);
						break;
					case "vendedor":
						intent = new Intent(getActivity(), AdminScreen.class);
						intent.putExtra(EXTRA_ID, partyId);
						startActivity(intent);
						break;
					case "segurança":
						intent = new Intent(getActivity(), Reader.class);
						intent.putExtra(EXTRA_ID, partyId);
						startActivity(intent);
						break;
					case "festeiro":
						intent = new Intent(getActivity(), TabbedActivity.class); //!!!!!!!!!!!
						intent.putExtra(EXTRA_ID, partyId);
						startActivity(intent);
						break;
					default:
						System.out.println(getResources().getString(R.string.error_role_party));
						break;
				}
				break;
			case "finished":
				intent = new Intent(getActivity(), PartyInfo.class);
				intent.putExtra(EXTRA_STATUS, partyStatus);
				intent.putExtra(EXTRA_DESCRICAO, descricao);
				intent.putExtra(EXTRA_NOME, nomeFesta);
				intent.putExtra(EXTRA_LOCAL, local);
				intent.putExtra(EXTRA_HORA, Integer.toString(hora));
				intent.putExtra(EXTRA_DIA, Integer.toString(dia));
				intent.putExtra(EXTRA_MES, Integer.toString(mes));
				intent.putExtra(EXTRA_ANO, Integer.toString(ano));
				startActivity(intent);

				break;
			default:
				System.out.println(getResources().getString(R.string.error_status_party));
				break;
		}
	}

	public final static String EXTRA_STATUS = "com.example.victor.fester.STATUS";
	public final static String EXTRA_DESCRICAO = "com.example.victor.fester.DESCRICAO";
	public final static String EXTRA_LOCAL = "com.example.victor.fester.LOCAL";
	public final static String EXTRA_NOME = "com.example.victor.fester.NOME";
	public final static String EXTRA_HORA = "com.example.victor.fester.HORA";
	public final static String EXTRA_DIA = "com.example.victor.fester.DIA";
	public final static String EXTRA_MES = "com.example.victor.fester.MES";
	public final static String EXTRA_ANO = "com.example.victor.fester.ANO";
	public final static String EXTRA_ID = "com.example.victor.fester.ID";
}
