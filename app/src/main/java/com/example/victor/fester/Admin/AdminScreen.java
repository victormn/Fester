package com.example.victor.fester.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.victor.fester.R;

/**
 * Created by Victor on 25/11/2016.
 */
public class AdminScreen extends AppCompatActivity {

    Button btnSell, btnCheckin, btnManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_screen);

        btnSell = (Button) findViewById(R.id.btnSell);
        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        btnManage = (Button) findViewById(R.id.btnManage);

        // Listening to sell button
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent (AdminScreen.this, Reader.class);
                startActivity(sIntent);
            }
        });

        // Listening to checkin button
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cIntent = new Intent (AdminScreen.this, Reader.class);
                startActivity(cIntent);
            }
        });

        // Listening to checkin button
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent (AdminScreen.this, Permission.class);
                startActivity(mIntent);
            }
        });
    }
}