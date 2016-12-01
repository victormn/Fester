package com.example.victor.fester.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Register extends AppCompatActivity {

    static EditText username, email, nome, senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        username = (EditText) findViewById(R.id.reg_username);
        email = (EditText) findViewById(R.id.reg_email);
        nome = (EditText) findViewById(R.id.reg_fullname);
        senha = (EditText) findViewById(R.id.reg_password);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        // Listening to login button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(
                    username.getText().toString().equals("") |
                    email.getText().toString().equals("") |
                    nome.getText().toString().equals("") |
                    senha.getText().toString().equals(""))

                    Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                else {

                    //registrar no BD

                    Intent gIntent = new Intent(Register.this, Login.class);
                    startActivity(gIntent);
                }
            }
        });
    }
}