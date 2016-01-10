package com.kelompok4.sealis.sealis;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class InfoPrediksiActivity extends AppCompatActivity {

    public Wilayah getWil() {
        return wil;
    }

    private Wilayah wil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoprediksi);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SEALIS");
        setSupportActionBar(toolbar);

        TabLayout tabMain = (TabLayout) findViewById(R.id.tab_layout);
        tabMain.addTab(tabMain.newTab().setText("Sekarang"));
        tabMain.addTab(tabMain.newTab().setText("Prediksi"));
        tabMain.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final InfoPrediksiPagerAdapter adapter = new InfoPrediksiPagerAdapter
                (getSupportFragmentManager(), tabMain.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        wil = (Wilayah)getIntent().getSerializableExtra("wilayah");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_tambahwilayah:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_logout:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
