package br.usp.fester.fester.signup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.example.victor.fester.R;

import java.util.Map;

import br.usp.fester.fester.AmazonAppHelper;

public class SignupActivity extends AppCompatActivity
{
	private EditText mName;
	private EditText mLastname;
	private EditText mPhoneNumber;
	private EditText mEmailAddress;
	private EditText mUsername;
	private EditText mPassword;

	private Button mSignUpButton;

	private ProgressDialog mWaitDialog;
	private AlertDialog mAlertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		mName = (EditText) findViewById(R.id.name_signup);
		mLastname = (EditText) findViewById(R.id.lastname_signup);
		mPhoneNumber = (EditText) findViewById(R.id.phone_signup);
		mEmailAddress = (EditText) findViewById(R.id.email_signup);
		mUsername = (EditText) findViewById(R.id.username_signup);
		mPassword = (EditText) findViewById(R.id.password_signup);

		mSignUpButton = (Button) findViewById(R.id.signup_button);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher("BR"));
		}

		mSignUpButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Map<String, String> fieldMap = AmazonAppHelper.getSignUpFieldsC2O();
				CognitoUserAttributes userAttributes = new CognitoUserAttributes();

				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();

				userAttributes.addAttribute(fieldMap.get("Name"), mName.getText().toString());
				userAttributes.addAttribute(fieldMap.get("Family name"), mLastname.getText().toString());
				userAttributes.addAttribute(fieldMap.get("Phone number"), getUnformattedPhoneNumber());
				userAttributes.addAttribute(fieldMap.get("Email"), mEmailAddress.getText().toString());

				mWaitDialog = new ProgressDialog(v.getContext());
				mWaitDialog.setTitle(R.string.signup_wait_dialog);
				mWaitDialog.show();

				AmazonAppHelper.getUserPool().signUpInBackground(username, password, userAttributes, null, signUpHandler);

				Toast.makeText(SignupActivity.this, "O telefone é " + getUnformattedPhoneNumber(), Toast.LENGTH_LONG).show();
			}
		});
	}

	private String getUnformattedPhoneNumber()
	{
		String phoneNumber = "+55" + mPhoneNumber.getText().toString().replaceAll("\\D", "");
		return phoneNumber;
	}

	private SignUpHandler signUpHandler = new SignUpHandler()
	{
		@Override
		public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails)
		{
			mWaitDialog.dismiss();

			if (signUpConfirmationState)
			{
				Toast.makeText(SignupActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(SignupActivity.this, "O usuário precisa confirmar o cadastro", Toast.LENGTH_LONG).show();
			}
			finish();
		}

		@Override
		public void onFailure(Exception exception)
		{
			mWaitDialog.dismiss();
			showDialogMessage("Falha no cadastro", formatException(exception));
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

		return  formattedString;
	}
}
