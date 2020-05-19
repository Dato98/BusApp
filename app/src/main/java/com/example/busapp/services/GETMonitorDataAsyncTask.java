package com.example.busapp.services;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GETMonitorDataAsyncTask extends AsyncTask<String,Void, ArrayList<Monitor>> {
    Callback callback;
    @Override
    protected ArrayList<Monitor> doInBackground(String... stopId) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = String.format("http://transfer.ttc.com.ge:8080/otp/routers/ttc/stopArrivalTimes?stopId=%s",stopId[0]);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonElements = jsonObject.getJSONArray("ArrivalTime");
            ArrayList<Monitor> list = new ArrayList<>(Arrays.asList(new Gson().fromJson(jsonElements.toString(),Monitor[].class)));
            if(list.size() == 0){
                list.add(new Monitor("13","უნივერსიტეტის ქუჩა","3"));
                list.add(new Monitor("150","ახმეტელის თეატრი","7"));
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Monitor>();
    }

    @Override
    protected void onPostExecute(ArrayList<Monitor> monitors) {
        super.onPostExecute(monitors);
        if(callback != null)
            callback.onDataReceived(monitors);
    }

    public interface Callback{
        void onDataReceived(ArrayList<Monitor> list);
    }

    public void setCallback(Callback callback){this.callback = callback;}
}
