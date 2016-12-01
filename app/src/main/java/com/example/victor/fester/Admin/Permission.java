package com.example.victor.fester.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.victor.fester.Admin.QRCode.Reader;
import com.example.victor.fester.R;

public class Permission extends AppCompatActivity {

    Button btnEdit, btnAddAdmin, btnAddSeller, btnAddSecur, btnAddDj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_permission);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnAddAdmin = (Button) findViewById(R.id.btnAddAdmin);
        btnAddSeller = (Button) findViewById(R.id.btnAddSeller);
        btnAddSecur = (Button) findViewById(R.id.btnAddSecur);
        btnAddDj = (Button) findViewById(R.id.btnAddDj);

        // Listening to edit button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eIntent = new Intent (Permission.this, Reader.class);
                startActivity(eIntent);
            }
        });

        // Listening to add admin button
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent (Permission.this, Reader.class);
                startActivity(aIntent);
            }
        });

        // Listening to add seller button
        btnAddSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent (Permission.this, Reader.class);
                startActivity(sIntent);
            }
        });

        // Listening to add secur button
        btnAddSecur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secIntent = new Intent (Permission.this, Reader.class);
                startActivity(secIntent);
            }
        });

        // Listening to add dj button
        btnAddDj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent djIntent = new Intent (Permission.this, Reader.class);
                startActivity(djIntent);
            }
        });
    }
}
