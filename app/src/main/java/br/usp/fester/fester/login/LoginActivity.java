package br.usp.fester.fester.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.victor.fester.R;

import br.usp.fester.fester.AmazonAppHelper;
import br.usp.fester.fester.party.PartiesActivity;

public class LoginActivity extends AppCompatActivity
{
	private static final String TAG = "LoginActivity";

	private EditText mUsername;
	private EditText mPassword;

	private Button mLoginButton;

	// User input
	private String mUsernameInput;
	private String mPasswordInput;

	private ProgressDialog mWaitDialog;
	private AlertDialog mAlertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mUsername = (EditText) findViewById(R.id.textfield_login_username);
		mPassword = (EditText) findViewById(R.id.textfield_login_password);

		mLoginButton = (Button) findViewById(R.id.login_button);

		mLoginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mUsernameInput = mUsername.getText().toString();
				mPasswordInput = mPassword.getText().toString();

				AmazonAppHelper.setUser(mUsernameInput);

				mWaitDialog = new ProgressDialog(LoginActivity.this);
				mWaitDialog.setTitle(R.string.login_wait_dialog);
				mWaitDialog.show();

				AmazonAppHelper.getUserPool().getUser(mUsernameInput).getSessionInBackground(authenticationHandler);
			}
		});
	}

	AuthenticationHandler authenticationHandler = new AuthenticationHandler()
	{
		@Override
		public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice)
		{
			Log.d(TAG, "Authentication succeeded");
			mWaitDialog.dismiss();

			AmazonAppHelper.setCurrentSession(userSession);
			AmazonAppHelper.setThisDevice(newDevice);

			// newDevice.rememberThisDeviceInBackground(); ?

			// LAUNCH NEW ACTIVITY
			// Might be interesting to send user data to the new activity
			Intent intent = new Intent(LoginActivity.this, PartiesActivity.class);
			startActivity(intent);
			finish();
		}

		@Override
		public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String UserId)
		{
			if (UserId != null)
			{
				AmazonAppHelper.setUser(UserId);
			}

			AuthenticationDetails authenticationDetails = new AuthenticationDetails(UserId, mPasswordInput, null);
			authenticationContinuation.setAuthenticationDetails(authenticationDetails);
			authenticationContinuation.continueTask();
		}

		@Override
		public void getMFACode(MultiFactorAuthenticationContinuation continuation)
		{
			// Not used
		}

		@Override
		public void authenticationChallenge(ChallengeContinuation continuation)
		{
			// Not used
		}

		@Override
		public void onFailure(Exception exception)
		{
			mWaitDialog.dismiss();

			showDialogMessage("O login falhou", formatException(exception));
		}
	};

	private void showDialogMessage(String title, String body)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mAlertDialog.dismiss();
			}
		});
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	public static String formatException(Exception exception)
	{
		String formattedString = "Internal Error";
		Log.e("App Error",exception.toString());
		Log.getStackTraceString(exception);

		String temp = exception.getMessage();

		if(temp != null && temp.length() > 0) {
			formattedString = temp.split("\\(")[0];
			if(temp != null && temp.length() > 0) {
				return formattedString;
			}
		}

		return formattedString;
	}
}
