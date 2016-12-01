package br.usp.fester.fester.party;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.example.victor.fester.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import backend.FesterClient;
import backend.model.IdResponse;
import backend.model.PartyInput;
import backend.model.PartyInputDate;
import br.usp.fester.fester.AmazonAppHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPartyFragment extends Fragment
{
	private TextInputEditText mNameInputText;
	private TextInputEditText mPlaceTextInputText;
	private TextInputEditText mDescriptionInputText;
	private TextView mDateText;
	private TextView mTimeText;
	private Button mCreatePartyButton;

	private Toolbar mToolbar;

	private CardView mDateCardView;
	private CardView mTimeCardView;
	private CardView mPlaceCardView;

	private ImageButton mDatePickerButton;
	private ImageButton mTimePickerButton;

	private SimpleDateFormat mDateFormat;
	private SimpleDateFormat mTimeFormat;

	private ProgressDialog mWaitDialog;

	private static String DIALOG_DATE = "DialogDate";
	private static String DIALOG_TIME = "DialogTime";

	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_TIME = 1;

	private int mYear, mMonth, mDay, mHour, mMinute;

	public AddPartyFragment()
	{
		Locale brHue = new Locale("pt", "BR");

		mDateFormat = new SimpleDateFormat("dd/MM/yyyy", brHue);
		mTimeFormat = new SimpleDateFormat("HH:mm", brHue);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_add_party, container, false);

		setHasOptionsMenu(true);

		mNameInputText = (TextInputEditText) view.findViewById(R.id.add_party_name_textview);
		mDescriptionInputText = (TextInputEditText) view.findViewById(R.id.add_party_description_textview);
		mPlaceTextInputText = (TextInputEditText) view.findViewById(R.id.add_party_place_textinput);

		mDatePickerButton = (ImageButton) view.findViewById(R.id.add_party_date_picker_button);
		mTimePickerButton = (ImageButton) view.findViewById(R.id.add_party_time_picker_button);
		mDateText = (TextView) view.findViewById(R.id.add_party_date_textview);
		mTimeText = (TextView) view.findViewById(R.id.add_party_time_textview);

		mDateCardView = (CardView) view.findViewById(R.id.add_party_date_cardview);
		mTimeCardView = (CardView) view.findViewById(R.id.add_party_time_cardview);
		mPlaceCardView = (CardView) view.findViewById(R.id.add_party_place_cardview);

		mToolbar = (Toolbar) view.findViewById(R.id.add_party_toolbar);

//		AppCompatActivity activity = ((AppCompatActivity) getActivity());
//		activity.setSupportActionBar(mToolbar);
//		activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//		activity.getSupportActionBar().setTitle("");

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		Calendar calendar = getNextTime();

		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		mHour = calendar.get(Calendar.HOUR_OF_DAY);
		mMinute = calendar.get(Calendar.MINUTE);

		mDateText.setText(mDateFormat.format(calendar.getTime()));
		mTimeText.setText(mTimeFormat.format(calendar.getTime()));

		mDateCardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentManager fragmentManager = getFragmentManager();

				DatePickerFragment dialog = DatePickerFragment.newInstance(mYear, mMonth, mDay);
				dialog.setTargetFragment(AddPartyFragment.this, REQUEST_DATE);
				dialog.show(fragmentManager, DIALOG_DATE);
			}
		});

		mTimeCardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FragmentManager fragmentManager = getFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mHour, mMinute);
				dialog.setTargetFragment(AddPartyFragment.this, REQUEST_TIME);
				dialog.show(fragmentManager, DIALOG_TIME);
			}
		});
	}

	@NonNull
	private Calendar getNextTime()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		return calendar;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.add_party_create_party_action_button:
				return saveParty();

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.add_party_menu, menu);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode != Activity.RESULT_OK) return;

		if (requestCode == REQUEST_DATE)
		{
			mYear = data.getIntExtra(DatePickerFragment.EXTRA_DATE_YEAR, mYear);
			mMonth = data.getIntExtra(DatePickerFragment.EXTRA_DATE_MONTH, mMonth);
			mDay = data.getIntExtra(DatePickerFragment.EXTRA_DATE_DAY, mDay);

			updateDate();
		}

		if (requestCode == REQUEST_TIME)
		{
			mHour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, mHour);
			mMinute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE, mMinute);

			updateTime();
		}
	}

	private boolean saveParty()
	{
		String partyName = mNameInputText.getText().toString();
		String partyDescription = mDescriptionInputText.getText().toString();
		String partyAddress = mPlaceTextInputText.getText().toString();

		/*Toast.makeText(getActivity(), "Dados da festa:\nNome: " + partyName + "\nData: " +
				partyDate + "\nHora: " + partyTime + "\nEndereço: " + partyAddress +
				"\nDescrição: " + partyDescription, Toast.LENGTH_LONG).show();*/

		mWaitDialog = new ProgressDialog(getActivity());
		mWaitDialog.setTitle(R.string.add_party_wait_dialog);
		mWaitDialog.show();

		ApiClientFactory factory = new ApiClientFactory();
		final FesterClient client = factory.build(FesterClient.class);

		PartyInput newParty = newPartyInput(partyName, partyDescription, partyAddress,
				mYear, mMonth + 1, mDay, mHour, mMinute);

		new AsyncTask<PartyInput, Void, IdResponse>()
		{
			@Override
			protected IdResponse doInBackground(PartyInput... params)
			{
				PartyInput party = params[0];

				return client.partiesPost(party);
			}

			@Override
			protected void onPostExecute(IdResponse idResponse)
			{
				mWaitDialog.dismiss();
				//Toast.makeText(getActivity(), "Party ID: " + idResponse.getId(), Toast.LENGTH_LONG).show();

				getActivity().finish();
			}
		}.execute(newParty);

		return true;
	}

	private void updateDate()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, mYear);
		calendar.set(Calendar.MONTH, mMonth);
		calendar.set(Calendar.DAY_OF_MONTH, mDay);

		mDateText.setText(mDateFormat.format(calendar.getTime()));
	}

	private void updateTime()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, mHour);
		calendar.set(Calendar.MINUTE, mMinute);

		mTimeText.setText(mTimeFormat.format(calendar.getTime()));
	}

	private PartyInput newPartyInput(String name, String description, String address, int year, int month, int day,
									 int hour, int minute)
	{
		PartyInputDate inputDate = new PartyInputDate();
		PartyInput inputParty = new PartyInput();

		inputDate.setDay(day);
		inputDate.setMonth(month);
		inputDate.setYear(year);
		inputDate.setHour(hour);
		inputDate.setMinute(minute);

		inputParty.setDate(inputDate);
		inputParty.setName(name);
		inputParty.setDescription(description);
		inputParty.setAddress(address);
		inputParty.setOwner(AmazonAppHelper.getUser());

		return inputParty;
	}
}
