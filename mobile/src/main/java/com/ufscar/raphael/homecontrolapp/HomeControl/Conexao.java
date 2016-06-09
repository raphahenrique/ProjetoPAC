package com.ufscar.raphael.homecontrolapp.HomeControl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ufscar.raphael.homecontrolapp.HTTPRequestLibrary.HttpRequest;

/**
 * Created by Raphael on 5/21/2016.
 */
public class Conexao {

    //Enviar dadoss
    public static String enviarDadosRecuperaString(String ... param){

        //JSONObject jo = new JSONObject();
        String resposta ="";
        try {
            HttpRequest request =  HttpRequest.get(param[0]);

            resposta = HttpRequest.post(param[0]).send(montaMensagem(true,param[1],param[2])).body();


            return resposta;

        } catch (HttpRequest.HttpRequestException exception) {
            Log.e("Erro ao enviar Dados ",exception.getMessage());
        }
        return "";
    }

    public static String atualizarDados(String... param){
        String resposta ="";

        try {
            HttpRequest request =  HttpRequest.get(param[0]);

            resposta = HttpRequest.post(param[0]).send(montaMensagem(false,"","")).body();


            return resposta;

        } catch (HttpRequest.HttpRequestException exception) {
            Log.e("Erro ao receber dados ",exception.getMessage());
        }
        return "";
    }



    public static String montaMensagem(boolean isPost, String atributo, String valor_atributo){
        String mensagem;
        //Post -> enviando dados
        if(isPost) {
            mensagem = TagsGlobais.CABEÇALHO + "&" + TagsGlobais.COMANDO_ENVIAR + "&valor={"
                    +"\"" + atributo + "\":\"" + valor_atributo + "\"}";
        }
        else
            mensagem = TagsGlobais.CABEÇALHO + "&" + TagsGlobais.COMANDO_ATUALIZAR;//Atualizar

        return mensagem;
    }


    public static boolean verificaConexaoComInternet(Context context){

        ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }

}
