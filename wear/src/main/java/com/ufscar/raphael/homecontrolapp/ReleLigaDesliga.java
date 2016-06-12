package com.ufscar.raphael.homecontrolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReleLigaDesliga extends Activity {

    private BoxInsetLayout mContainerView;
    private TextView txtNomeRele;
    private ImageView imgRele;
    private int numeroRele;

    private int valorRele1Wear;
    private int valorRele2Wear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rele_liga_desliga);

        initialize();
        valorRele1Wear = 0;
        valorRele2Wear = 0;

        imgRele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numeroRele==1){
                    if(valorRele1Wear==0){
                        valorRele1Wear=1;
                        imgRele.setImageResource(R.drawable.lampada_desligada);
                        //Manda ENVIAR - new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele1","False");
                    }else {
                        valorRele1Wear=0;
                        imgRele.setImageResource(R.drawable.lampada_ligada);
                        //Manda ENVIAR - new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele1","True");
                    }
                }else if(numeroRele==2){
                    if(valorRele2Wear==0){
                        valorRele2Wear=1;
                        imgRele.setImageResource(R.drawable.lampada_desligada);
                        //Manda ENVIAR - new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele2","False");
                    }else {
                        valorRele2Wear=0;
                        imgRele.setImageResource(R.drawable.lampada_ligada);
                        //Manda ENVIAR - new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele2","True");
                }
                }

            }
        });

    }


    private void initialize(){
        mContainerView = (BoxInsetLayout) findViewById(R.id.container_rele_liga_desliga);
        txtNomeRele = (TextView) findViewById(R.id.txtNomeRele);
        imgRele = (ImageView) findViewById(R.id.imgRele);

        Intent i = getIntent();
        numeroRele = i.getIntExtra("RELE",0);

        if(numeroRele==1)
            txtNomeRele.setText("Quarto");
        else if(numeroRele==2)
            txtNomeRele.setText("Sala");
        else
            txtNomeRele.setText("Erro");




    }


}
