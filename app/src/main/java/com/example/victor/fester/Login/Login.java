package com.example.victor.fester.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victor.fester.Admin.AdminScreen;
import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.QRGenerator;

public class Login extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Listening to login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userEditText = (EditText) findViewById(R.id.login_username);
                String user = userEditText.getText().toString();

                EditText senhaEditText = (EditText) findViewById(R.id.login_password);
                String senha = senhaEditText.getText().toString();

                if(user.equals("") | senha.equals(""))
                    Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                else {

                    boolean login = true; //verificar

                    if(login) {

                        QRGenerator.generateQR(userEditText, getBaseContext());

                        Intent gIntent = new Intent(Login.this, NavigationActivity.class);
                        startActivity(gIntent);
                    }else Toast.makeText(getBaseContext(), "Login ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
