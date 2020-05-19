package com.example.busapp.services;

import android.os.AsyncTask;

import com.example.busapp.xml.XMLparser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GETStopsDataAsyncTask extends AsyncTask<String,Void, ArrayList<BusStop>> {
    Callback callback;

    @Override
    protected ArrayList<BusStop> doInBackground(String... RouteNumber) {
        try{
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = String.format("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routeInfo?routeNumber=%s&type=bus",RouteNumber[0]);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            String jsonData = response.body().string();

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonElements = jsonObject.getJSONArray("RouteStops");
            ArrayList<BusStop> list = new ArrayList<>(Arrays.asList(new Gson().fromJson(jsonElements.toString(),BusStop[].class)));
            // რადგან xml-ში არ მიბრუნებდა და აბრუნებდა  json-ს, ასე დაწერა მომიწია.
            return list;
        }catch (Exception e){

            e.printStackTrace();
        }
        return new ArrayList<BusStop>();
    }

    @Override
    protected void onPostExecute(ArrayList<BusStop> busStops) {
        super.onPostExecute(busStops);
        if(callback != null)
            callback.onDataReceived(busStops);
    }

    public interface Callback{
        void onDataReceived(ArrayList<BusStop> busStops);
    }

    public void SetCallback(Callback callback){this.callback = callback;}
}
