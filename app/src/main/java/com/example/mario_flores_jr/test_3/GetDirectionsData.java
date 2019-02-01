package com.example.mario_flores_jr.test_3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.IOException;

import static android.app.PendingIntent.getActivity;
import static android.widget.Toast.LENGTH_LONG;
import static com.example.mario_flores_jr.test_3.MapsActivity.context;


public class GetDirectionsData extends AsyncTask<Object,String,String> {


    GoogleMap mMap;
    String url;
    String googleDirectionsData;

    LatLng latLng;
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];



        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

        String[] directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        displayDirection(directionsList);

    }

    public void displayDirection(String[] directionsList)
    {


        int count = directionsList.length;
        if(count ==0){
            Toast.makeText(context,"Error al momento de Trazar la ruta, intente mas tarde",Toast.LENGTH_LONG).show();
            return;
        }else {

            for (int i = 0; i < count; i++) {
                PolylineOptions options = new PolylineOptions();
                options.color(Color.RED);
                options.width(10);
                options.addAll(PolyUtil.decode(directionsList[i]));
                mMap.addPolyline(options);
            }

            Toast.makeText(context,"Ruta",Toast.LENGTH_SHORT).show();
        }
    }






}

