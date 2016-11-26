package com.example.victor.fester.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.victor.fester.R;

/**
 * Created by Victor on 25/11/2016.
 */
public class Register extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Listening to login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gIntent = new Intent (Register.this, Login.class);
                startActivity(gIntent);
            }
        });
    }
}