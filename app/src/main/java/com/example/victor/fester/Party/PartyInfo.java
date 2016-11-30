package com.example.victor.fester.Party;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.R;

public class PartyInfo extends AppCompatActivity {

    private String partyName;
    private String patyLocal;
    private String partyHora;
    private String partyDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_info);

        Intent intent = getIntent();

        int id = Integer.parseInt(intent.getStringExtra(NavigationActivity.EXTRA_ID));
        updateInfo(id);

        TextView name = (TextView) findViewById(R.id.info_party_name);
        TextView local = (TextView) findViewById(R.id.info_party_local);
        TextView hora = (TextView) findViewById(R.id.info_party_horario);
        TextView description = (TextView) findViewById(R.id.info_party_description);
        TextView finished = (TextView) findViewById(R.id.info_party_finished);

        finished.setVisibility(View.INVISIBLE);

        name.setText(partyName);
        local.setText(patyLocal);
        hora.setText(partyHora);
        description.setText(partyDescription);

        String status = intent.getStringExtra(NavigationActivity.EXTRA_STATUS);

        if(status.equals("finished")){
            finished.setText(getResources().getString(R.string.party_finished));
            finished.setTextColor(Color.RED);
            finished.setVisibility(View.VISIBLE);
        }

        setTitle(getResources().getString(R.string.party_info));

    }

    public void updateInfo(int id){

        partyName = "PARTY HARD ENGCOMP"; //getName by id
        patyLocal = "Bloco 8"; //getLocal by id
        partyHora = "23:59h"; //getTime by id
        partyDescription = "aehoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"; //getDescription by id

    }

}
