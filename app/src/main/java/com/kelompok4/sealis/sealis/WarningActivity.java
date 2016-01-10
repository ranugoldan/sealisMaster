package com.kelompok4.sealis.sealis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

public class WarningActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ArrayList<Wilayah> willist = new ArrayList<Wilayah>();
    private ArrayList<String> wilayahnama = new ArrayList<String>();
    private ArrayAdapter<String> wilayahArrayAdapter;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DownloadContentTask task = new DownloadContentTask(this);
        task.execute(new String[]
                        {"http://192.168.43.36/sealis/public/warning"}
        );

        setContentView(R.layout.activity_warning);


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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Wilayah obt = willist.get(i);
        Intent go = new Intent(WarningActivity.this, InfoPrediksiActivity.class);
        go.putExtra("wilayah", obt);
        startActivity(go);

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
            pDialog = new ProgressDialog(WarningActivity.this);
            pDialog.setMessage("Loading Data");
            pDialog.setCancelable(false);
            pDialog.show();

        }

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
                            Double lat = Double.parseDouble(area.getString("lat"));
                            Double lon = Double.parseDouble(area.getString("lon"));

                            JSONObject info = area.getJSONObject("info");
                            JSONObject prediksi = area.getJSONObject("prediksi");
                            String cuaca = info.getString("cuaca");
                            String arahangin = info.getString("arahangin");
                            String kecangin = info.getString("kecmin") + " - " + info.getString("kecmax")+" knot";
                            String tinggigel = info.getString("tinggimin") + " - " + info.getString("tinggimax")+" m";
                            String pcuaca = prediksi.getString("cuaca");
                            String parahangin = prediksi.getString("arahangin");
                            String pkecangin = prediksi.getString("kecmin") + " - " + info.getString("kecmax")+" knot";
                            String ptinggigel = prediksi.getString("tinggimin") + " - " + info.getString("tinggimax")+" m";

                            Info qinfo = new Info(arahangin, kecangin, cuaca, tinggigel);
                            Prediksi qprediksi = new Prediksi(parahangin, pkecangin, pcuaca, ptinggigel);
                            Wilayah qwilayah = new Wilayah(id, stasiun, nama, lat, lon, qinfo, qprediksi);
                            willist.add(qwilayah);
                            wilayahnama.add(nama);
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            setUp();
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

    private void setUp() {

        ListView listView = (ListView) findViewById(R.id.listView);
        wilayahArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wilayahnama);
        listView.setAdapter(wilayahArrayAdapter);
        listView.setOnItemClickListener(this);
    }
}
