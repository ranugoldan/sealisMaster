package com.kelompok4.sealis.sealis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class PilihStasiunActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Spinner spinner1;
    private Button btnPilih;
    private ArrayList<Stasiun> staslist = new ArrayList<Stasiun>();
    private ArrayAdapter<Stasiun> stasiunArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DownloadContentTask task = new DownloadContentTask(this);
        task.execute(new String[]
                        {"http://192.168.43.36/sealis/public/stasiun"}
        );


        setContentView(R.layout.activity_pilihstasiun);
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

    private class DownloadContentTask extends AsyncTask<String, Void, String>{
        String response = "";
        Activity activity;
        public DownloadContentTask(Activity a){
            this.activity = a;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(PilihStasiunActivity.this);
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
                    if (jArr.length()!=0){
                        for(int i=0;i<jArr.length();i++){
                            JSONObject stas = jArr.getJSONObject(i);
                            int id = stas.getInt("id");
                            String stasiun = stas.getString("nama");
                            Stasiun newstas = new Stasiun(id,stasiun);
                            staslist.add(newstas);
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

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        stasiunArrayAdapter = new ArrayAdapter<Stasiun>(this, android.R.layout.simple_spinner_item, staslist);
        spinner1.setAdapter(stasiunArrayAdapter);
        btnPilih = (Button) findViewById(R.id.btnSubmit);
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PilihStasiunActivity.this, MapsActivity.class);
                Stasiun selectedItem = (Stasiun)spinner1.getSelectedItem();
                intent1.putExtra("idstas", selectedItem.getId());
                startActivity(intent1);
            }
        });
    }
}
