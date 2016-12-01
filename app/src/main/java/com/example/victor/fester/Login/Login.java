package com.example.victor.fester.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.victor.fester.Admin.AdminScreen;
import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.R;

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

                EditText emailEditText = (EditText) findViewById(R.id.login_email);
                String email = emailEditText.getText().toString();

                EditText senhaEditText = (EditText) findViewById(R.id.login_password);
                String senha = senhaEditText.getText().toString();

                //enviar e validar

                Intent gIntent = new Intent (Login.this, NavigationActivity.class);
                startActivity(gIntent);
            }
        });
    }
}
