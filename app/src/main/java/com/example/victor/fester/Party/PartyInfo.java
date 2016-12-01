package com.example.victor.fester.Party;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.victor.fester.NavigationActivity;
import com.example.victor.fester.R;

import br.usp.fester.fester.party.PartiesFragment;

public class PartyInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_info);

        Intent intent = getIntent();

        String partyName = intent.getStringExtra(PartiesFragment.EXTRA_NOME);
        String patyLocal = intent.getStringExtra(PartiesFragment.EXTRA_LOCAL);
        String partyHora = intent.getStringExtra(PartiesFragment.EXTRA_HORA);
        String partyDescription = intent.getStringExtra(PartiesFragment.EXTRA_DESCRICAO);
        String partymes = intent.getStringExtra(PartiesFragment.EXTRA_MES);
        String partydia = intent.getStringExtra(PartiesFragment.EXTRA_DIA);
        String partyano = intent.getStringExtra(PartiesFragment.EXTRA_ANO);

        String partydata = partydia+"/"+partymes+"/"+partyano;

        TextView name = (TextView) findViewById(R.id.info_party_name);
        TextView local = (TextView) findViewById(R.id.info_party_local);
        TextView hora = (TextView) findViewById(R.id.info_party_horario);
        TextView description = (TextView) findViewById(R.id.info_party_description);
        TextView finished = (TextView) findViewById(R.id.info_party_finished);
        TextView data = (TextView) findViewById(R.id.info_party_data);

        finished.setVisibility(View.INVISIBLE);

        name.setText(partyName);
        local.setText(patyLocal);
        hora.setText(partyHora + ":00h");
        description.setText(partyDescription);
        data.setText(partydata);

        String status = intent.getStringExtra(PartiesFragment.EXTRA_STATUS);

        if(status.equals("finished")){
            finished.setText(getResources().getString(R.string.party_finished));
            finished.setTextColor(Color.RED);
            finished.setVisibility(View.VISIBLE);
        }

        setTitle(getResources().getString(R.string.party_info));

    }

}
