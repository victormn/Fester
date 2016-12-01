package com.example.victor.fester.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.BitmapManager;
import com.example.victor.fester.Toolbox.QRGenerator;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Map;

import br.usp.fester.fester.AmazonAppHelper;

public class Register extends AppCompatActivity {

    static EditText username, phone, nome, senha;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        username = (EditText) findViewById(R.id.reg_username);
        phone = (EditText) findViewById(R.id.reg_email);
        nome = (EditText) findViewById(R.id.reg_fullname);
        senha = (EditText) findViewById(R.id.reg_password);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        // Listening to login button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(
                    username.getText().toString().equals("") |
                    phone.getText().toString().equals("") |
                    nome.getText().toString().equals("") |
                    senha.getText().toString().equals(""))

                    Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                else {

                    Map<String, String> fieldMap = AmazonAppHelper.getSignUpFieldsC2O();
                    CognitoUserAttributes userAttributes = new CognitoUserAttributes();

                    String usernameS = username.getText().toString();
                    String passwordS = senha.getText().toString();

                    userAttributes.addAttribute(fieldMap.get("Name"), nome.getText().toString());
                    userAttributes.addAttribute(fieldMap.get("Family name"), "");
                    userAttributes.addAttribute(fieldMap.get("Phone number"), phone.getText().toString());
                    userAttributes.addAttribute(fieldMap.get("Email"), "a@a.com");

                    mWaitDialog = new ProgressDialog(v.getContext());
                    mWaitDialog.setTitle(R.string.signup_wait_dialog);
                    mWaitDialog.show();

                    AmazonAppHelper.getUserPool().signUpInBackground(usernameS, passwordS, userAttributes, null, signUpHandler);
                }
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
                Toast.makeText(Register.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(Register.this, "O usuário precisa confirmar o cadastro", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
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