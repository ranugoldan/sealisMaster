package com.kelompok4.sealis.sealis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonWarning, buttonCari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonWarning = (Button)findViewById(R.id.buttonWarning);
        buttonCari = (Button)findViewById(R.id.buttonCari);

        buttonWarning.setOnClickListener(this);
        buttonCari.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == buttonWarning){
            Intent warning = new Intent(HomeActivity.this, WarningActivity.class);
            startActivity(warning);
        } else if(view == buttonCari) {
            Intent cari = new Intent(HomeActivity.this, PilihStasiunActivity.class);
            startActivity(cari);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent logout = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(logout);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
