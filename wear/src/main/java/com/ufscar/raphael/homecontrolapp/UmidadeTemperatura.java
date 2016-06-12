package com.ufscar.raphael.homecontrolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UmidadeTemperatura extends Activity {

    private TextView txtUmidadeTemperatura;
    private TextView txtValorUmidadeTemperatura;
    private int umiOuTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umidade_temperatura);

        initialize();

    }

    private void initialize(){

        txtUmidadeTemperatura = (TextView) findViewById(R.id.txtUmidadeTemperatura);
        txtValorUmidadeTemperatura = (TextView) findViewById(R.id.txtValorUmidadeTemperatura);

        Intent i = getIntent();
        umiOuTemp = i.getIntExtra("UMI_TEMP",3);

        if(umiOuTemp==0) {//umidade
            txtUmidadeTemperatura.setText("Umidade");
            txtValorUmidadeTemperatura.setText("32.63 %");
        }else if(umiOuTemp==1) {//temperatura
            txtUmidadeTemperatura.setText("Temperatura");
            txtValorUmidadeTemperatura.setText("29.49ºC");
        }else {
            txtUmidadeTemperatura.setText("ERRO");
            txtValorUmidadeTemperatura.setText("ERRO");
        }

    }
}
