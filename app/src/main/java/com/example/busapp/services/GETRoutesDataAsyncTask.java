package com.example.busapp.services;

import android.os.AsyncTask;

import com.example.busapp.xml.XMLparser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GETRoutesDataAsyncTask extends AsyncTask<Void,Void, ArrayList<Route>> {
    Callback callback;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Route> doInBackground(Void... voids) {
        try
        {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routes")
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String XMLdata = response.body().string();
            XMLparser xmLparser = new XMLparser();
            ArrayList<Route> list = xmLparser.GetRouteArrayFromString(XMLdata);
            return  list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Route>();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Route> routes) {
        super.onPostExecute(routes);
        if(callback!=null)
            callback.onDataReceived(routes);
    }

    public interface Callback{
        void onDataReceived(ArrayList<Route> routes);
    }

    public void setCallback(Callback callback) {this.callback = callback;}
}
