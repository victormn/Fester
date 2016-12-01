package br.usp.fester.fester.party;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.victor.fester.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment
{
	//private static final String ARGUMENT_TIME = "time";
	private static final String ARGUMENT_HOUR = "hour";
	private static final String ARGUMENT_MINUTE = "minute";

	//public static final String EXTRA_TIME = "br.usp.fester.time";
	public static final String EXTRA_HOUR = "br.usp.fester.time.hour";
	public static final String EXTRA_MINUTE = "br.usp.fester.time.minute";

	private int mHour;
	private int mMinute;

	private TimePicker mTimePicker;

	public TimePickerFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param hour Hour.
	 * @param minute Minute.
	 * @return A new instance of fragment TimePickerFragment.
	 */
	public static TimePickerFragment newInstance(int hour, int minute)
	{
		TimePickerFragment fragment = new TimePickerFragment();
		Bundle args = new Bundle();
		args.putInt(ARGUMENT_HOUR, hour);
		args.putInt(ARGUMENT_MINUTE, minute);
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
			mHour = getArguments().getInt(ARGUMENT_HOUR);
		mMinute = getArguments().getInt(ARGUMENT_MINUTE);

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

		mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_picker);
		mTimePicker.setIs24HourView(true);
		mTimePicker.setCurrentHour(mHour);
		mTimePicker.setCurrentMinute(mMinute);

		return new AlertDialog.Builder(getActivity())
				.setView(view)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						mHour = mTimePicker.getCurrentHour();
						mMinute = mTimePicker.getCurrentMinute();

						sendResult(Activity.RESULT_OK, mHour, mMinute);
					}
				})
				.create();
	}

	private void sendResult(int resultCode, int hour, int minute)
	{
		if (getTargetFragment() == null) return;

		Intent intent = new Intent();
		intent.putExtra(EXTRA_HOUR, hour);
		intent.putExtra(EXTRA_MINUTE, minute);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}
}
