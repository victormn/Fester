package br.usp.fester.fester.party;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.victor.fester.R;

/**
 * Created by adriano on 26/11/16.
 */

public class DatePickerFragment extends DialogFragment
{
	private static final String ARG_DATE_YEAR = "year";
	private static final String ARG_DATE_MONTH = "month";
	private static final String ARG_DATE_DAY = "day";

	public static final String EXTRA_DATE_YEAR = "br.usp.fester.date.year";
	public static final String EXTRA_DATE_MONTH = "br.usp.fester.date.month";
	public static final String EXTRA_DATE_DAY = "br.usp.fester.date.day";

	private DatePicker mDatePicker;

	public static DatePickerFragment newInstance(int year, int month, int day)
	{
		Bundle arguments = new Bundle();

		arguments.putInt(ARG_DATE_YEAR, year);
		arguments.putInt(ARG_DATE_MONTH, month);
		arguments.putInt(ARG_DATE_DAY, day);

		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(arguments);

		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Bundle arguments = getArguments();

		int year = arguments.getInt(ARG_DATE_YEAR);
		int month = arguments.getInt(ARG_DATE_MONTH);
		int day = arguments.getInt(ARG_DATE_DAY);

		View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

		mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);

		mDatePicker.init(year, month, day, null);

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						int year = mDatePicker.getYear();
						int month = mDatePicker.getMonth(); // ZERO-INDEXED!!!
						int day = mDatePicker.getDayOfMonth();

						sendResult(Activity.RESULT_OK, year, month, day);
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				})
				.create();
	}

	private void sendResult(int resultCode, int year, int month, int day)
	{
		if (getTargetFragment() == null) return;

		Intent intent = new Intent();

		intent.putExtra(EXTRA_DATE_YEAR, year);
		intent.putExtra(EXTRA_DATE_MONTH, month);
		intent.putExtra(EXTRA_DATE_DAY, day);

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}
}
