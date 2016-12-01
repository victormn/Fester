package com.example.victor.fester.Party;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.victor.fester.Admin.Reader;
import com.example.victor.fester.R;

/**
 * Created by Victor on 30/11/2016.
 */
public class EditParty extends AppCompatActivity {

    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_edit);

        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        // Listening to edit button
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eIntent = new Intent (EditParty.this, Reader.class);
                startActivity(eIntent);
            }
        });
    }
}
