package com.example.victor.fester.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.example.victor.fester.Admin.AdminScreen;
import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.QRGenerator;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;

import java.util.HashMap;
import java.util.Map;

import br.usp.fester.fester.AmazonAppHelper;
import br.usp.fester.fester.party.PartiesActivity;

public class Login extends AppCompatActivity {

    private Button btnLogin;

    private static final String TAG = "LoginActivity";

    private EditText mUsername;
    private EditText mPassword;

    // User input
    private String mUsernameInput;
    private String mPasswordInput;

    private ProgressDialog mWaitDialog;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Listening to login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUsername = (EditText) findViewById(R.id.login_username);
                mUsernameInput = mUsername.getText().toString();

                mPassword = (EditText) findViewById(R.id.login_password);
                mPasswordInput = mPassword.getText().toString();

                if(mUsernameInput.equals("") | mPasswordInput.equals(""))
                    Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                else {

                    AmazonAppHelper.setUser(mUsernameInput);

                    mWaitDialog = new ProgressDialog(Login.this);
                    mWaitDialog.setTitle(R.string.login_wait_dialog);
                    mWaitDialog.show();

                    AmazonAppHelper.getUserPool().getUser(mUsernameInput).getSessionInBackground(authenticationHandler);

                }
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

            QRGenerator.generateQR(mUsername, getBaseContext());

            // Recebendo usuario do BD
            UserDBAdapter dbAdapter = new UserDBAdapter(getBaseContext());
            dbAdapter.open();
            User user = dbAdapter.getUser();
            dbAdapter.close();

            user.setName(mUsername.getText().toString());
            user.toDataBase(getBaseContext());


            // LAUNCH NEW ACTIVITY
            // Might be interesting to send user data to the new activity
            Intent intent = new Intent(Login.this, NavigationActivity.class);
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
