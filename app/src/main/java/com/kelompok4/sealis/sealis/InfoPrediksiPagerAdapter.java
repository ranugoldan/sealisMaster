package com.kelompok4.sealis.sealis; /**
 * Created by Ranu on 19/11/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class InfoPrediksiPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public InfoPrediksiPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoFragment tab1 = new InfoFragment();
                return tab1;
            case 1:
                PrediksiFragment tab2 = new PrediksiFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}