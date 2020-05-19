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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busapp.services.GETRoutesDataAsyncTask;
import com.example.busapp.services.Route;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ListView routesList;
    RoutesArrayAdapter routesArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        routesList = findViewById(R.id.RoutesList);
        routesArrayAdapter = new RoutesArrayAdapter(this,0,new ArrayList<Route>());
        routesList.setAdapter(routesArrayAdapter);
        routesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Route route = (Route)parent.getItemAtPosition(position);
                StartBusStop(route.getRouteNumber());
            }
        });
    }

    public void StartBusStop(String RouteNumber){
        Intent intent = new Intent(MainActivity.this,BustStopsActivity.class);
        intent.putExtra("RouteNumber",RouteNumber);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRoutes();
    }

    private void getRoutes(){
        GETRoutesDataAsyncTask getRoutesDataAsyncTask = new GETRoutesDataAsyncTask();
        GETRoutesDataAsyncTask.Callback callback = new GETRoutesDataAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<Route> routes) {
                ArrayList<Route> list = routes;
                routesArrayAdapter.clear();
                routesArrayAdapter.addAll(list);
            }
        };
        getRoutesDataAsyncTask.setCallback(callback);
        getRoutesDataAsyncTask.execute();
    }


    class RoutesArrayAdapter extends ArrayAdapter<Route>{
        Context mContext;

        public RoutesArrayAdapter(@NonNull Context context, int resource, @NonNull List<Route> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Route route = getItem(position);
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.route_list_item,parent,false);

            TextView txtRouteNumber = view.findViewById(R.id.txtRouteNumber);
            txtRouteNumber.setText(route.getRouteNumber());

            TextView txtStopA = view.findViewById(R.id.txtStopA);
            txtStopA.setText(route.getStopA());

            TextView txtStopB = view.findViewById(R.id.txtStopB);
            txtStopB.setText(route.getStopB());

            return view;
        }
    }
}
