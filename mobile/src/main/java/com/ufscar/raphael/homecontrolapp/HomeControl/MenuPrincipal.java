package com.ufscar.raphael.homecontrolapp.HomeControl;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ufscar.raphael.homecontrolapp.R;

import org.json.JSONException;
import org.json.JSONObject;


public class MenuPrincipal extends AppCompatActivity {

    private TextView txtHello;
    private TextView txtEnvio;
    private Button btnTeste;

    private Switch sw1;
    private Switch sw2;
    private TextView txtUmidade;
    private TextView txtUmidadeValor;
    private TextView txtTemperatura;
    private TextView txtTemperaturaValor;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        initialize();



        btnTeste.setOnClickListener(new ClickBtnTeste());

        //Ações dos switchs
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Snackbar.make(buttonView, "1-Esta checado :" + isChecked, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if(isChecked)
                    new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele1","True");
                else
                    new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele1","False");

            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Snackbar.make(buttonView, "2-Esta checado :" + isChecked, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if(isChecked)
                    new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele2","True");
                else
                    new EnviarDados().execute(TagsGlobais.URL_SERVIDOR,"rele2","False");

            }
        });



    }


    private void initialize() {

        txtHello = (TextView) findViewById(R.id.txtHello);
        txtEnvio = (TextView) findViewById(R.id.txtEnvio);
        btnTeste = (Button) findViewById(R.id.btnTestaCon);

        sw1 = (Switch) findViewById(R.id.switch1);
        sw2 = (Switch) findViewById(R.id.switch2);
        txtUmidade = (TextView) findViewById(R.id.txtUmidade);
        txtUmidade.setTextColor(Color.BLACK);
        txtUmidadeValor = (TextView) findViewById(R.id.txtUmidadeValor);
        txtUmidadeValor.setTextColor(Color.BLACK);
        txtTemperatura = (TextView) findViewById(R.id.txtTemperatura);
        txtTemperatura.setTextColor(Color.BLACK);
        txtTemperaturaValor = (TextView) findViewById(R.id.txtTemperaturaValor);
        txtTemperaturaValor.setTextColor(Color.BLACK);

        if (Conexao.verificaConexaoComInternet(getApplicationContext())) {
            txtHello.setText("Conectado à internet");
        } else {
            txtHello.setTextColor(Color.RED);
            txtHello.setText("Sem acesso à internet");
        }

        mHandler = new Handler();
        startRepeatingTask();

    }


    //A cada intervalo de tempo, irá executar aqui em um thread separado
    Runnable atualizarInformacoes = new Runnable() {
        @Override
        public void run() {
            try{
                atualizar();

            }finally {
                Log.e("FINALLY","FINALLY -atualiza");
                mHandler.postDelayed(atualizarInformacoes,TagsGlobais.INTERVALO_DE_UPDATE);
            }
        }
    };
    void startRepeatingTask() {
        atualizarInformacoes.run();
    }
    void stopRepeatingTask() {
        mHandler.removeCallbacks(atualizarInformacoes);
    }


    private class ClickBtnTeste implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new AtualizarDados().execute(TagsGlobais.URL_SERVIDOR);

        }
    }


    //Atualizar dados a partir do servidor
    private void atualizar(){
        Log.e("ATUALIZAR","ATUALIZASAS");
        //new AtualizarDados().execute(TagsGlobais.URL_SERVIDOR);

    }


    //<Params, Progress, Result>
    private class EnviarDados extends AsyncTask<String, Long, Integer> {

        String resposta;
        //params -> [0]-URL, [1]-ATRIBUTO1, [2]-ValorAtributo
        protected Integer doInBackground(String... params) {
            resposta = Conexao.enviarDadosRecuperaString(params[0],params[1],params[2]);
            return 0;
        }

        protected void onProgressUpdate(Long... progress) {
            Log.d("Home Control", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(Integer i) {
            Log.d("HOME CONTROL", "ON POST EXECUTE-Enviar");

            //Tratar STRING RESPOSTA
            txtEnvio.setText(resposta);

        }
    }


    //<Params, Progress, Result>
    private class AtualizarDados extends AsyncTask<String, Long, Integer> {

        String resposta;

        String valorRele1;
        String valorRele2;
        String valorUmidade;
        String valorTemperatura;

        //params -> [0]-URL, [1]-ATRIBUTO1, [2]-ValorAtributo
        protected Integer doInBackground(String... params) {
            resposta = Conexao.atualizarDados(params);

            //Converte resposta para JSONObject
            try {
                JSONObject jsonResposta = new JSONObject(resposta);
                valorRele1 = jsonResposta.getString("rele1");
                valorRele2 = jsonResposta.getString("rele2");
                valorUmidade = jsonResposta.getString("umidade");
                valorTemperatura = jsonResposta.getString("temperatura");

            }catch (JSONException e){
                Log.e("JSON Erro - ", "Erro: " + e.getMessage());
            }
            return 0;
        }

        protected void onProgressUpdate(Long... progress) {
            Log.d("Home Control", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(Integer i) {
            Log.d("HOME CONTROL", "ON POST EXECUTE-");

            //Tratar STRING RESPOSTA
            txtEnvio.setText(resposta);

            //Atualiza UI
            if(valorRele1.equals("True"))
                sw1.setChecked(true);
            else
                sw1.setChecked(false);

            if(valorRele2.equals("True"))
                sw2.setChecked(true);
            else
                sw2.setChecked(false);

            txtUmidadeValor.setText(valorUmidade);
            txtTemperaturaValor.setText(valorTemperatura);

        }
    }

}
