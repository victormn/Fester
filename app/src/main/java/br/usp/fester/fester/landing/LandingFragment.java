package br.usp.fester.fester.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.victor.fester.R;

import br.usp.fester.fester.login.LoginActivity;
import br.usp.fester.fester.signup.SignupActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment
{
	// Widgets
	private LinearLayout mButtonArea;
	private Button mSignUpButton;
	private Button mLoginButton;

	public LandingFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		if (getArguments() != null)
//		{
//			mParam1 = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
//		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_landing, container, false);

		// Instantiate the manageable widgets we will use
		mButtonArea = (LinearLayout) v.findViewById(R.id.layout_authentication_buttons);
		mSignUpButton = (Button) v.findViewById(R.id.button_signup);
		mLoginButton = (Button) v.findViewById(R.id.button_login);

		mSignUpButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(), SignupActivity.class);
				startActivity(intent);
			}
		});

		mLoginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});

		return v;
	}

	/*@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		CognitoUser currentUser = null;
		CognitoUserPool userPool = AmazonAppHelper.getUserPool();

		if (userPool != null)
		{
			currentUser = AmazonAppHelper.getUserPool().getCurrentUser();
		}

		if (currentUser != null)
		{
			// CONTINUAR AQUI
			Toast.makeText(getActivity(), "Temos um usuário: " + currentUser.getUserId(), Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(getActivity(), "Sem usuário", Toast.LENGTH_LONG).show();
		}
	}*/
}
