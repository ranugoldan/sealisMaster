package com.kelompok4.sealis.sealis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressDialog pDialog;
    private String kota,cuaca , detail;
    private double lat,lon;
    private List<JSONObject> arealist;
    private ArrayList<Wilayah> wilayah = new ArrayList<Wilayah>();
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();
    private int stasiunID;
    //private JSONArray jArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        stasiunID = getIntent().getIntExtra("idstas", 0);
        setUpMapIfNeeded();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                DownloadContentTask task = new DownloadContentTask(this);
                task.execute (new String[ ]
                                {"http://192.168.43.36/sealis/public/wilayah?id="+stasiunID}
                );
            }
        }
    }

    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1 ;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public void setUpMap(double lat,double lon) {
        if(!wilayah.isEmpty()) {
            int i=0;
            for (Wilayah wil : wilayah) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(wil.lat, wil.lon)).title(wil.nama).snippet("Sentuh disini"));
                mHashMap.put(marker,i);
                i++;
            }
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int pos = mHashMap.get(marker);
                Wilayah obt = wilayah.get(pos);
                Intent intent = new Intent(MapsActivity.this,InfoPrediksiActivity.class);
                intent.putExtra("wilayah", obt);
                startActivity(intent);
            }
        });
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.98,112.63),3));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        /*mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat),
                Double.parseDouble(lon))).title("aa").snippet("ss")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(Double.parseDouble(lat), Double.parseDouble(lon)), 18));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent a = new Intent(MapsActivity.this,MainActivity.class);
                startActivity(a);
                //Toast.makeText(MapsActivity.this,"saayang",Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }

    private class DownloadContentTask extends AsyncTask<String, Void, String> {
        String response = "";
        Activity activity;
        public DownloadContentTask(Activity a){
            this.activity = a;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Fetching Location Data");
            pDialog.setCancelable(true);
            pDialog.show();

        }
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
            if (!response.equals("null")) {
                try {
                    JSONArray jArr = new JSONArray(response);
                    if (jArr.length()!=0) {
                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject area = jArr.getJSONObject(i);
                            int id = area.getInt("id");
                            String stasiun = area.getString("stasiun_id");
                            String nama = area.getString("nama");
                            lat = Double.parseDouble(area.getString("lat"));
                            lon = Double.parseDouble(area.getString("lon"));

                        JSONObject info = area.getJSONObject("info");
                        JSONObject prediksi = area.getJSONObject("prediksi");
                        String cuaca = info.getString("cuaca");
                        String arahangin = info.getString("arahangin");
                        String kecangin = info.getString("kecmin")+" - "+info.getString("kecmax")+" knot";
                        String tinggigel = info.getString("tinggimin")+" - "+info.getString("tinggimax")+" m";
                        String pcuaca = prediksi.getString("cuaca");
                        String parahangin = prediksi.getString("arahangin");
                        String pkecangin = prediksi.getString("kecmin")+" - "+info.getString("kecmax")+" knot";
                        String ptinggigel = prediksi.getString("tinggimin")+" - "+info.getString("tinggimax")+" m";

                        Info qinfo = new Info(arahangin,kecangin,cuaca,tinggigel);
                        Prediksi qprediksi = new Prediksi(parahangin,pkecangin,pcuaca,ptinggigel);
                        Wilayah qwilayah = new Wilayah(id,stasiun,nama,lat,lon,qinfo,qprediksi);
                        wilayah.add(qwilayah);

                        }
                    } else {
                        lat= 112.75;
                        lon= -7.25;
                    }
                    //JSONArray jObjcuaca = new JSONArray(jObj.getJSONArray("weather"));
                    //JSONObject jObjcoord = new JSONObject(jObj.getString("coord"));
                    //JSONObject jObjA = new JSONObject((Map) jObjcuaca.getJSONObject(0));
                    //cuaca = jObjA.getString("main");
                    //detail = jObjA.getString("description");
                    //lat = jObjcoord.getString("lat");
                    //lon = jObjcoord.getString("lon");
                    //kota = jObj.getString("name");
                } catch (JSONException e) {
                    lat= 112.75;
                    lon= -7.25;
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            setUpMap(lat, lon);
        }

        @Override
        protected String doInBackground(String... urls) {
            InputStream in = null;
            for (String url : urls) {
                try {
                    StringBuilder sb = new StringBuilder();
                    in = openHttpConnection(url);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String nextLine = "";
                    while ((nextLine = reader.readLine()) != null) {
                        sb.append(nextLine);
                        response = sb.toString();
                        in.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return response;
        }

    }
}
