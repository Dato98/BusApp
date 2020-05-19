package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busapp.services.BusStop;
import com.example.busapp.services.GETMonitorDataAsyncTask;
import com.example.busapp.services.GETStopsDataAsyncTask;
import com.example.busapp.services.Monitor;

import java.util.ArrayList;
import java.util.List;

public class BustStopsActivity extends AppCompatActivity {
    String RouteNumber;
    ListView busStopList;
    BusStopsArrayAdapter busStopsArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bust_stops);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            RouteNumber = extras.getString("RouteNumber");
        busStopList = findViewById(R.id.busStopList);
        busStopsArrayAdapter = new BusStopsArrayAdapter(this,0,new ArrayList<BusStop>());
        busStopList.setAdapter(busStopsArrayAdapter);

        busStopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusStop stop = (BusStop)parent.getItemAtPosition(position);
                getMonitorData(stop.getStopId());
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        getBusStops(RouteNumber);
    }

    private void getMonitorData(String StopId){
        final GETMonitorDataAsyncTask getMonitorDataAsyncTask = new GETMonitorDataAsyncTask();
        GETMonitorDataAsyncTask.Callback callback = new GETMonitorDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<Monitor> list) {
                String monitorData = "ჩანაწერი არ არის";
                if(list.size() > 0){
                    monitorData = "მარშ.  მიმართულება.  წთ \n";
                    for (int i = 0;i<list.size();i++){
                        monitorData += String.format("%s.  %s.  %s \n",list.get(i).getRouteNumber(),list.get(i).getDestination(),list.get(i).getMinutes());
                    }
                }
                Toast.makeText(getApplicationContext(),monitorData,Toast.LENGTH_SHORT).show();
            }
        };
        getMonitorDataAsyncTask.setCallback(callback);
        getMonitorDataAsyncTask.execute(StopId);
    }

    private void getBusStops(String RouteN){
        GETStopsDataAsyncTask getStopsDataAsyncTask = new GETStopsDataAsyncTask();
        GETStopsDataAsyncTask.Callback callback = new GETStopsDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<BusStop> busStops) {
                ArrayList<BusStop> list = busStops;
                busStopsArrayAdapter.clear();
                busStopsArrayAdapter.addAll(list);
            }
        };
        getStopsDataAsyncTask.SetCallback(callback);
        getStopsDataAsyncTask.execute(RouteN);
    }

    class BusStopsArrayAdapter extends ArrayAdapter<BusStop>{
        Context mContext;
        public BusStopsArrayAdapter(@NonNull Context context, int resource, @NonNull List<BusStop> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            BusStop stop = getItem(position);
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.busstop_list_item,parent,false);

            TextView txtStopName = view.findViewById(R.id.txtStopName);
            String name = stop.getName();
            txtStopName.setText(name.substring(0,name.indexOf('-')).trim());

            return view;
        }
    }
}
